package com.china.kuaiyou.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @className :AddressBean
 * @purpose: 通讯录好友的数据实体对象
 * @author:
 * @data:2017/11/11 12:09
 */

public class AddressBean implements Serializable {

    private int id;
    private long userID;
    private String userName;
    private String phone;
    private Bitmap iconBitmap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }
}
