package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @className: CompileUserActivity
 * @purpose: 编辑用户资料界面
 * @athor :
 * @date:2017/11/12 18:12
 */
@ContentView(R.layout.activity_compile_user)
public class CompileUserActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.iv_title_back)
    private ImageView blockV;
    @ViewInject(R.id.tv_title)
    private TextView titleV;
    @ViewInject(R.id.yes_image_view)
    private ImageView ascertainButton;

    @ViewInject(R.id.nick_layout)
    private LinearLayout nickLayout;
    @ViewInject(R.id.user_id_layout)
    private LinearLayout userIDLayout;
    @ViewInject(R.id.code_layout)
    private LinearLayout codeLayout;
    @ViewInject(R.id.sex_layout)
    private LinearLayout sexLayout;
    @ViewInject(R.id.birthday_layout)
    private LinearLayout birthdayLayout;
    @ViewInject(R.id.location_layout)
    private LinearLayout lcationLayout;

    @ViewInject(R.id.introduction_edit_view)
    private EditText introductionEditV;
    @ViewInject(R.id.nick_text)
    private TextView nickTextV;
    @ViewInject(R.id.user_id_text_view)
    private TextView userIdTextV;
    @ViewInject(R.id.sex_text_view)
    private TextView sexTextView;
    @ViewInject(R.id.birthdat_text_view)
    private TextView birthdayTextV;
    @ViewInject(R.id.code_text_view)
    private TextView codeTextV;

    private String userID;
    private String userName;

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("userIDs");
        userName = bundle.getString("userNames");
        titleV.setText("编辑个人资料");
        if (userName == null)
            nickTextV.setText("自己起名字");
        else
            nickTextV.setText(userName);
        if (userID == null)
            userIdTextV.setText("没有ID");
        else
            userIdTextV.setText(userID);
    }

    @OnClick({R.id.iv_title_back, R.id.yes_image_view, R.id.nick_layout, R.id.user_id_layout
            , R.id.code_layout, R.id.sex_layout, R.id.birthday_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_image_view:
                print("确定提交信息");
                break;
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.nick_layout:
                break;
            case R.id.user_id_layout:
                break;
            case R.id.code_layout:
                toActivity(RqCodeActivity.class);

                break;
            case R.id.sex_layout:
                break;
            case R.id.birthday_layout:
                break;
            case R.id.location_layout:
                break;
        }
    }
}
