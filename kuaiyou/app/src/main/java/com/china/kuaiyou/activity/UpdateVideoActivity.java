package com.china.kuaiyou.activity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.adapter.UpdataVideoRVAdapter;
import com.china.kuaiyou.appliction.MyApplication;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.util.TimeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class UpdateVideoActivity extends BaseCompatActivity {
    private List<String> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatevideo_layout);

        recyclerView = (RecyclerView) findViewById(R.id.rv_updatevideo);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getBitmapsFromVideo(getIntentString());
    }

    public void getBitmapsFromVideo(String filePath) {
        list = new ArrayList<>();
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filePath);
// 取得视频的长度(单位为毫秒)
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
// 取得视频的长度(单位为秒)
        int seconds = Integer.valueOf(time) / 1000;
// 得到每一秒时刻的bitmap比如第一秒,第二秒
        for (int i = 1; i <= seconds; i++) {
            Bitmap bitmap = retriever.getFrameAtTime(i * 1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            String path = MyApplication.pic_files + File.separator + i + "--" + TimeUtil.getNowTimeLong() + ".jpg";
            list.add(path);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        UpdataVideoRVAdapter updataVideoRVAdapter=new UpdataVideoRVAdapter(this,list);
        recyclerView.setAdapter(updataVideoRVAdapter);
    }

}
