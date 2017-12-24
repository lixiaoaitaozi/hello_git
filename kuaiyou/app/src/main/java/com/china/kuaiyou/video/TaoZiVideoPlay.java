package com.china.kuaiyou.video;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceHolder;

import com.china.kuaiyou.mybase.LG;
import com.china.kuaiyou.video.imp.TaoZiVideoStatuImp;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class TaoZiVideoPlay implements MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private final String tag = this.getClass().toString() + ">>>"; // LOG信息
    private MediaPlayer mediaPlayer; /* 播放器 */
    private SurfaceHolder surfaceHolder;
    private TaoZiVideoStatuImp taoZiVideoStatuImp;
    private boolean canPlay;//能否播放
    private boolean autoPlay;//自动播放,默认为true
    private String filePath;

    public TaoZiVideoPlay(SurfaceHolder surfaceHolder, TaoZiVideoStatuImp taoZiVideoStatuImp) {
        this.surfaceHolder = surfaceHolder;
        this.taoZiVideoStatuImp = taoZiVideoStatuImp;
    }

    private void setMediaPlayer() {
        mediaPlayer = new MediaPlayer(); /* 创建 MediaPlayer 对象 */
        mediaPlayer.setAudioStreamType(2); /* 设置播放音量 */
        mediaPlayer.setDisplay(surfaceHolder); /* 设置播放载体 */
       /* 设置 MediaPlayer 错误监听器, 如果出现错误就会回调该方法打印错误代码 */
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        canPlay = false;
        autoPlay = true;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        LG.d(tag, "MediaPlayer 出现错误 what : " + what
                + " , extra : " + extra);
        taoZiVideoStatuImp.videoException("MediaPlayer 出现错误 what : " + what
                + " , extra : " + extra);

        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
         /* 打印缓冲的百分比, 如果缓冲 */
        LG.d(tag, "缓冲了的百分比 : " + percent + " %");
        taoZiVideoStatuImp.videoCache(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        LG.d(tag, "播放完毕了");
        canPlay = false;
        stop();
        taoZiVideoStatuImp.videoOver();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        LG.d(tag, "------------准备完成" + filePath);
        taoZiVideoStatuImp.videoIsOk();
        canPlay = true;
        if (autoPlay)
            start();

    }

    //设置自动播放功能开启
    public void setAutoPlayOpen() {
        autoPlay = true;
    }

    //设置自动播放功能关闭
    public void setAutoPlayClose() {
        autoPlay = false;
    }

    //设置播放文件
    public void setPlay(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            this.filePath = filePath;
            setMediaPlayer();
            try {
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                taoZiVideoStatuImp.videoException(e.toString());
            }
        } else {
            taoZiVideoStatuImp.videoNoFile(filePath);

        }
    }

    //设置播放文件
    public void setPlayUrl(String filePath, Context context) {
        this.filePath = filePath;
        setMediaPlayer();
        try {
            Uri uri = Uri.parse(filePath);
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            taoZiVideoStatuImp.videoException(e.toString());
        }

    }


    public void start() {
        if (canPlay && mediaPlayer != null) {
            mediaPlayer.start();
            taoZiVideoStatuImp.videoPlay();
            //校准程序後期在加
            new Thread(new RecvThread()).start();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying() && mediaPlayer != null) {
            mediaPlayer.pause();
            taoZiVideoStatuImp.videoPause();
        }
    }


    public void stop() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        } catch (Exception e) {
            // TODO: handle exceptm ion
            mediaPlayer = null;
        }
    }

    /**
     * 设置固定区域播放
     *
     * @param index
     */
    public void setSeekTo(int index) {
        mediaPlayer.seekTo(index);
    }

    private class RecvThread implements Runnable {
        int count = 0;

        public void run() {
            try {
                while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int duration = mediaPlayer.getDuration();
                    double percentage = (double) currentPosition / (double) duration;
                    NumberFormat nf = NumberFormat.getPercentInstance();
                    nf.setMinimumFractionDigits(2);
                    String retStr = nf.format(percentage);
                    taoZiVideoStatuImp.videoPlayCurrentPosition(percentage, currentPosition, duration);
                    LG.d(tag, "播放器:進度為：" + currentPosition + "总长度为:" + duration + "播放百分比為:" + retStr);
                    new Thread().sleep(1000);
                    count++;
                    if (count % 5 == 0) {
//                        ToastUtil.showToast("播放器:進度為：" + currentPosition + "总长度为:" + duration + "播放百分比為:" + retStr);
                    }
                }
            } catch (Exception e) {
                LG.d(tag, "播放器:" + e);
            }
        }
    }

}


