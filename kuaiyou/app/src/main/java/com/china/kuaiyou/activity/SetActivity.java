package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.mybase.MySharePreferences;
import com.china.kuaiyou.netbean.user.UserBean;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class SetActivity extends BaseCompatActivity {
    private UserBean appUserBean;
    private ImageView headIV;
    private TextView nameTV, sexTV, shopPlaceTV, introductionTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_layout);
        myWindowSet.setFlatStyleColoredBars();
        initTitle("我的信息");
        initDate();
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void initTitle(String titleString) {
        ImageView titleBackIV = (ImageView) findViewById(R.id.iv_title_back);
        titleBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView titleTV = (TextView) findViewById(R.id.tv_title);
        titleTV.setText(titleString);
    }

    private void initDate() {
        appUserBean = MyGson.getInstance().fromJson(MySharePreferences.getInstance().getString("appuser"), UserBean.class);
    }

    private void initView() {
        headIV = (ImageView) findViewById(R.id.iv_icon_head);
        nameTV = (TextView) findViewById(R.id.tv_name);
        if (TextUtils.isEmpty(appUserBean.getName())) {
            nameTV.setText("未命名");
        } else {
            nameTV.setText(appUserBean.getName());
        }
        sexTV = (TextView) findViewById(R.id.tv_sex);
        sexTV.setText(appUserBean.getSex());
        shopPlaceTV = (TextView) findViewById(R.id.tv_shop_place);
//        shopPlaceTV.setText("深圳店");
        introductionTV = (TextView) findViewById(R.id.tv_introduction);
        introductionTV.setText(appUserBean.getIntroduction());
        Button aa = (Button) findViewById(R.id.aaa);
        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySharePreferences.getInstance().editorClear();
                finish();
                toActivity(WelcomeActivity.class);
            }
        });
    }
}
