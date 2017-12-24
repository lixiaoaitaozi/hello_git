package com.china.kuaiyou.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class UploadBean implements Serializable {
    private String fileName;//文件名字
    private String filePath;//本地的文件路径
    private String uploadPath;//上传的文件地址


    public UploadBean(String fileName, String filePath, String uploadPath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadPath = uploadPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public String toString() {
        return "UploadBean{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                '}';
    }
}
