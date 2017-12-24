package com.china.kuaiyou.thread;

import android.os.Handler;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class WaitThread extends Thread {
    private Handler handler;
    private int waitNum;//等待次數
    private int sleepSecond;//等待的時間
    public WaitThread(Handler handler, int waitNum){
        this.handler=handler;
        this.waitNum=waitNum;
        this.sleepSecond=1000;
    }

    public WaitThread(Handler handler, int waitSecond, int sleepSecond){
        this.handler=handler;
        this.waitNum=waitNum;
        this.sleepSecond=sleepSecond;
    }

    @Override
    public void run() {
        super.run();
        for(int i=waitNum;i>=0;i--) {
            handler.sendEmptyMessage(i);
            if(i==0)
                break;
            try {
                new Thread().sleep(sleepSecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
