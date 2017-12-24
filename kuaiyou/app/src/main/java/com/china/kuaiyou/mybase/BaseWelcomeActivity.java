package com.china.kuaiyou.mybase;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class BaseWelcomeActivity extends BaseCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myWindowSet.setScreenFull();
        myWindowSet.setFlatStyleColoredBars();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        myWindowSet.hindBottomNavigation(view);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

}
