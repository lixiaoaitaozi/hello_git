package com.china.kuaiyou.fragment;

import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.MeTemplateActivity;
import com.china.kuaiyou.activity.UpdateVideoActivity;
import com.china.kuaiyou.adapter.UpdataVideoRVAdapter;
import com.china.kuaiyou.appliction.MyApplication;
import com.china.kuaiyou.mybase.BaseFragment_v4;
import com.china.kuaiyou.util.TimeUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class ToCameraFragment extends BaseFragment_v4 implements View.OnClickListener {
    private ImageView recordIV;
    private ImageView faceIV, musicIV;
    private SurfaceView mCameraPreview;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private MediaRecorder mRecorder;
    private final static int CAMERA_ID = 0;
    private boolean mIsRecording = false;
    private boolean mIsSufaceCreated = false;
    private static final String TAG = "Jackie";
    private Handler mHandler = new Handler();
    private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mIsSufaceCreated = false;
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mIsSufaceCreated = true;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            startPreview();
        }
    };

    private Runnable mTimestampRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimestamp();
            mHandler.postDelayed(this, 1000);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_to_camera_layout, null);
        initView();
        initData();
        viewSetData();
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mIsRecording) {
            stopRecording();
        }
        stopPreview();
    }

    private void initView() {
        mCameraPreview = view.findViewById(R.id.sv_camera);
        recordIV = view.findViewById(R.id.iv_start_or_stop_camera);
        faceIV = view.findViewById(R.id.iv_face);
        musicIV = view.findViewById(R.id.iv_music);

    }

    private void initData() {
        mSurfaceHolder = mCameraPreview.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void viewSetData() {

        recordIV.setOnClickListener(this);
        faceIV.setOnClickListener(this);
        musicIV.setOnClickListener(this);
    }


    //启动预览
    private void startPreview() {
        //保证只有一个Camera对象
        if (mCamera != null || !mIsSufaceCreated) {
            Log.d(TAG, "startPreview will return");
            return;
        }

        mCamera = Camera.open(CAMERA_ID);

        Camera.Parameters parameters = mCamera.getParameters();

        Camera.Size size = getBestPreviewSize(200, 200, parameters);
        if (size != null) {
            parameters.setPreviewSize(size.width, size.height);
        }

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        parameters.setPreviewFrameRate(20);

        //设置相机预览方向
        mCamera.setDisplayOrientation(90);

        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
//          mCamera.setPreviewCallback(mPreviewCallback);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        mCamera.startPreview();
    }


    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }

        return result;
    }


    private void stopPreview() {
        //释放Camera对象
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(null);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    private void stopRecording() {
        if (mCamera != null) {
            mCamera.lock();
        }

        if (mRecorder != null) {
            try {
                //下面三个参数必须加，不加的话会奔溃，在mediarecorder.stop();
                //报错为：RuntimeException:stop failed
                mRecorder.setOnErrorListener(null);
                mRecorder.setOnInfoListener(null);
                mRecorder.setPreviewDisplay(null);
                mRecorder.stop();
            } catch (IllegalStateException e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
            } catch (RuntimeException e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
            } catch (Exception e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
            }
            mRecorder.release();
            mRecorder = null;
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }

        recordIV.setImageDrawable(getResources().getDrawable(R.drawable.luxiang));
        mIsRecording = false;

        mHandler.removeCallbacks(mTimestampRunnable);


        //重启预览
        startPreview();
    }


    private void updateTimestamp() {
        int second = 30;
        int minute = 1;
        second++;
        Log.d(TAG, "second: " + second);

//        if (second < 10) {
//            mSecondText.setText(String.valueOf(second));
//        } else if (second >= 10 && second < 60) {
//            mSecondPrefix.setVisibility(View.GONE);
//            mSecondText.setText(String.valueOf(second));
//        } else if (second >= 60) {
//            mSecondPrefix.setVisibility(View.VISIBLE);
//            mSecondText.setText("0");
//
//            minute++;
//            mMinuteText.setText(String.valueOf(minute));
//        } else if (minute >= 60) {
//            mMinutePrefix.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_start_or_stop_camera:
                if (mIsRecording) {
                    stopRecording();
                } else {
                    initMediaRecorder();
                    startRecording();
                    //开始录像后，每隔1s去更新录像的时间戳
                    mHandler.postDelayed(mTimestampRunnable, 1000);
                }
                break;
            case R.id.iv_face:
                showToast("尚未添加魔法表情");
                break;
            case R.id.iv_music:
                showToast("尚未添加音乐");
                toActivity(MeTemplateActivity.class, "0");
                break;
        }
    }

    private void initMediaRecorder() {
        mRecorder = new MediaRecorder();//实例化
        mCamera.unlock();
        //给Recorder设置Camera对象，保证录像跟预览的方向保持一致
        mRecorder.setCamera(mCamera);
        mRecorder.setOrientationHint(90);  //改变保存后的视频文件播放时是否横屏(不加这句，视频文件播放的时候角度是反的)
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置从麦克风采集声音
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // 设置从摄像头采集图像
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);  // 设置视频的输出格式 为MP4
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); // 设置音频的编码格式
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264); // 设置视频的编码格式
        mRecorder.setVideoSize(176, 144);  // 设置视频大小
        mRecorder.setVideoFrameRate(20); // 设置帧率
//        mRecorder.setMaxDuration(10000); //设置最大录像时间为10s
        mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        //设置视频存储路径
        File file = new File(MyApplication.movies_files + File.separator + "VideoRecorder");
        if (!file.exists()) {
            //多级文件夹的创建
            file.mkdirs();
        }
        mRecorder.setOutputFile(file.getPath() + File.separator + "VID_" + System.currentTimeMillis() + ".mp4");
    }

    private void startRecording() {
        if (mRecorder != null) {
            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (Exception e) {
                mIsRecording = false;
                Log.e(TAG, e.getMessage());
            }
        }

        recordIV.setImageDrawable(getResources().getDrawable(R.drawable.luxiang2));
        mIsRecording = true;
    }
}
