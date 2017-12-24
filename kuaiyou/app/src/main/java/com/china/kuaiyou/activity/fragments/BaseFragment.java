package com.china.kuaiyou.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;

/**
 * 类名: BaseFragment
 * 注释: 父类碎片
 * 时间: 2017/8/26 13:52
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(getLayout(), null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initObject();
    }


    /**
     * @return 获取布局
     */
    protected int getLayout() {
        return 0;
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


    protected void toActivity(Class<?> c) {
        Intent i = new Intent(getActivity(), c);
        startActivity(i);
    }

    /**
     * 弹出提示
     */
    protected void print(String obj) {
        Toast.makeText(getActivity(), obj, Toast.LENGTH_LONG).show();
    }

    // 带数据跳转活动
    protected void toActivity(Class toClass, String gsonString) {
        Intent intent = new Intent(getActivity(), toClass);
        intent.putExtra("tb", gsonString);
        startActivity(intent);
    }

    //获得传的数据
    protected String getIntentString() {
        return getActivity().getIntent().getStringExtra("tb");
    }


}
