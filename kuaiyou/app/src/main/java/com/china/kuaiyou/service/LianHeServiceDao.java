package com.china.kuaiyou.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.china.kuaiyou.mybase.LG;
import com.china.kuaiyou.oss.OssManager;

/**
 * Created by xiefuning on 2017/5/11.
 * about:
 */

public class LianHeServiceDao {
    private String tag = "LianHeServiceDao>>>>";
    private Context context;
    private ServiceConnection sc;
    private LianHeService yhs;
    private static LianHeServiceDao lianHeServiceDao;

    private LianHeServiceDao(Context context) {
        this.context = context;
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LianHeService.LianHeServiceBinder serviceBinder = (LianHeService.LianHeServiceBinder) service;
                yhs = serviceBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        // TODO: handle exception
        LG.i(tag, "init()");
    }

    public static LianHeServiceDao getInstance(Context context) {
        if (lianHeServiceDao == null) {
            synchronized (LianHeServiceDao.class) {
                if (lianHeServiceDao == null) {
                    lianHeServiceDao = new LianHeServiceDao(context);
                }
            }
        }

        return lianHeServiceDao;
    }


    public void startService() {
        try {
            Intent intent = new Intent(context, LianHeService.class);
//            context.startService(intent);
            context.bindService(intent, sc, Context.BIND_AUTO_CREATE);
            LG.i(tag, "start true");
        } catch (Exception e) {
            // TODO: handle exception
            LG.i(tag, "start false");
        }
    }

    public void upload(String queStr, String netUrl, String localUrl, OssManager.OssUploadImp ossUploadImp) {
        if (yhs != null) {
            LG.i(tag, "--------yhsl");
            yhs.upload(queStr, netUrl, localUrl, ossUploadImp);
        } else {
            LG.i("", "--------yhs==null");
        }
    }


    public void stopService() {
        try {
            context.unbindService(sc);
            LG.i(tag, "--------stop");
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
