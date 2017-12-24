package com.china.kuaiyou.activity.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.AddressActivity;
import com.china.kuaiyou.activity.UserMessageActivity;
import com.china.kuaiyou.adapter.AddressBookAdapter;
import com.china.kuaiyou.bean.AddressBookBean;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;


/**
 * @className: FindUserFragment
 * @purpose: 查找的用户碎片
 * @athor :
 * @date:2017/11/10 22:03
 */
public class FindUserFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, AddressBookAdapter.ButtonListener {

    private static final String TAG = "===" + FindUserFragment.class.getSimpleName();

    @ViewInject(R.id.show_address_book_view)
    private ListView addressBookV;//通讯录显示控件

    private AddressBookAdapter abookAdapter;//通讯录适配器
    private List<AddressBookBean> list;//数据源

    @ViewInject(R.id.flush_layout)
    private SwipeRefreshLayout finshView;
    private Handler finshHandle;

    @ViewInject(R.id.phone_address_view)
    private LinearLayout addressLayout;

    @Override
    protected int getLayout() {
        return R.layout.fragment_find_user;
    }

    @Override
    protected void initView() {
        finshView.setOnRefreshListener(this);
        finshView.setColorSchemeColors(getResources().getColor(R.color.help_button_view),
                getResources().getColor(android.R.color.holo_green_light), getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
    }

    @Override
    protected void initObject() {
        finshHandle = new FiushHandler();
        getData();
        abookAdapter = new AddressBookAdapter(list, getActivity());
        addressBookV.setAdapter(abookAdapter);
        abookAdapter.setButtonListener(this);
    }

    /**
     * 获取数据源
     */
    private void getData() {
        list = new ArrayList<>();
        AddressBookBean ab = null;
        for (int i = 0; i < 10; i++) {
            ab = new AddressBookBean();
            ab.setId(i);
            ab.setIconPath(null);
            ab.setUserName("用户" + i);
            ab.setSource("我觉得还是可以商量下的");
            ab.setUserID(i);
            ab.setIsAttention(0);
            list.add(ab);
        }
    }

    @OnClick({R.id.phone_address_view})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_address_view:
                toActivity(AddressActivity.class);
                break;
        }
    }

    @OnItemClick({R.id.show_address_book_view})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        print("点击了吧");
        Intent intent = new Intent(getActivity(), UserMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("uesrID", list.get(position).getUserID() + "");
        bundle.putString("userName", list.get(position).getUserName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        finshHandle.sendEmptyMessageDelayed(1, 10000);
    }

    @Override
    public void buttonClick(View view, int position) {
        print("点击关注");
    }

    /**
     * 限制刷新时间的通知 处理
     */
    class FiushHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    finshView.setRefreshing(false);
                    break;
            }
        }
    }
}
