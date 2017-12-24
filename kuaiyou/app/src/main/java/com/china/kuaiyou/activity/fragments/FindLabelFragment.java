package com.china.kuaiyou.activity.fragments;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @className: FindLabelFragment
 * @purpose: 查找的标签页面
 * @athor :
 * @date:2017/11/10 22:03
 */
public class FindLabelFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = "==" + FindLabelFragment.class.getSimpleName();
    @ViewInject(R.id.flush_layout)
    private SwipeRefreshLayout finshView;
    private Handler finshHandle;

    @ViewInject(R.id.one_show_list_view)
    private ListView oneListView;
    @ViewInject(R.id.two_show_list_view)
    private ListView twoListView;

    private String[] oneList;
    private String[] twoList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_find_label;
    }

    @Override
    protected void initView() {
        finshView.setOnRefreshListener(this);
        finshView.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        oneList = getResources().getStringArray(R.array.label_list_one);
        twoList = getResources().getStringArray(R.array.label_list_two);
        oneListView.setAdapter(new OneApadter(oneList, getActivity()));
        twoListView.setAdapter(new OneApadter(twoList, getActivity()));
    }

    @Override
    protected void initObject() {
        finshHandle = new FiushHandler();

    }

    @Override
    public void onRefresh() {
        finshHandle.sendEmptyMessageDelayed(1, 10000);
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

    class OneApadter extends BaseAdapter {
        private String[] list;
        private Context context;
        private LayoutInflater lf;

        public OneApadter(String[] list, Context context) {
            this.list = list;
            this.context = context;
            lf = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.length;
        }

        @Override
        public String getItem(int position) {
            return list[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = lf.inflate(R.layout.label_item_layout, null);
            TextView text = (TextView) convertView.findViewById(R.id.label_text_view);
            text.setText(getItem(position));
            return convertView;
        }
    }
}
