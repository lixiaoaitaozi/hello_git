package com.china.kuaiyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.adapter.UploadAdapter;
import com.china.kuaiyou.bean.ResultUploadbean;
import com.china.kuaiyou.bean.UploadBean;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.oss.OssManager;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class UploadActivity extends BaseCompatActivity implements OssManager.OssUploadImp {
    private RecyclerView recyclerView;
    private UploadAdapter uploadAdapter;
    private ResultUploadbean resultUploadbean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RESET_PROGRESS:
                    if (uploadAdapter != null && resultUploadbean != null) {
                        for (int i = 0; i < resultUploadbean.getList().size(); i++) {
                            UploadBean uploadBean = resultUploadbean.getList().get(i);
                            if (uploadBean.getFileName().equals(msg.obj + "")) {
                                ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                                uploadAdapter.notifyItemChanged(i);
                            }
                        }
                    }
                    break;
            }
        }
    };
    private final int RESET_PROGRESS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_layout);
        initTitle("文件上传");
        initView();
        initData();
        viewSetData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initTitle(String titleString) {
        ImageView titleBackIV = (ImageView) findViewById(R.id.iv_title_back);
        titleBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (UploadBean uploadBean : resultUploadbean.getList()) {
                    try {
                        OssManager.getInstance().cancel(uploadBean.getFileName());
                    } catch (Exception e) {

                    }
                }
                Intent data = new Intent();
                UploadActivity.this.setResult(101, data);
                finish();
            }
        });
        TextView titleTV = (TextView) findViewById(R.id.tv_title);
        titleTV.setText(titleString);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_upload);
    }

    private void initData() {
        resultUploadbean = MyGson.getInstance().fromJson(getIntentString(), ResultUploadbean.class);
        if (resultUploadbean == null) {
            resultUploadbean = new ResultUploadbean();
        }
        uploadAdapter = new UploadAdapter(context, resultUploadbean.getList());
        for (UploadBean uploadBean : resultUploadbean.getList()) {
            OssManager.getInstance().upload(uploadBean.getFileName(), uploadBean.getUploadPath(), uploadBean.getFilePath(), this);
        }

    }

    private void viewSetData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(uploadAdapter);
    }

    @Override
    public void uploadOk(String queStr) {
        uploadAdapter.setNowProgress(queStr, 101);
        Message msg = new Message();
        msg.what = RESET_PROGRESS;
        msg.obj = queStr;
        handler.sendMessage(msg);
        if (uploadAdapter.checkUploadIsOver()) {
            showToast("需要的文件都上传成功");
            Intent data = new Intent();
            this.setResult(100, data);
            finish();
        }


    }

    @Override
    public void uploadError(String queStr, String localUrl) {
        uploadAdapter.setNowProgress(queStr, 102);
        Message msg = new Message();
        msg.what = RESET_PROGRESS;
        msg.obj = queStr;
        handler.sendMessage(msg);
    }

    @Override
    public void uploadProgress(String queStr, int progress) {
        uploadAdapter.setNowProgress(queStr, progress);
        Message msg = new Message();
        msg.what = RESET_PROGRESS;
        msg.obj = queStr;
        handler.sendMessage(msg);
    }

}
