package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.china.kuaiyou.R;
import com.china.kuaiyou.mybase.BaseCompatActivity;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class AllLoginActivity extends BaseCompatActivity implements View.OnClickListener {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_all_login_layout, null);
        setContentView(view);
        initView();
        initData();
    }

    private void initView() {
        view.findViewById(R.id.bt_qq_login).setOnClickListener(this);
        view.findViewById(R.id.bt_weixin_login).setOnClickListener(this);
        view.findViewById(R.id.bt_phone_login).setOnClickListener(this);
        view.findViewById(R.id.tv_other_login).setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_qq_login:
                showToast("尚未开放该功能");
                break;
            case R.id.bt_weixin_login:
                showToast("尚未开放该功能");
                break;
            case R.id.bt_phone_login:
                toActivity(LoginActivty.class);
                break;
            case R.id.tv_other_login:
                toActivity(LoginActivty.class);
                break;
        }
    }
}
