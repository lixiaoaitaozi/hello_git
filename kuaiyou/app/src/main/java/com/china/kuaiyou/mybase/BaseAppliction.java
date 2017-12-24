package com.china.kuaiyou.mybase;

import android.app.Application;
import android.content.Context;


public class BaseAppliction extends Application {
    private static BaseAppliction baseAppliction;

    public BaseAppliction() {
        // TODO Auto-generated constructor stub

    }

    public static Context getContext(){
        return baseAppliction;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
//		MyExceptionSearch.getInstance().init(context);
        baseAppliction=this;
        ActivityClose.getInstance();
    }


}
