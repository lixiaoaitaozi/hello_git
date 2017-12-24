package com.china.kuaiyou.huanxin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiefuning on 2017/5/12.
 * about:
 */

public class HXSubscriptionSubject implements HXSubject {
    //储存订阅公众号的微信用户
    private List<HXObserver> observers;
    private static HXSubscriptionSubject subscriptionSubject;

    private HXSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static HXSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (HXSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new HXSubscriptionSubject();
            }
        }
        return subscriptionSubject;

    }

    @Override
    public void attach(HXObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(HXObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(String message,String userId) {
        for (HXObserver observer : observers) {
            observer.update(message,userId);
        }
    }
}
