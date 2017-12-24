package com.china.kuaiyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.lidroid.xutils.ViewUtils;

/**
 * 类名: BaseActivity
 * 注释: 活动父类
 * 时间: 2017/8/25 14:43
 */
public class BaseActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**初始XUtils框架的注解功能*/
        ViewUtils.inject(this);
        initView();
        initObject();
    }

    /**
     * 初始化控件
     */
    protected void initView() {
    }

    /**
     * 初始化对象
     */
    protected void initObject() {
    }

    /**
     * 授权结果
     */
    protected void authorization() {
    }

    /**
     * 页面跳转
     *
     * @param src    跳转目标
     * @param values 跳转类型
     */
    protected void toActivity(Class<?> src, int values) {
        Intent intent = new Intent(this, src);
        intent.putExtra("type", values);
        startActivity(intent);
    }

    /**
     * 弹出提示
     */
    protected void print(String obj) {
        Toast.makeText(this, obj, Toast.LENGTH_LONG).show();
    }

}
