package com.china.kuaiyou.netbean.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class ResultWifiInfoBean implements Serializable {
    private List<WifiInfoBean> list;

    public ResultWifiInfoBean() {
        this.list = new ArrayList<>();
    }

    public List<WifiInfoBean> getList() {
        return list;
    }

    public void setList(List<WifiInfoBean> list) {
        this.list = list;
    }

    public void add(WifiInfoBean wifiInfoBean) {
        this.list.add(wifiInfoBean);
    }

    public void remove(WifiInfoBean wifiInfoBean) {
        for (int i = 0; i < list.size(); i++) {
            WifiInfoBean queWifiInfoBean = list.get(i);
            if (wifiInfoBean.getSsid().equals(queWifiInfoBean.getSsid()) && wifiInfoBean.getPassWord().equals(queWifiInfoBean.getPassWord())) {
                list.remove(i);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "ResultWifiInfoBean{" +
                "list=" + list +
                '}';
    }
}
