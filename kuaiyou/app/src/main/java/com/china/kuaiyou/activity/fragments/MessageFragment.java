package com.china.kuaiyou.activity.fragments;


import android.view.View;
import android.widget.Button;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.FindActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @className: MessageFragment
 * @purpose: 消息的碎片
 * @athor :
 * @date:2017/11/8 21:24
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "===" + MessageFragment.class.getSimpleName();

    @ViewInject(R.id.look_find_button)
    private Button lookFindButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_message;
    }


    @OnClick({R.id.look_find_button, R.id.look_find_layouot})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.look_find_button:
                toActivity(FindActivity.class);
                break;
        }
    }
}
