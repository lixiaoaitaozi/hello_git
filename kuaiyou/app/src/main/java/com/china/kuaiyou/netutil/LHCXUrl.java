package com.china.kuaiyou.netutil;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class LHCXUrl {
    public static final String URL = "http://taozi-cn.cn/inno_cn_svservice/";//(稳定测试)
    //    public static final String URL = "http://10.0.0.65:8080/inno_cn_svservice/";//(稳定测试)
    public static final String APP_REGISTER_URL = URL + "user/merchantregister";//注册商户
    public static final String APP_LOGIN_URL = URL + "user/login";//登录统一接口
    public static final String APP_USER_UPDATE_URL = URL + "user/update";//修改
    public static final String SMS_SEND_SUCERITY_CODY = URL + "sms/sendsuceritycode";
    public static final String SMS_SEND_FINDPASSWORD_MESSAGE = URL + "sms/findpassword";
    public static final String DEVICE_QUE_ALL_BY_USERID = URL + "device/queAllByUserId";
    public static final String DEVICE_UPDATE = URL + "device/update";
    public static final String DEVICE_CLEAR = URL + "device/clear";
    public static final String DEVICE_BONDED = URL + "device/bonded";
    public static final String TEMPLATE_ADD = URL + "template/add";
    public static final String TEMPLATE_QUE = URL + "template/que";
    public static final String ISSUE_ADD = URL + "issue/add";
    public static final String ISSUE_QUE = URL + "issue/que";
    public static final String ISSUE_UPDATE = URL + "issue/update";// template que
    public static final String LIST_SCREEN_URL = URL + "listscreen";
    public static boolean isItem = false;
    public static final String IMEGA_HEAD = "http://innosmart2017.oss-cn-shenzhen.aliyuncs.com/";
}
