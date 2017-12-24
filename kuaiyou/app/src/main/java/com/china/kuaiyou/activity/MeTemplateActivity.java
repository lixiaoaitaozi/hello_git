package com.china.kuaiyou.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.adapter.MeTemplateAdapter;
import com.china.kuaiyou.appliction.MyApplication;
import com.china.kuaiyou.bean.ResultUploadbean;
import com.china.kuaiyou.bean.UploadBean;
import com.china.kuaiyou.fileutil.FileInfo;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.LG;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.mybase.MySharePreferences;
import com.china.kuaiyou.myview.MyRecyclerView;
import com.china.kuaiyou.netbean.Msg;
import com.china.kuaiyou.netbean.template.TemplateBean;
import com.china.kuaiyou.netbean.user.UserBean;
import com.china.kuaiyou.netutil.NetToGet;
import com.china.kuaiyou.netutil.NetToSend;
import com.china.kuaiyou.okhttp.OkHttpManager;
import com.china.kuaiyou.util.TimeUtil;
import com.china.kuaiyou.zxing.util.ScanUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/9/8 0008.
 */

public class MeTemplateActivity extends BaseCompatActivity implements View.OnClickListener, MeTemplateAdapter.MyOnLongClick, OkHttpManager.DataCallBack {
    private UserBean appUser;
    public final int REQUEST_QRCODE = 0x01;
    private TextView waitTV;
    private MyRecyclerView recyclerView;
    private ArrayList<FileInfo> fileInfos;
    private MeTemplateAdapter meTemplateAdapter;
    private final int INIT_FILE = 0;
    private final int INIT_OVER = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INIT_FILE:
                    waitTV.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fileInfos = new ArrayList<>();
                            switch (showType) {
                                case "0":
                                    fileInfos = MyApplication.videoFileInfos;
                                    break;
                                case "1":
                                    fileInfos = MyApplication.zipFileInfos;
                                    break;
                                case "2":
                                    fileInfos = MyApplication.imageFileInfos;
                                    break;
                            }
                            LG.i(tag, "-----" + fileInfos.size());
                            meTemplateAdapter = new MeTemplateAdapter(MeTemplateActivity.this, fileInfos, MeTemplateActivity.this);
                            handler.sendEmptyMessage(INIT_OVER);
                        }
                    }).start();
                    break;
                case INIT_OVER:
                    waitTV.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(meTemplateAdapter);
                    break;
            }
        }
    };
    //弹窗
    private AlertDialog alertDialog;
    private TextView dialogTitleTV;
    private Button dialogOkBT, dialogCancelBT;
    private FileInfo selectFileInfo;
    private final int NET_TYPE = 1;
    private String showType = "";
    private boolean isUpload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_template_layout);
        initData();
        initTitle();
        initView();
        initFile();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null)
            alertDialog.cancel();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            switch (resultCode) {
                case 100:
                    if (!TextUtils.isEmpty(netImagUrl) && !TextUtils.isEmpty(netDownUrl)) {
                        TemplateBean templateBean = new TemplateBean();
                        templateBean.setName(selectFileInfo.getDisplayName());
                        templateBean.setVertical(isV);
                        templateBean.setDownUrl(netDownUrl);
                        templateBean.setImageUrl(netImagUrl);
                        switch (appUser.getType()) {
                            case MERCHANT_USER:
                                templateBean.setUserId(appUser.getId());
                                break;
                            case EMPLOYEE_USER:
                                templateBean.setUserId(appUser.getCreateById());
                                break;
                        }
                        templateBean.setIspublic(false);
                        switch (showType) {
                            case "0":
                                templateBean.setType(1);
                                break;
                            case "1":
                                templateBean.setType(2);
                                break;
                            case "2":
                                templateBean.setType(0);
                                break;

                        }
                        templateBean.setVertical(isV);
                        isUpload = false;
                        NetToSend.getInstance().tempalteAdd(templateBean, this, NET_TYPE);
                        break;
                    }
                    break;
                case 101:
                    isUpload = false;
                    showToast("取消上传了");
                    break;
            }

        }
    }

    private void initTitle() {
        ImageView titleBackIV = (ImageView) findViewById(R.id.iv_title_back);
        titleBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView titleTV = (TextView) findViewById(R.id.tv_title);
        String titleString = "";
        switch (showType) {
            case "0":
                titleString = "视频";
                break;
            case "1":
                titleString = "模板";
                break;
            case "2":
                titleString = "图片";
                break;

        }
        titleTV.setText(titleString);
    }

    private void initData() {
        appUser = MyGson.getInstance().fromJson(MySharePreferences.getInstance().getString("appuser"), UserBean.class);
        showType = getIntentString();
    }

    boolean isV = true;

    private void initView() {
        recyclerView = (MyRecyclerView) findViewById(R.id.rv_me_template);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        waitTV = (TextView) findViewById(R.id.tv_me_templat_wait);
        //弹窗
        alertDialog = new AlertDialog.Builder(context).create();
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_me_template_layout, null);
        final RadioGroup rg = dialogView.findViewById(R.id.ddd);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.ddd_h:
                        isV = false;
                        break;
                    case R.id.ddd_v:
                        isV = true;
                        break;
                }
            }
        });
        dialogTitleTV = dialogView.findViewById(R.id.tv_device_dialog_title);
        dialogTitleTV.setText("是否把这个内容设置成对应的模板?(默认不选择为横屏)");
        dialogOkBT = dialogView.findViewById(R.id.bt_device_dialog_ok);
        dialogCancelBT = dialogView.findViewById(R.id.bt_device_dialog_cancel);
        alertDialog.setView(dialogView);
        dialogOkBT.setOnClickListener(this);
        dialogCancelBT.setOnClickListener(this);

    }

    private void initFile() {
        handler.sendEmptyMessage(INIT_FILE);
    }

    String netImagUrl;
    String netDownUrl;
    List<UploadBean> uploadBeanList;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_device_dialog_ok) {
            if (selectFileInfo != null && appUser != null) {
                final String nowTime = TimeUtil.getNowTimeLong() + "";
                final String imageName = nowTime + ".png";
                switch (showType) {
                    case "0":
                        showToast("生成首页图中...请等待");
                        isUpload = true;
                        final String imageUrl = MyApplication.pic_files + File.separator + imageName;
                        MyGlide.GetBitmapImp getBitmapImp = new MyGlide.GetBitmapImp() {
                            @Override
                            public void getBitmap(Bitmap bitmap) {
                                boolean getImage = MyGlide.getInstance().bitmapToFile(bitmap, imageUrl);
                                if (getImage) {
                                    netImagUrl = "private" + File.separator + "d" + appUser.getId() + File.separator + imageName;
                                    netDownUrl = "private" + File.separator + "d" + appUser.getId() + File.separator + selectFileInfo.getDisplayName();

                                    UploadBean imageUpload = new UploadBean(imageName, imageUrl, netImagUrl);
                                    UploadBean downUpload = new UploadBean(selectFileInfo.getDisplayName(), selectFileInfo.getPath(), netDownUrl);
                                    uploadBeanList = new ArrayList<>();
                                    uploadBeanList.add(imageUpload);
                                    uploadBeanList.add(downUpload);
                                    Intent intent = new Intent(MeTemplateActivity.this, UploadActivity.class);
                                    intent.putExtra("tb", MyGson.getInstance().toGsonStr(new ResultUploadbean(uploadBeanList)));
                                    MeTemplateActivity.this.startActivityForResult(intent, 0);
                                } else {
                                    showToast("wenjianshibai");
                                }
                            }
                        };
                        MyGlide.getInstance().getBitmap(context, selectFileInfo.getPath(), getBitmapImp);
                        break;
                    case "1":
                        showToast("buzuochuli");
                        break;
                    case "2":
                        showToast("imageUp");
                        netDownUrl = "private" + File.separator + "d" + appUser.getId() + File.separator + imageName;
                        netImagUrl = "private" + File.separator + "d" + appUser.getId() + File.separator + imageName;
                        isUpload = true;
                        UploadBean imageUpload = new UploadBean(imageName, selectFileInfo.getPath(), netImagUrl);
                        uploadBeanList = new ArrayList<>();
                        uploadBeanList.add(imageUpload);
                        Intent intent = new Intent(MeTemplateActivity.this, UploadActivity.class);
                        intent.putExtra("tb", MyGson.getInstance().toGsonStr(new ResultUploadbean(uploadBeanList)));
                        MeTemplateActivity.this.startActivityForResult(intent, 0);
                        break;
                }


            } else {
                showToast("选择资源有问题");
            }
            alertDialog.hide();
        } else if (id == R.id.bt_device_dialog_cancel) {
            alertDialog.hide();
        }
    }


    @Override
    public void myOnLongClickDataListen(FileInfo fileInfo) {
        if (!isUpload) {
            alertDialog.show();
            selectFileInfo = fileInfo;
        } else {
            showToast("uploading !please wait...");
        }

    }

    @Override
    public void requestFailure(Request request, IOException e) {
        showToast("网络连接失败");
    }

    @Override
    public void requestSuccess(String result, int netbs) throws Exception {
        Msg msg = NetToGet.getInstance().getMessageBean(result);
        if (msg.getC() == 1) {
//            EventBus.getDefault().post(new MessageEvent(EventEnum.EVENTBUS_MESSAGE_BY_TEMPLATE, "reget"));

        }
        showToast(msg.getM());
    }


}
