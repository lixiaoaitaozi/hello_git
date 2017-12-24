package com.china.kuaiyou.video;


import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.china.kuaiyou.mybase.LG;
import com.china.kuaiyou.video.imp.GetTaoZiVideoPlayImp;
import com.china.kuaiyou.video.imp.TaoZiVideoStatuImp;

public class TaoZiVideoPlayView implements SurfaceHolder.Callback {
    private final String tag = this.getClass().toString() + ">>>"; // LOG信息
    private SurfaceHolder surfaceHolder;
    private TaoZiVideoStatuImp taoZiVideoStatuImp;
    private TaoZiVideoPlay taoZiVideoPlay;
    private GetTaoZiVideoPlayImp getTaoZiVideoPlayImp;

    public TaoZiVideoPlayView(SurfaceView surfaceView, GetTaoZiVideoPlayImp getTaoZiVideoPlayImp, TaoZiVideoStatuImp taoZiVideoStatuImp) {
        this.getTaoZiVideoPlayImp = getTaoZiVideoPlayImp;
        this.taoZiVideoStatuImp = taoZiVideoStatuImp;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);/* 设置视频类型 */
    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        // 播放器变化大小的时候会调用这里

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        // 必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        LG.i(tag, "surface准备完成可以显示图像了");
        if (getTaoZiVideoPlayImp != null && taoZiVideoStatuImp != null) {
            taoZiVideoPlay = new TaoZiVideoPlay(arg0, taoZiVideoStatuImp);
            getTaoZiVideoPlayImp.getVideoPlay(taoZiVideoPlay);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        LG.d(tag, "播放器关闭");
        if (taoZiVideoPlay != null)
            taoZiVideoPlay.stop();
    }


}
