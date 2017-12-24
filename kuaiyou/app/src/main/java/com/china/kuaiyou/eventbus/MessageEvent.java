package com.china.kuaiyou.eventbus;

/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class MessageEvent {
    private String str;
    private EventEnum eventEnum;

    public MessageEvent(EventEnum eventEnum, String str) {
        this.str = str;
        this.eventEnum = eventEnum;
    }

    public String getStr() {
        return str;
    }

    public EventEnum getEventEnum() {
        return eventEnum;
    }
}
