package com.china.kuaiyou.mybase;

import android.app.Activity;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

public class ActivityClose implements UncaughtExceptionHandler {
    private final String tag = this.getClass().toString() + ">>>"; // LOG信息
    private List<Activity> list;
    private static ActivityClose activityClose;

    private ActivityClose() {
        list = new ArrayList<Activity>();
    }

    // 单例模式得到退出APP的实例
    public static ActivityClose getInstance() {
        if (activityClose == null) {
            activityClose = new ActivityClose();
        }
        return activityClose;
    }

    // 得到所有进行过，并且没有退出活动的实例
    public List<Activity> getlist() {
        if (list == null) {
            activityClose = new ActivityClose();
        }
        return list;
    }

    // 添加所有活动实例
    public void add(Activity activity) {
        list.add(activity);

    }

    // 删除活动实例
    public void delete(Activity activity) {
        LG.i("退出了一个活动>>>>", activity);
        if (activity.isFinishing() == false)
            activity.finish();
        list.remove(activity);
//        if (list.size() == 1)
//            MobclickAgent.onKillProcess(activity);
    }

    // 关闭所有活动，退出APP
    public void closeAll() {
        for (int i = 0; i < list.size(); i++) {
            Activity activity = list.get(i);
            if (activity.isFinishing() == false) {
                activity.finish();
            }
        }

    }

    // 弹窗显示所有活动的实例,然并卵
    public void getAllActivity() {
        for (int i = 0; i < activityClose.list.size(); i++) {
            Activity activity = activityClose.list.get(i);
            Toast.makeText(activity, activity.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // TODO Auto-generated method stub

    }

}
