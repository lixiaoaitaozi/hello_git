package com.china.kuaiyou.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @className :PrivatesLetterBean
 * @purpose: 私信显示内容实体对象
 * @author:
 * @data:2017/11/12 16:27
 */

public class PrivatesLetterBean implements Serializable {

    private int id;
    private String LetterName;
    private Bitmap icon;
    private String contents;
    private String dataTimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLetterName() {
        return LetterName;
    }

    public void setLetterName(String letterName) {
        LetterName = letterName;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDataTimes() {
        return dataTimes;
    }

    public void setDataTimes(String dataTimes) {
        this.dataTimes = dataTimes;
    }
}
