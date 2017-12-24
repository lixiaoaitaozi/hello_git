package com.china.kuaiyou.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.china.kuaiyou.appliction.MyApplication;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class ScreenUtil {
    private WindowManager windowManager;
    public ScreenUtil(Activity activity){
        windowManager=activity.getWindowManager();
    }

    /**
     *  size.X為宽
     *  size.y为长
     */
    public Point getPoint(){
        Display display = windowManager.getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * 當前是否豎屏
     * @return
     */
    public boolean screenIdVertical(){
        if (MyApplication.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        } else if(MyApplication.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
              return true;
        }
        return false;
    }
}
