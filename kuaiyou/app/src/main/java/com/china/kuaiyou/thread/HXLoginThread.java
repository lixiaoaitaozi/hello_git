package com.china.kuaiyou.thread;

import com.china.kuaiyou.mybase.LG;
import com.china.kuaiyou.netbean.user.UserBean;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class HXLoginThread extends Thread{

    private String tag="HXLoginThread";
    private UserBean userBean;
    public HXLoginThread(UserBean userBean){
        this.userBean=userBean;
    }

    @Override
    public void run() {
        super.run();

        try {
            EMClient.getInstance().createAccount("u" + userBean.getId(), "p" + userBean.getId());
            LG.i(tag, "注册成功！" + "u" + userBean.getId());
        } catch (HyphenateException e) {
            LG.i(tag, "注册失败！" + userBean.getId());

        } finally {
            try {
                EMClient.getInstance().login("u" + userBean.getId(), "p" + userBean.getId(), new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        LG.i(tag, "登录成功！");
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {

                        LG.i(tag, "登录失败！" + code + message);
                    }
                });
                EMClient.getInstance().chatManager().loadAllConversations();
            } catch (Exception e) {

            }
        }
    }


}
