package com.china.kuaiyou.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * @className :GossipBean
 * @purpose: 八卦内容显示实体对象
 * @author:
 * @data:2017/11/12 15:01
 */

public class GossipBean implements Serializable {

    private int id;
    private int userID;//用户ID
    private int userIcon;//用户头像ID
    private String userName;//用户名
    private String signObject;//关注对象
    private String dateTimes;//发表时间
    private String contents;//发表内容
    private int dataType;//发表内容的数据类型 2 是图片 1 是文本
    private List<Bitmap> list;//内容为图片的数据源


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

    public int getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(int userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSignObject() {
        return signObject;
    }

    public void setSignObject(String signObject) {
        this.signObject = signObject;
    }

    public String getDateTimes() {
        return dateTimes;
    }

    public void setDateTimes(String dateTimes) {
        this.dateTimes = dateTimes;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public List<Bitmap> getList() {
        return list;
    }

    public void setList(List<Bitmap> list) {
        this.list = list;
    }
}
