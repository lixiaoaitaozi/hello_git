package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.video.TaoZiVideoPlay;
import com.china.kuaiyou.video.TaoZiVideoPlayView;
import com.china.kuaiyou.video.imp.GetTaoZiVideoPlayImp;
import com.china.kuaiyou.video.imp.TaoZiVideoStatuImp;

/**
 * Created by Administrator on 2017/9/12 0012.
 */

public class VideoActivity extends BaseCompatActivity {
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_layout);
        myWindowSet.setFlatStyleColoredBars();
        initTitle("视频显示");
        url = getIntentString();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.iv_video_show);
        TaoZiVideoStatuImp taoZiVideoStatuImp = new TaoZiVideoStatuImp() {
            @Override
            public void videoIsOk() {

            }

            @Override
            public void videoCache(int percent) {

            }

            @Override
            public void videoException(String exceptionStr) {

            }

            @Override
            public void videoPlay() {

            }

            @Override
            public void videoNoFile(String filePath) {

            }

            @Override
            public void videoPause() {

            }

            @Override
            public void videoOver() {

            }

            @Override
            public void videoPlayCurrentPosition(double percentage, int currentPosition, int duration) {

            }
        };
        GetTaoZiVideoPlayImp getTaoZiVideoPlayImp = new GetTaoZiVideoPlayImp() {
            @Override
            public void getVideoPlay(TaoZiVideoPlay taoZiVideoPlay) {
                if (!TextUtils.isEmpty(url)){
                    showToast(url);
                    taoZiVideoPlay.setPlayUrl(url,VideoActivity.this);
                }


            }
        };
        TaoZiVideoPlayView taoZiVideoPlayView = new TaoZiVideoPlayView(surfaceView, getTaoZiVideoPlayImp, taoZiVideoStatuImp);
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
