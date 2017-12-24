package com.china.kuaiyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.mybase.MySharePreferences;
import com.china.kuaiyou.netbean.user.UserBean;
import com.china.kuaiyou.zxing.app.CaptureActivity;

import java.io.File;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class MeActivity extends BaseCompatActivity implements View.OnClickListener {

    private UserBean appUserBean;
    String nameStr = "";
    //head
    private ImageView headIV;
    private TextView nameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_layout);
        initTitle("个人设置");
        initData();
        initView();

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


    private void initView() {
        //head管理
        headIV = (ImageView) findViewById(R.id.iv_me_icon);
        MyGlide.getInstance().setRoundedBitMap(this, headIV, R.drawable.me_avatar);
        nameTV = (TextView) findViewById(R.id.tv_me_user_name_view);
        nameTV.setText(nameStr);
        findViewById(R.id.ll_me_to_message).setOnClickListener(this);
        findViewById(R.id.ll_me_to_kuaiyoubi).setOnClickListener(this);
        findViewById(R.id.ll_me_to_find).setOnClickListener(this);
        findViewById(R.id.ll_me_to_set).setOnClickListener(this);
        findViewById(R.id.ll_me_to_videos).setOnClickListener(this);
        findViewById(R.id.ll_me_to_camera).setOnClickListener(this);
        findViewById(R.id.me_message_layout).setOnClickListener(this);
        findViewById(R.id.me_private_letter_layout).setOnClickListener(this);
        findViewById(R.id.iv_me_icon).setOnClickListener(this);
    }

    private void initData() {
        appUserBean = MyGson.getInstance().fromJson(MySharePreferences.getInstance().getString("appuser"), UserBean.class);
        StringBuilder sb = new StringBuilder();
        sb.append("用户");
        sb.append("   ");
        if (TextUtils.isEmpty(appUserBean.getName())) {
            sb.append(appUserBean.getId());
        } else {
            sb.append(appUserBean.getName());
        }
        nameStr = sb.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_me_to_message:
                toActivity(MessageActivity.class, 0 + "");
                break;
            case R.id.ll_me_to_kuaiyoubi:
                toActivity(KuaiYouBiActivity.class);
                break;
            case R.id.ll_me_to_find:
                toActivity(FindActivity.class);
                break;
            case R.id.ll_me_to_set:
                toActivity(SettingsActivity.class);
                break;
            case R.id.ll_me_to_videos:
                toActivity(MyVideosActivity.class);
                break;
            case R.id.ll_me_to_camera:
                toActivity(CaptureActivity.class);
                break;
            case R.id.me_message_layout://消息
                toActivity(MessageActivity.class, 1 + "");
                break;
            case R.id.me_private_letter_layout://私信
                toActivity(MessageActivity.class, 2 + "");
                break;
            case R.id.iv_me_icon:
                Intent intent = new Intent(this, UserMessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("uesrID", appUserBean.getId());
                bundle.putString("userName", appUserBean.getUserName());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
