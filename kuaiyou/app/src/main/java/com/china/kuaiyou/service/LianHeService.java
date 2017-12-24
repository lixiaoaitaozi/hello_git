package com.china.kuaiyou.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.china.kuaiyou.huanxin.HXSubscriptionSubject;
import com.china.kuaiyou.mybase.LG;
import com.china.kuaiyou.oss.OssManager;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.util.List;

/**
 * Created by xiefuning on 2017/5/10.
 * about:
 */

public class LianHeService extends Service {
    private IBinder iBinder = new LianHeService.LianHeServiceBinder();
    private OssManager ossManager;
    private String tag = "LianHeService>>>>>>>>";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LG.i("----", "服務啟動了");
        ossManager = OssManager.getInstance();
        ossManager.init();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LG.i("----", "服務开始了");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public void upload(String queStr, String netUrl, String localUrl, OssManager.OssUploadImp ossUploadImp) {
        LG.i("", "------star upload"+(ossManager==null));
        ossManager.upload(queStr, netUrl, localUrl, ossUploadImp);
    }


    public class LianHeServiceBinder extends Binder {
        public LianHeService getService() {
            return LianHeService.this;
        }
    }

    private EMMessageListener msgListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {

            //get message
            for (EMMessage message : messages) {
                /**
                 * the message is read?
                 */
                boolean isRead = true;
                if (message.direct() == EMMessage.Direct.RECEIVE) {
                    isRead = true;
                } else if (message.direct() == EMMessage.Direct.SEND) {
                    isRead = false;
                }

                /**
                 *  is xiaoge relocation the message type
                 *  0 admin , 1 user , 2 device , 3 service
                 */
                int messageType = 99;
                String str = message.getUserName().substring(0, 1);
                if (message.getUserName().equals("admin")) {
                    messageType = 0;
                } else if (str.equals("u")) {
                    messageType = 1;
                } else if (str.equals("d")) {
                    messageType = 2;
                } else if (str.equals("s")) {
                    messageType = 3;
                }
                LG.i(tag, "消息来源是否是读的" + isRead + "消息的来源中类似为" + messageType);
                switch (message.getType()) {
                    case TXT:

                        EMTextMessageBody emTextMessageBody = (EMTextMessageBody) message.getBody();
                        LG.i(tag, "收到的消息为MAIN：" + emTextMessageBody.getMessage());
                        HXSubscriptionSubject.getInstence().notify(emTextMessageBody.getMessage(), message.getFrom());

                        break;
                    case IMAGE:

                        EMImageMessageBody emImageMessageBody = (EMImageMessageBody) message.getBody();
                        break;
                    case VIDEO:
                        EMVideoMessageBody emVideoMessageBody = (EMVideoMessageBody) message.getBody();
                        break;
                    case LOCATION:
                        EMLocationMessageBody emLocationMessageBody = (EMLocationMessageBody) message.getBody();
                        break;
                    case VOICE:
                        EMVoiceMessageBody emVoiceMessageBody = (EMVoiceMessageBody) message.getBody();
                        break;
                    case FILE:
                        EMFileMessageBody emFileMessageBody = (EMFileMessageBody) message.getBody();
                        break;
                    case CMD:
                        EMCmdMessageBody emCmdMessageBody = (EMCmdMessageBody) message.getBody();
                        break;
                }
            }


        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LG.i(tag, messages.toString());
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            LG.i(tag, messages.toString());
        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {
            //收到已送达回执
            LG.i(tag, messages.toString());
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            LG.i(tag, change.toString());
        }
    };
}
