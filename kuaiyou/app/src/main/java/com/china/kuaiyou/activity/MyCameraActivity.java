package com.china.kuaiyou.activity;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.adapter.FragmentAdapter;
import com.china.kuaiyou.adapter.MyCaremaRVAdapter;
import com.china.kuaiyou.fileutil.FileInfo;
import com.china.kuaiyou.fileutil.MyFileSearch;
import com.china.kuaiyou.fragment.PhotosFragment;
import com.china.kuaiyou.fragment.ToCameraFragment;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.BaseFragment_v4;
import com.china.kuaiyou.util.ScreenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class MyCameraActivity extends BaseCompatActivity implements View.OnClickListener {
    //view
    private View view;
    private ViewPager vp;
    private TextView photosTV, toCameraTV;
    //model
    private PhotosFragment photosFragment;
    private ToCameraFragment toCameraFragment;
    private List<Fragment> fragments;
    //controller
    private FragmentAdapter fragmentAdapter;


    //view change handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TO_PHONOS_TYPE:
                    if (nowSelect != 0) {
                        photosTV.setBackgroundResource(R.color.gray);
                        toCameraTV.setBackgroundResource(R.color.white);
                        nowSelect = 0;
                        vp.setCurrentItem(nowSelect);
                    }
                    break;
                case TO_CAMERA_TYPE:
                    if (nowSelect != 1) {
                        toCameraTV.setBackgroundResource(R.color.gray);
                        photosTV.setBackgroundResource(R.color.white);
                        nowSelect = 1;
                        vp.setCurrentItem(nowSelect);
                    }
                    break;
            }
        }
    };
    private final int TO_PHONOS_TYPE = 0;
    private final int TO_CAMERA_TYPE = 1;
    private int nowSelect = 99;//if no init zhe int is 99

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_mycamera_layout, null);
        setContentView(view);
        initView();
        initData();
        viewSetData();
    }

    private void initView() {
        vp = view.findViewById(R.id.vp_mycamera);
        photosTV = view.findViewById(R.id.tv_photos);
        toCameraTV = view.findViewById(R.id.tv_to_camera);
    }


    private void initData() {
        photosFragment = new PhotosFragment();
        toCameraFragment = new ToCameraFragment();
        fragments = new ArrayList<>();
        fragments.add(photosFragment);
        fragments.add(toCameraFragment);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
    }


    private void viewSetData() {
        vp.setAdapter(fragmentAdapter);
        handler.sendEmptyMessage(TO_CAMERA_TYPE);
        photosTV.setOnClickListener(this);
        toCameraTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_photos:
                handler.sendEmptyMessage(TO_PHONOS_TYPE);
                break;
            case R.id.tv_to_camera:
                handler.sendEmptyMessage(TO_CAMERA_TYPE);
                break;
        }
    }
}
