package com.china.kuaiyou.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.mybase.ActivityClose;
import com.china.kuaiyou.mybase.MySharePreferences;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @className: SettingsActivity
 * @purpose: 设置界面
 * @athor :
 * @date:2017/11/9 21:38
 */
@ContentView(R.layout.activity_settings)
public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.tv_title)
    private TextView title;

    @ViewInject(R.id.iv_title_back)
    private ImageView blockButton;

    @ViewInject(R.id.phone_layuout)
    private LinearLayout phoneLayout;
    @ViewInject(R.id.account_guard_layout)
    private LinearLayout accountGuadrLayout;
    @ViewInject(R.id.privates_set_layout)
    private LinearLayout privatesSetLayout;
    @ViewInject(R.id.cache_layout)
    private LinearLayout cacheLayout;
    @ViewInject(R.id.save_production_layout)
    private LinearLayout saveProductionLayout;
    @ViewInject(R.id.power_radio_layout)
    private LinearLayout powerLayout;
    @ViewInject(R.id.fans_layout)
    private LinearLayout fansLayout;
    @ViewInject(R.id.ticking_layout)
    private LinearLayout tickingLayout;
    @ViewInject(R.id.about_me_layout)
    private LinearLayout aboutLayout;
    @ViewInject(R.id.exit_land_buuton)
    private Button exitButton;

    @Override

    protected void initView() {
        title.setText("设置");
    }

    @OnClick({R.id.iv_title_back, R.id.phone_layuout, R.id.account_guard_layout, R.id.privates_set_layout,
            R.id.cache_layout, R.id.save_production_layout, R.id.power_radio_layout,
            R.id.fans_layout, R.id.ticking_layout, R.id.about_me_layout, R.id.exit_land_buuton})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.phone_layuout:
                break;
            case R.id.account_guard_layout:
                break;
            case R.id.privates_set_layout:
                break;
            case R.id.cache_layout:
                break;
            case R.id.save_production_layout:
                break;
            case R.id.power_radio_layout:
                break;
            case R.id.fans_layout:
                break;
            case R.id.ticking_layout:
                break;
            case R.id.about_me_layout:
                break;
            case R.id.exit_land_buuton:
                MySharePreferences.getInstance().editorClear();
                ActivityClose.getInstance().closeAll();
                toActivity(MainActivity.class);
                break;
        }
    }
}
