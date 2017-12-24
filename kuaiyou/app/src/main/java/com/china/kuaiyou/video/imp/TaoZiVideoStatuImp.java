package com.china.kuaiyou.video.imp;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public interface TaoZiVideoStatuImp {
    public void videoIsOk();
    public void videoCache(int percent);
    public void videoException(String exceptionStr);
    public void videoPlay();
    public void videoNoFile(String filePath);
    public void videoPause();
    public void videoOver();
    public void videoPlayCurrentPosition(double percentage, int currentPosition, int duration);
}
