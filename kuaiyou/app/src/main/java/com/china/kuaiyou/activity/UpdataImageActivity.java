package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.netbean.Msg;
import com.china.kuaiyou.netutil.NetToGet;
import com.china.kuaiyou.netutil.NetToSend;
import com.china.kuaiyou.okhttp.OkHttpManager;
import com.china.kuaiyou.thread.WaitThread;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class UpdataImageActivity extends BaseCompatActivity implements View.OnClickListener {


    private View view;
    private String imageUrl;
    private boolean canUpdate;
    private ImageView showImageIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_updateimage_layout, null);
        setContentView(view);
        initTitle("修改图片");
        initView();
        initDate();
        viewSetData();
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

    private void initDate() {
        imageUrl=getIntentString();
        canUpdate=!TextUtils.isEmpty(imageUrl);
    }

    private void initView() {
        showImageIV=view.findViewById(R.id.iv_show_image);
        view.findViewById(R.id.tv_gaosimohu).setOnClickListener(this);
        view.findViewById(R.id.tv_nuanseqingdiao).setOnClickListener(this);
        view.findViewById(R.id.tv_fenhonghuiyi).setOnClickListener(this);
        view.findViewById(R.id.tv_heibaijiaocuo).setOnClickListener(this);
        view.findViewById(R.id.tv_tianjiawenzi).setOnClickListener(this);
    }

    private void viewSetData(){
        if(canUpdate){
            MyGlide.getInstance().setBitMap(context,showImageIV,imageUrl);
        }
    }


    @Override
    public void onClick(View view) {
        if(canUpdate){
            switch (view.getId()){
                case R.id.tv_gaosimohu:
                    showToast("设置高斯模糊效果中");
                    MyGlide.getInstance().setBitMapGaoSiMohu(this, showImageIV, imageUrl);
                    break;
                case R.id.tv_nuanseqingdiao:
                    MyGlide.getInstance().setBitMapNuanSeQingDiao(this, showImageIV, imageUrl);
                    break;
                case R.id.tv_fenhonghuiyi:
                    MyGlide.getInstance().setBitMapFenHongHuiYi(this, showImageIV, imageUrl);
                    break;
                case R.id.tv_heibaijiaocuo:
                    MyGlide.getInstance().setBitMapHeiSeJiaoCuo(this, showImageIV, imageUrl);
                    break;
                case R.id.tv_tianjiawenzi:
                    MyGlide.getInstance().setBitMapTianJiaWenZi(this, showImageIV, imageUrl);
                    break;
            }
        }
    }
}
