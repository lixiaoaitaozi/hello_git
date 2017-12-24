package com.china.kuaiyou.appliction;


import com.china.kuaiyou.netbean.device.ResultDeviceBean;
import com.china.kuaiyou.netbean.template.ResultTemplateBean;
import com.china.kuaiyou.netbean.user.UserBean;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class MyApplicationDataConfig {
    public static UserBean appUser;
    public static ResultDeviceBean allDevices;//该用户所有的设备集合
    public static ResultTemplateBean allTemplates;//该用户所有的模板集合


    public static void clear() {
        appUser = null;
        allDevices = null;
        allTemplates = null;

    }
}
