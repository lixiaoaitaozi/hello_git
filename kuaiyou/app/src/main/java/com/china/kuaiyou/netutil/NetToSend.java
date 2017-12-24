package com.china.kuaiyou.netutil;

import android.content.Context;

import com.china.kuaiyou.mybase.BaseAppliction;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.mybase.PhoneUtil;
import com.china.kuaiyou.netbean.QueTemplateBean;
import com.china.kuaiyou.netbean.Send;
import com.china.kuaiyou.netbean.device.DeviceBean;
import com.china.kuaiyou.netbean.template.TemplateBean;
import com.china.kuaiyou.netbean.user.AppLogin;
import com.china.kuaiyou.netbean.user.AppRegister;
import com.china.kuaiyou.okhttp.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiefuning on 2017/4/12.
 * about:
 */

public class NetToSend {
    private static NetToSend send;
    private String searchStr;//最后一次搜索的字符串
    private Context context;

    private NetToSend() {
        this.context = BaseAppliction.getContext();
    }

    public static NetToSend getInstance() {
        if (send == null) {
            send = new NetToSend();
        }
        return send;
    }

    private Send getSendBean(String sendStr, String object) {
        return new Send(sendStr, object);
    }

    private Map<String, String> getMap(Send send) {
        Map<String, String> map = new HashMap<>();
        map.put("send", MyGson.getInstance().toGsonStr(send));
        return map;
    }

    /**
     * 用户注册
     */
    public void appUserToRegister(String phoneName, String passWord, OkHttpManager.DataCallBack callBack, int netbs) {
        AppRegister appRegisterBean = new AppRegister(phoneName, passWord, PhoneUtil.getInstance().getDeviceId(), PhoneUtil.getInstance().getMac());
        Send send = getSendBean(NetRequstHead.APP_REGISTER, MyGson.getInstance().toGsonStr(appRegisterBean));
        OkHttpManager.postAsync(LHCXUrl.APP_REGISTER_URL, getMap(send), callBack, netbs);
    }

    /**
     * 用户登录
     */
    public void appUserToLogin(String loginStr, String passWord, OkHttpManager.DataCallBack callBack, int netbs) {
        AppLogin loginBean = new AppLogin(loginStr, passWord, PhoneUtil.getInstance().getDeviceId(), PhoneUtil.getInstance().getMac());
        Send send = getSendBean(NetRequstHead.APP_LOGIN, MyGson.getInstance().toGsonStr(loginBean));
        OkHttpManager.postAsync(LHCXUrl.APP_LOGIN_URL, getMap(send), callBack, netbs);
    }

    /**
     * 发送短信验证码
     */
    public void smsSendSucerityCode(String phoneName, OkHttpManager.DataCallBack callBack, int netbs) {
        Send send = getSendBean(NetRequstHead.SEND_SMS_SUCERITY_CODE, phoneName);
        OkHttpManager.postAsync(LHCXUrl.SMS_SEND_SUCERITY_CODY, getMap(send), callBack, netbs);
    }


    /**
     * 得到所有设备信息
     */
    public void getAllDeviceByUserId(String userId, OkHttpManager.DataCallBack callBack, int netbs) {
        Send send = getSendBean(NetRequstHead.DEVICE_QUE_BY_USERID, userId);
        OkHttpManager.postAsync(LHCXUrl.DEVICE_QUE_ALL_BY_USERID, getMap(send), callBack, netbs);
    }

    /**
     * 得到所有设备信息
     */
    public void deviceUpdate(DeviceBean deviceBean, OkHttpManager.DataCallBack callBack, int netbs) {
        Send send = getSendBean(NetRequstHead.DEVICE_UPDATE, MyGson.getInstance().toGsonStr(deviceBean));
        OkHttpManager.postAsync(LHCXUrl.DEVICE_UPDATE, getMap(send), callBack, netbs);
    }

    /**
     * 得到所有设备信息
     */
    public void deviceClear(DeviceBean deviceBean, OkHttpManager.DataCallBack callBack, int netbs) {
        Send send = getSendBean(NetRequstHead.DEVICE_CLEAR, MyGson.getInstance().toGsonStr(deviceBean));
        OkHttpManager.postAsync(LHCXUrl.DEVICE_CLEAR, getMap(send), callBack, netbs);
    }

    /**
     * 得到所有设备信息
     */
    public void deviceBondedUser(DeviceBean deviceBean, OkHttpManager.DataCallBack callBack, int netbs) {
        Send send = getSendBean(NetRequstHead.DEVICE_BONDED, MyGson.getInstance().toGsonStr(deviceBean));
        OkHttpManager.postAsync(LHCXUrl.DEVICE_BONDED, getMap(send), callBack, netbs);
    }



    /**
     * 得到所有设备信息
     */
    public void tempaltePublic(OkHttpManager.DataCallBack callBack, int netbs) {
        QueTemplateBean queTemplateBean = new QueTemplateBean(true, 99, "");
        Send send = getSendBean(NetRequstHead.TEMPLATE_QUE, MyGson.getInstance().toGsonStr(queTemplateBean));
        OkHttpManager.postAsync(LHCXUrl.TEMPLATE_QUE, getMap(send), callBack, netbs);
    }

    /**
     * 得到私有模板的信息
     */
    public void tempaltePrivate(String userId, OkHttpManager.DataCallBack callBack, int netbs) {

        QueTemplateBean queTemplateBean = new QueTemplateBean(true, 1, userId);
        Send send = getSendBean(NetRequstHead.TEMPLATE_QUE, MyGson.getInstance().toGsonStr(queTemplateBean));
        OkHttpManager.postAsync(LHCXUrl.TEMPLATE_QUE, getMap(send), callBack, netbs);
    }

    /**
     * 得到所有设备信息
     */
    public void issueQue(String userId, OkHttpManager.DataCallBack callBack, int netbs) {
        Send send = getSendBean(NetRequstHead.ISSUE_QUE, userId);
        OkHttpManager.postAsync(LHCXUrl.ISSUE_QUE, getMap(send), callBack, netbs);
    }

    /**
     * 添加模板信息
     */
    public void tempalteAdd(TemplateBean tempalteAdd, OkHttpManager.DataCallBack callBack, int netbs) {
        Send send = getSendBean(NetRequstHead.TEMPLATE_ADD, MyGson.getInstance().toGsonStr(tempalteAdd));
        OkHttpManager.postAsync(LHCXUrl.TEMPLATE_ADD, getMap(send), callBack, netbs);
    }


}
