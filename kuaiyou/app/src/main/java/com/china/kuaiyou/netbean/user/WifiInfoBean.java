package com.china.kuaiyou.netbean.user;

import java.io.Serializable;

public class WifiInfoBean implements Serializable {
    private String ssid;// 账号
    private String passWord;// 密码
    private int type;// WIFI种类 0 店铺的路由器,1主机自生产的WIFI

    public WifiInfoBean(String ssid, String passWord, int type) {
        this.ssid = ssid;
        this.passWord = passWord;
        this.type = type;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "WifiInfoBean{" +
                "ssid='" + ssid + '\'' +
                ", passWord='" + passWord + '\'' +
                ", type=" + type +
                '}';
    }
}
