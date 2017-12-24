package com.china.kuaiyou.netutil;

import android.text.TextUtils;

import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.netbean.Msg;

/**
 * Created by xiefuning on 2017/4/12.
 * about:
 */

public class NetToGet {
    private static NetToGet get;
    private final String tag = this.getClass().toString() + ">>>"; // LOG信息
    private NetToGet() {

    }

    public static NetToGet getInstance() {
        if (get == null)
            get = new NetToGet();
        return get;
    }

    public Msg getMessageBean(String gsonStr) {
        Msg msg=null;
        if(TextUtils.isEmpty(gsonStr)){
            msg= new Msg("",0,"返回数据为空");
        }else{
            msg= MyGson.getInstance().fromJson(gsonStr,Msg.class);
            if(msg==null)
                msg=new Msg("",0,"服务器回传消息错误");
        }
        return msg;
    }



}
