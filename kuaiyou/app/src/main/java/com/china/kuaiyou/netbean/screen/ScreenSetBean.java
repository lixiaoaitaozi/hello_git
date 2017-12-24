package com.china.kuaiyou.netbean.screen;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ScreenSetBean {
    int x;
    int y;
    int index;

    public ScreenSetBean(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ScreenSetBean{" +
                "x=" + x +
                ", y=" + y +
                ", index=" + index +
                '}';
    }
}
