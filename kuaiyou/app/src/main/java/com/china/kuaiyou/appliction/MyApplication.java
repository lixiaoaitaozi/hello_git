package com.china.kuaiyou.appliction;

import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;


import com.china.kuaiyou.fileutil.FileInfo;
import com.china.kuaiyou.mybase.BaseAppliction;
import com.china.kuaiyou.oss.OssManager;
import com.china.kuaiyou.thread.GetTemplateThread;
import com.china.kuaiyou.thread.GetTemplateThreadImp;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyApplication extends BaseAppliction implements GetTemplateThreadImp {
    public static final String pack_name = "lhcx";
    public static final String S_APK_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + pack_name;// 总文件夹
    public static final String public_files = S_APK_PATH + File.separator
            + "public";// 公共文件
    public static final String private_files = S_APK_PATH + File.separator
            + "private";// 私有文件
    public static final String movies_files = S_APK_PATH + File.separator
            + "movies";// 电影
    public static final String adver_files = S_APK_PATH + File.separator
            + "adver";// 广告
    public static final String pic_files = S_APK_PATH + File.separator + "pic";// 图片广告
    public static final String gif_files = S_APK_PATH + File.separator + "gif";// 图片广告
    public static final String doc_files = S_APK_PATH + File.separator + "doc";// 需求文档
    public static final String apks_files = S_APK_PATH + File.separator
            + "apks";// apk
    public static final String html_files = S_APK_PATH + File.separator
            + "htmls";// apk
    public static final String cache_files = S_APK_PATH + File.separator
            + "cache";// apk
    public static final String[] file_names = {S_APK_PATH, public_files,
            private_files, movies_files, adver_files, doc_files, pic_files,
            apks_files, html_files, gif_files, cache_files};
    public static boolean initTemplateIsOk;
    public static ArrayList<FileInfo> imageFileInfos;
    public static ArrayList<FileInfo> videoFileInfos;
    public static ArrayList<FileInfo> zipFileInfos;

    @Override
    public void onCreate() {
        super.onCreate();

//        MyExceptionSearch.getInstance().init(this);
        mkFile();
        OssManager.getInstance().init();
        //启动文件搜索线程
        initGetTemplate();
        //初始化FILEDOWNLODER
        initDownload();
        //初始化环信聊天系统
        initialEaseMob();
    }

    public void initGetTemplate() {
        initTemplateIsOk = false;
        new GetTemplateThread(MyApplication.this).start();
    }

    public void mkFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < MyApplication.file_names.length; i++) {
                    File file = new File(MyApplication.file_names[i]);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
            }
        }).start();
    }


    private void initDownload() {
        // 1、创建Builder
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(this);
        // 2.配置Builder
        // 配置下载文件保存的文件夹
        builder.configFileDownloadDir(S_APK_PATH);
        // 配置同时下载任务数量，如果不配置默认为2
        builder.configDownloadTaskSize(3);
        // 配置失败时尝试重试的次数，如果不配置默认为0不尝试
        builder.configRetryDownloadTimes(5);
        // 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
        builder.configDebugMode(true);
        // 配置连接网络超时时间，如果不配置默认为15秒
        builder.configConnectTimeout(25000);// 25秒
        // 3、使用配置文件初始化FileDownloader
        FileDownloadConfiguration configuration = builder.build();
        FileDownloader.init(configuration);

    }

    private void initialEaseMob() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);


        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e("myappliction", "aaaaaenter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

//初始化
        EMClient.getInstance().init(this, options);
        Log.e("myappliction", "aaaaaininOk");
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    @Override
    public void initOk(ArrayList<FileInfo> imageFileInfos, ArrayList<FileInfo> videoFileInfos, ArrayList<FileInfo> zipFileInfos) {
        initTemplateIsOk = true;
        MyApplication.imageFileInfos = imageFileInfos;
        MyApplication.videoFileInfos = videoFileInfos;
        MyApplication.zipFileInfos = zipFileInfos;
        try {
            new Thread().sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new GetTemplateThread(MyApplication.this).start();

    }
}
