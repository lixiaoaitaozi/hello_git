package com.china.kuaiyou.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class ResultUploadbean implements Serializable {
    private List<UploadBean> list;

    public ResultUploadbean() {
        this.list = new ArrayList<>();
    }

    public ResultUploadbean(List<UploadBean> list) {
        this.list = list;
    }

    public List<UploadBean> getList() {
        return list;
    }

    public void setList(List<UploadBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ResultUploadbean{" +
                "list=" + list +
                '}';
    }
}
