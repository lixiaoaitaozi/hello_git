package com.china.kuaiyou.activity.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.UserMessageActivity;
import com.china.kuaiyou.adapter.GossipAdapter;
import com.china.kuaiyou.bean.GossipBean;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: GossipFragment
 * @purpose: 八卦的碎片
 * @athor :
 * @date:2017/11/8 21:21
 */
public class GossipFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "==" + GossipFragment.class.getSimpleName();

    @ViewInject(R.id.show_gossip_list_view)
    private ListView showGossipV;
    private GossipAdapter gossipAdapter;
    private List<GossipBean> list;

    @Override
    protected int getLayout() {
        return R.layout.fragment_gossip;
    }

    @Override
    protected void initObject() {
        getData();
        gossipAdapter = new GossipAdapter(list, getActivity());
        showGossipV.setAdapter(gossipAdapter);
    }


    private void getData() {
        list = new ArrayList<>();
        GossipBean gossipBean = null;
        for (int i = 0; i < 5; i++) {
            gossipBean = new GossipBean();
            gossipBean.setId(i);
            gossipBean.setUserID(i);
            gossipBean.setUserName("用户" + i);

            if (i / 2 != 0 && i != 0) {
                gossipBean.setContents("发表了");
                gossipBean.setSignObject("多张图片");
                gossipBean.setDataType(2);
            } else {
                gossipBean.setContents("关注了");
                gossipBean.setSignObject("用户" + i);
                gossipBean.setDataType(1);
            }
            List<Bitmap> bitmapList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                bitmapList.add(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            }
            gossipBean.setList(bitmapList);
            list.add(gossipBean);
        }
    }

    @OnItemClick({R.id.show_gossip_list_view})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), UserMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("uesrID", list.get(position).getUserID() + "");
        bundle.putString("userName", list.get(position).getUserName());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
