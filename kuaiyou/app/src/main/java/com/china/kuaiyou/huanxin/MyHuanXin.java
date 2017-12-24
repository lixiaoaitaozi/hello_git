package com.china.kuaiyou.huanxin;

import com.china.kuaiyou.mybase.LG;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class MyHuanXin {
    public static void sendMessage(String contextStr, String toUserId) {
        LG.i("","----环信发送的内容为:"+contextStr);
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(contextStr, toUserId);
        message.setChatType(EMMessage.ChatType.Chat);
//发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }


    public void sendNeed(){

    }


}
