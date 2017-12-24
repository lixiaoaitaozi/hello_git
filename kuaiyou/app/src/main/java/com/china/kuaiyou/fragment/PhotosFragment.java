package com.china.kuaiyou.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.UpdataImageActivity;
import com.china.kuaiyou.adapter.MyCaremaRVAdapter;
import com.china.kuaiyou.fileutil.FileInfo;
import com.china.kuaiyou.fileutil.MyFileSearch;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.mybase.BaseFragment_v4;
import com.china.kuaiyou.mybase.ToastUtil;
import com.china.kuaiyou.util.ScreenUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class PhotosFragment extends BaseFragment_v4 implements MyCaremaRVAdapter.DataListen, View.OnClickListener {
    private ImageView selectCameraIV;
    private String selectImageUrl = "";
    private Button nextBT;
    private RecyclerView camerasRV;
    private MyCaremaRVAdapter myCaremaRVAdapter;

    private Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SETCAMERASRV:
                    if (fileInfos != null) {
                        myCaremaRVAdapter.notifyDataSetChanged();
                        if(fileInfos.size()>0){
                            selectImageUrl=fileInfos.get(0).getPath();
                            MyGlide.getInstance().setBitMap(getContext(), selectCameraIV, selectImageUrl);
                        }

                    }
                    break;
            }
        }
    };
    private final int SETCAMERASRV = 2;
    private ArrayList<FileInfo> fileInfos = new ArrayList<>();
    private Handler dataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETCAREMAS:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fileInfos.clear();
                            String url = Environment
                                    .getExternalStorageDirectory().getAbsolutePath()
                                    + File.separator + "DCIM";
                            File file = new File(url);
                            if (file.exists()) {
                                fileInfos = MyFileSearch.getAllImageFile(fileInfos, file);
                            } else {
                                fileInfos = MyFileSearch.getPhoneAllImageFile(fileInfos);
                            }
                            viewHandler.sendEmptyMessage(SETCAMERASRV);
                        }
                    }).start();
                    break;
            }
        }
    };
    private final int GETCAREMAS = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_photos_layout, null);
        initView();
        initData();
        viewSetData();
        return view;

    }

    private void initView() {
        selectCameraIV = view.findViewById(R.id.iv_select_camera);
        nextBT = view.findViewById(R.id.bt_next);
        camerasRV = view.findViewById(R.id.rv_photos);
    }

    private void initData() {
        myCaremaRVAdapter = new MyCaremaRVAdapter(getContext(), fileInfos, new ScreenUtil(getActivity()).getPoint().x / 4 - 2, this);
        dataHandler.sendEmptyMessage(GETCAREMAS);
    }

    private void viewSetData() {
        nextBT.setOnClickListener(this);
        camerasRV.setLayoutManager(new GridLayoutManager(getContext(), 4));
        camerasRV.setItemAnimator(new DefaultItemAnimator());
        camerasRV.setAdapter(myCaremaRVAdapter);
    }

    @Override
    public void getUrl(String url) {
        selectImageUrl = url;
        MyGlide.getInstance().setBitMap(getContext(), selectCameraIV, selectImageUrl);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                if (!TextUtils.isEmpty(selectImageUrl)) {
                    toActivity(UpdataImageActivity.class,selectImageUrl);
                } else {
                    ToastUtil.showToast("还未选择图片");
                }
                break;

        }
    }
}
