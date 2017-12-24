package com.china.kuaiyou.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @className: LawClauseActivity
 * @purpose: 法律条款活动界面
 * @athor :
 * @date:2017/11/11 17:15
 */
@ContentView(R.layout.activity_law_clause)
public class LawClauseActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "====" + LawClauseActivity.class.getSimpleName();
    @ViewInject(R.id.iv_title_back)
    private ImageView blockButton;
    @ViewInject(R.id.tv_title)
    private TextView titleV;

    @Override
    protected void initView() {
        titleV.setText("法律条款");
    }

    @OnClick({R.id.iv_title_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
        }
    }
}
