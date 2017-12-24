package com.china.kuaiyou.activity.fragments;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.EnrollNextActivity;
import com.china.kuaiyou.activity.view.ClearEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


/**
 * @className: EnrollFragment
 * @purpose: 注册碎片 页面
 * @athor :
 * @date:2017/11/11 13:24
 */
public class EnrollFragment extends BaseFragment implements View.OnClickListener, ClearEditText.inputListener {

    @ViewInject(R.id.area_show_view)
    private Button codeButton;
    @ViewInject(R.id.phone_text_view)
    private ClearEditText phoneV;
    @ViewInject(R.id.next_one_button)
    private Button nextOneV;
    @ViewInject(R.id.sina_image_view)
    private ImageView sinaImage;
    @ViewInject(R.id.weichat_image_view)
    private ImageView weiChatImaga;
    @ViewInject(R.id.qq_image_view)
    private ImageView qqImage;

    @Override
    protected int getLayout() {
        return R.layout.fragment_enroll;
    }

    @Override
    protected void initView() {
        phoneV.setIl(this);
    }

    @OnClick({R.id.area_show_view, R.id.next_one_button, R.id.sina_image_view,
            R.id.weichat_image_view, R.id.qq_image_view})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_one_button:
                Intent intent = new Intent(getActivity(), EnrollNextActivity.class);
                intent.putExtra("phone", phoneV.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.area_show_view:
                break;
            case R.id.qq_image_view:
                break;
            case R.id.weichat_image_view:
                break;
            case R.id.sina_image_view:
                break;
        }
    }

    @Override
    public void isInput() {
        nextOneV.setBackgroundResource(R.drawable.button_gray_style);
        nextOneV.setEnabled(true);
    }

    @Override
    public void noInput() {
        nextOneV.setBackgroundResource(R.drawable.button_thin_gray_style);
        nextOneV.setEnabled(false);
    }
}
