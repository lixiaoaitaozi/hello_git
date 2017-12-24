package com.china.kuaiyou.bean;

import java.io.Serializable;

/**
 * @className :AddressBookBean
 * @purpose: 通讯录数据实体对象
 * @author:
 * @data:2017/11/7 22:35
 */

public class AddressBookBean implements Serializable {

    private int id;//编号
    private int userID;//用户编号
    private String userName;//用户昵称
    private String source;//用户来源
    private String iconPath;//用户头像地址
    private int isAttention;//用户是否关注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(int isAttention) {
        this.isAttention = isAttention;
    }
}
