package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.BaseMainActivity;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class KuaiYouBiActivity extends BaseMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myWindowSet.setFlatStyleColoredBars();
        setContentView(R.layout.activity_kuaiyoubi_layout);
        initTitle();
    }

    private void initTitle() {
        ImageView backIV = (ImageView) findViewById(R.id.iv_title_back);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
