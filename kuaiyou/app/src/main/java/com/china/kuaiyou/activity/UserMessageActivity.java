package com.china.kuaiyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.fragments.LovesFragment;
import com.china.kuaiyou.activity.fragments.SecretsFragment;
import com.china.kuaiyou.activity.fragments.WorksFragment;
import com.china.kuaiyou.adapter.MenuApadter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: UserMessageActivity
 * @purpose: 用户信息界面
 * @athor :
 * @date:2017/11/12 16:56
 */
@ContentView(R.layout.activity_user_message)
public class UserMessageActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "====" + UserMessageActivity.class.getSimpleName();

    @ViewInject(R.id.iv_title_back)
    private ImageView blockImage;
    @ViewInject(R.id.tv_title)
    private TextView titleV;
    @ViewInject(R.id.shareimage_view)
    private ImageView shareImage;
    @ViewInject(R.id.start_compile_button)
    private Button compileButton;
    @ViewInject(R.id.comlile_text_view)
    private TextView compileTextV;
    @ViewInject(R.id.way_change_view)
    private CheckBox changeV;
    @ViewInject(R.id.user_message_view_pager)
    private ViewPager showUserView;

    @ViewInject(R.id.works_text_view)
    private TextView worksV;
    @ViewInject(R.id.secret_text_view)
    private TextView secretV;
    @ViewInject(R.id.love_text_view)
    private TextView loveV;

    private String userID;
    private String userName;

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("userID");
        userName = bundle.getString("userName");
        titleV.setText(userName);
        changeV.setOnCheckedChangeListener(this);
        Log.e(TAG, "initObject: " + userID);
    }

    @Override
    protected void initObject() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new WorksFragment());
        fragmentList.add(new SecretsFragment());
        fragmentList.add(new LovesFragment());
        MenuApadter mp = new MenuApadter(getSupportFragmentManager(), fragmentList);
        showUserView.setOffscreenPageLimit(0);
        showUserView.setAdapter(mp);
        showUserView.addOnPageChangeListener(new PageChang());
    }


    @OnClick({R.id.iv_title_back, R.id.shareimage_view, R.id.start_compile_button,
            R.id.comlile_text_view, R.id.works_text_view, R.id.secret_text_view,
            R.id.love_text_view})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareimage_view:
                print("点击分享");
                break;
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.start_compile_button:
                startCompile();
                break;
            case R.id.comlile_text_view:
                startCompile();
                break;
            case R.id.works_text_view:
                menuChangs(0);
                showUserView.setCurrentItem(0);
                break;
            case R.id.secret_text_view:
                menuChangs(1);
                showUserView.setCurrentItem(1);
                break;
            case R.id.love_text_view:
                menuChangs(2);
                showUserView.setCurrentItem(2);
                break;
        }
    }

    /**
     * 跳转编辑界面
     */
    private void startCompile() {
        Intent intent = new Intent(this, CompileUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userIDs", userID);
        bundle.putString("userNames", userName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Log.e(TAG, "onCheckedChanged: 显示全部");
        } else {
            Log.e(TAG, "onCheckedChanged: 显示缩略");
        }
    }


    /**
     * 监听ViewPager变化 状态
     */
    class PageChang implements ViewPager.OnPageChangeListener {

        /**
         * 当页面在滑动的时候会调用此方法在滑动被停止之前，此方法回一直得到
         *
         * @param position             当前页面，及你点击滑动的页面
         * @param positionOffset       当前页面偏移的百分比
         * @param positionOffsetPixels 当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        /**
         * 页面跳转完后得到调用
         *
         * @param position 是你当前选中的页面的Position
         */
        @Override
        public void onPageSelected(int position) {
            menuChangs(position);
        }

        /**
         * 在状态改变的时候调用
         *
         * @param state 有三种状态（0，1，2）
         */
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    /**
     * ViewPager的子体变化时菜单跟随变化
     *
     * @param position 变化的目标
     */
    private void menuChangs(int position) {
        worksV.setTextColor(0xc000ff00);
        secretV.setTextColor(0xc000ff00);
        loveV.setTextColor(0xc000ff00);
        switch (position) {
            case 0:
                worksV.setTextColor(0xff000000);
                break;
            case 1:
                secretV.setTextColor(0xff000000);
                break;
            case 2:
                loveV.setTextColor(0xff000000);
                break;
        }
    }

}
