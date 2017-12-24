package com.china.kuaiyou.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @className: UserContractActivity
 * @purpose: 用户服务协议
 * @athor :
 * @date:2017/11/11 17:11
 */
@ContentView(R.layout.activity_user_contract)
public class UserContractActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "===" + UserContractActivity.class.getSimpleName();
    @ViewInject(R.id.tv_title)
    private TextView titleV;
    @ViewInject(R.id.iv_title_back)
    private ImageView blockButton;

    @Override
    protected void initView() {
        titleV.setText("用户服务协议基本条款");
    }

    @OnClick({R.id.iv_title_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
        }
    }
}
