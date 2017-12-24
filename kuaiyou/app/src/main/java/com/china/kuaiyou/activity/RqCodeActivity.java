package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.mybase.MySharePreferences;
import com.china.kuaiyou.netbean.user.UserBean;
import com.china.kuaiyou.zxing.util.ErWeiMa;
import com.china.kuaiyou.zxing.util.Utils;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class RqCodeActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rqcode_layout);
        myWindowSet.setScreenFull();
        myWindowSet.setFlatStyleColoredBars();
        initTitle("二维码");
        ImageView rqIV = (ImageView) findViewById(R.id.iv_rq);
        UserBean appUser = MyGson.getInstance().fromJson(MySharePreferences.getInstance().getString("appuser"), UserBean.class);
        if (appUser != null) {
            ErWeiMa.getInstance().createQRImage(appUser.getId(), rqIV);
        }

    }

    private void initTitle(String titleString) {
        ImageView titleBackIV = (ImageView) findViewById(R.id.iv_title_back);
        titleBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView titleTV = (TextView) findViewById(R.id.tv_title);
        titleTV.setText(titleString);
    }
}
