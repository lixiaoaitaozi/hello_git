package com.china.kuaiyou.activity.fragments;


import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.adapter.PrivateLetterAdapter;
import com.china.kuaiyou.bean.PrivatesLetterBean;
import com.china.kuaiyou.util.BitmapUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;


/**
 * @className: PrivateLetterFragment
 * @purpose: 私信的碎片
 * @athor :
 * @date:2017/11/8 21:25
 */
public class PrivateLetterFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.show_privates_letter_list_view)
    private ListView showLetterView;
    private PrivateLetterAdapter adapter;
    private List<PrivatesLetterBean> list;

    @Override
    protected int getLayout() {
        return R.layout.fragment_private_letter;
    }

    @Override
    protected void initObject() {
        getData();
        adapter = new PrivateLetterAdapter(list, getActivity());
        showLetterView.setAdapter(adapter);
    }

    private void getData() {
        list = new ArrayList<>();
        PrivatesLetterBean pb = null;
        for (int i = 0; i < 4; i++) {
            pb = new PrivatesLetterBean();
            pb.setId(i);
            pb.setLetterName("快友官方账号" + i);
            pb.setContents("快友叫你回家了" + i);
            pb.setDataTimes(i + "天前");
            pb.setIcon(BitmapUtils.scale(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), 60, 60, false));
            list.add(pb);
        }

    }


    @OnItemClick({R.id.show_privates_letter_list_view})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        print("进入 消息");
    }


}
