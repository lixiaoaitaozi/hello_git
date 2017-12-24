package com.china.kuaiyou.mybase;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.china.kuaiyou.R;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public abstract class BaseMainActivity extends BaseCompatActivity {
    private ImageView titleStatuIV;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        myWindowSet.hindBottomNavigation(view);
        titleStatuIV = view.findViewById(R.id.iv_title_statu);
        showTitle();
        initView();
        initData();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @TargetApi(21)
    public void showTitle() {
        if (titleStatuIV != null) {
            titleStatuIV.setVisibility(View.VISIBLE);
        }
    }


    protected abstract void initView();

    protected abstract void initData();
}
