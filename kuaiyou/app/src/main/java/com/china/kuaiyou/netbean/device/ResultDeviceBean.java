package com.china.kuaiyou.netbean.device;

import java.util.ArrayList;
import java.util.List;

public class ResultDeviceBean {
    private List<DeviceBean> list;

    public ResultDeviceBean() {
        super();
        this.list = new ArrayList<>();
    }

    public ResultDeviceBean(List<DeviceBean> list) {
        super();
        this.list = list;
    }

    public List<DeviceBean> getList() {
        return list;
    }

    public void setList(List<DeviceBean> list) {
        this.list = list;
    }

    public void add(DeviceBean deviceBean) {
        list.add(deviceBean);
    }

    public void remove(DeviceBean deviceBean) {
        for (int i = 0; i < list.size(); i++) {
            DeviceBean deviceBean1 = list.get(i);
            if (deviceBean.getId().equals(deviceBean1.getId())) {
                this.list.remove(i);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "ResultDeviceBean [list=" + list + "]";
    }

}
