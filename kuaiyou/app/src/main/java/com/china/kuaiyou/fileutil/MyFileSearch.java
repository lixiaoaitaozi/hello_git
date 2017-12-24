package com.china.kuaiyou.fileutil;

import android.os.Environment;
import android.text.TextUtils;

import com.china.kuaiyou.appliction.MyApplication;
import com.china.kuaiyou.mybase.LG;

import java.io.File;
import java.util.ArrayList;


/**
 * 黎潇自己写的搜索器
 */
public class MyFileSearch {

    private static String tag = "MyFileSearch>>>";

    /**
     * 获取你所需要的文件下面的符合条件的文件集合
     *
     * @param fileInfos
     * @param file
     * @param myFilefilter
     * @return
     */
    public static ArrayList<FileInfo> getAllNeedFile(
            ArrayList<FileInfo> fileInfos, File file, MyFilefilter myFilefilter) {
        tag = "MyFileSearch>>>getAllNeedFile()";
        File files[] = {};
        File needFiles[] = {};
        files = getFiles(file);
        needFiles = getFiles(file, myFilefilter);
        try {
            for (int i = 0; i < needFiles.length; i++) {
                fileInfos.add(new FileInfo(needFiles[i].getName(), needFiles[i]
                        .getAbsolutePath()));
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    getAllNeedFile(fileInfos, files[i], myFilefilter);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            LG.w(tag, e);
        }
        return fileInfos;
    }

    /**
     * 获取手机所有的目录集合
     */
    public static ArrayList<FileInfo> getPhoneAllFile(
            ArrayList<FileInfo> fileInfos) {
        try {
            tag = "MyFileSearch>>>getPhoneAllFile()";
            File file = new File(MyApplication.S_APK_PATH);
            if (file.exists()) {
                file.mkdirs();
            }
            File files[] = getFiles(file);
            for (int i = 0; i < files.length; i++) {
                fileInfos.add(new FileInfo(files[i].getName(), files[i]
                        .getAbsolutePath()));
                if (files[i].isDirectory()) {
                    getPhoneAllFile(fileInfos, files[i]);
                }
            }
        } catch (Exception e) {
        }

        return fileInfos;
    }

    /**
     * 获取对应文件所有的目录集合
     */
    public static ArrayList<FileInfo> getPhoneAllFile(
            ArrayList<FileInfo> fileInfos, File file) {
        tag = "MyFileSearch>>>getPhoneAllFile()";
        File files[] = getFiles(file);
        if (files != null)
            for (int i = 0; i < files.length; i++) {
                fileInfos.add(new FileInfo(files[i].getName(), files[i]
                        .getAbsolutePath()));
                if (files[i].isDirectory()) {
                    getPhoneAllFile(fileInfos, files[i]);
                }
            }
        return fileInfos;
    }

    /**
     * 获取手机对应搜索条件文件集合
     */
    public static ArrayList<FileInfo> getPhoneAllFile(
            ArrayList<FileInfo> fileInfos, MyFilefilter myFilefilter) {
        File file = Environment.getExternalStorageDirectory();
        return getAllNeedFile(fileInfos, file, myFilefilter);
    }

    /**
     * 获取手机所有的视频文件集合
     */
    public static ArrayList<FileInfo> getPhoneAllVideoFile(
            ArrayList<FileInfo> fileInfos) {
        File file = Environment.getExternalStorageDirectory();
        return getAllNeedFile(fileInfos, file, new MyFilefilter(
                FileType.fileIsVideo));
    }

    /**
     * 获取手机所有的音频文件集合
     */
    public static ArrayList<FileInfo> getPhoneAllVoiceFile(
            ArrayList<FileInfo> fileInfos) {
        File file = Environment.getExternalStorageDirectory();
        return getAllNeedFile(fileInfos, file, new MyFilefilter(
                FileType.fileIsVoice));
    }

    /**
     * 获取手机所有的APK文件集合
     */
    public static ArrayList<FileInfo> getPhoneAllAPKFile(
            ArrayList<FileInfo> fileInfos) {
        File file = Environment.getExternalStorageDirectory();
        return getAllNeedFile(fileInfos, file, new MyFilefilter(
                FileType.fileIsApk));
    }

    /**
     * 获取手机所有的图片文件集合
     */
    public static ArrayList<FileInfo> getPhoneAllImageFile(
            ArrayList<FileInfo> fileInfos) {
        File file = Environment.getExternalStorageDirectory();
        return getAllNeedFile(fileInfos, file, new MyFilefilter(
                FileType.fileIsImage));
    }

    /**
     * 获取指定文件所有的视频文件集合
     */
    public static ArrayList<FileInfo> getAllVideoFile(
            ArrayList<FileInfo> fileInfos, File file) {
        return getAllNeedFile(fileInfos, file, new MyFilefilter(
                FileType.fileIsVideo));
    }

    /**
     * 获取指定文件所有的音频文件集合
     */
    public static ArrayList<FileInfo> getAllVoiceFile(
            ArrayList<FileInfo> fileInfos, File file) {
        return getAllNeedFile(fileInfos, file, new MyFilefilter(
                FileType.fileIsVoice));
    }

    /**
     * 获取指定文件所有的APK文件集合
     */
    public static ArrayList<FileInfo> getAllAPKFile(
            ArrayList<FileInfo> fileInfos, File file) {
        return getAllNeedFile(fileInfos, file, new MyFilefilter(
                FileType.fileIsApk));
    }

    /**
     * 获取指定文件所有的图片文件集合
     */
    public static ArrayList<FileInfo> getAllImageFile(
            ArrayList<FileInfo> fileInfos, File file) {
        return getAllNeedFile(fileInfos, file, new MyFilefilter(
                FileType.fileIsImage));
    }

    /**
     * 传入需要搜索的文件，跟自定义的所有条件,返回所有符合条件的文件
     *
     * @param file
     * @param myFilefilter
     * @return
     */
    public static File[] getFiles(File file, MyFilefilter myFilefilter) {
        File[] files = {};
        try {
            files = file.listFiles(myFilefilter);
        } catch (Exception e) {
            // TODO: handle exception
            LG.e(tag + "getfiles()>>>", e.toString());
        }
        return files;
    }

    /**
     * 传入需要搜索的文件，获取该文件的所有子目录
     *
     * @param file
     * @return
     */
    public static File[] getFiles(File file) {
        File[] files = {};
        try {
            files = file.listFiles();
        } catch (Exception e) {
            // TODO: handle exception
            LG.e(tag + "getfiles()>>>", e);
        }
        return files;
    }

    //0为视频，1为音频，2为APK，3为图片，4为GIF图片,5为HTML
    public static int fileType(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            int index = filePath.lastIndexOf(".");
            if (index != -1) {
                String type = filePath.substring(index);
                for (int i = 0; i < FileType.fileTypes.length; i++) {
                    for (int j = 0; j < FileType.fileTypes[i].length; j++) {
                        if (type.equals(FileType.fileTypes[i][j])) {
                            return i;
                        }
                    }
                }
            }
        }
        return 99;
    }


}
