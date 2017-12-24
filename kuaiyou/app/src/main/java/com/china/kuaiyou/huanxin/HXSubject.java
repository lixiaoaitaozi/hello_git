package com.china.kuaiyou.huanxin;

/**
 * Created by xiefuning on 2017/5/12.
 * about:
 */

public interface HXSubject {
    /**
     * 增加订阅者
     * @param observer
     */
    public void attach(HXObserver observer);
    /**
     * 删除订阅者
     * @param observer
     */
    public void detach(HXObserver observer);
    /**
     * 通知订阅者更新消息
     */
    public void notify(String message, String userId);
}
