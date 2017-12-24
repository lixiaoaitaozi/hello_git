package com.china.kuaiyou.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.adapter.MainActivityModelRVAdapter;
import com.china.kuaiyou.adapter.MainActivityRVAdapter;
import com.china.kuaiyou.fileutil.FileInfo;
import com.china.kuaiyou.fileutil.MyFileSearch;
import com.china.kuaiyou.huanxin.HXObserver;
import com.china.kuaiyou.huanxin.HXSubscriptionSubject;
import com.china.kuaiyou.mybase.BaseMainActivity;
import com.china.kuaiyou.mybase.LG;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.netbean.Msg;
import com.china.kuaiyou.netbean.template.ResultTemplateBean;
import com.china.kuaiyou.netbean.template.TemplateBean;
import com.china.kuaiyou.netbean.user.UserBean;
import com.china.kuaiyou.netutil.NetToGet;
import com.china.kuaiyou.netutil.NetToSend;
import com.china.kuaiyou.okhttp.OkHttpManager;
import com.china.kuaiyou.service.LianHeServiceDao;
import com.china.kuaiyou.thread.HXLoginThread;
import com.china.kuaiyou.util.ScreenUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class MainActivity extends BaseMainActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OkHttpManager.DataCallBack, HXObserver {
    private UserBean appUser;
    private boolean appUserExit;
    private boolean isFirst = true;
    private RelativeLayout titleNoLoginRL, titleIsLoginRL;
    private TextView titleFindTV, titleLoveTV, titleSameCityTV;
    private SwipeRefreshLayout bodyShowModelSRL;
    private RecyclerView bodyShowModelRV;
    private MainActivityRVAdapter mainActivityModelRVAdapter;
    private LinearLayout bodyShowLoveLL;

    private void viewSet(int what) {
        switch (what) {
            case NOLOGIN:
                titleNoLoginRL.setVisibility(View.VISIBLE);
                titleIsLoginRL.setVisibility(View.GONE);
                dataSet(GETNOLOGINDATA);
                break;
            case ISLOGIN:
                titleNoLoginRL.setVisibility(View.GONE);
                titleIsLoginRL.setVisibility(View.VISIBLE);
                viewSet(FINDISCLICK);
                break;
            case SHOWMODELSRL:
                bodyShowModelSRL.setVisibility(View.VISIBLE);
                bodyShowLoveLL.setVisibility(View.GONE);
                break;
            case SHOWLOVELL:
                bodyShowModelSRL.setVisibility(View.GONE);
                bodyShowLoveLL.setVisibility(View.VISIBLE);
                break;
            case SHOWREFRESH:
                bodyShowModelSRL.setRefreshing(true);
                break;
            case HIDEREFRESH:
                bodyShowModelSRL.setRefreshing(false);
                if (templateBeans != null) {
                    mainActivityModelRVAdapter.notifyDataSetChanged();
                }
                break;
            case FINDISCLICK:
                titleFindTV.setTextColor(this.getResources().getColorStateList(R.color.orange));
                titleLoveTV.setTextColor(this.getResources().getColorStateList(R.color.black));
                titleSameCityTV.setTextColor(this.getResources().getColorStateList(R.color.black));
                nowSelect=GETFINDDATA;
                dataSet(GETFINDDATA);
                break;
            case LOVEISCLICK:
                titleFindTV.setTextColor(this.getResources().getColorStateList(R.color.black));
                titleLoveTV.setTextColor(this.getResources().getColorStateList(R.color.orange));
                titleSameCityTV.setTextColor(this.getResources().getColorStateList(R.color.black));
                nowSelect=GETLOVADATE;
                dataSet(GETLOVADATE);

                break;
            case SAMECITYISCLICK:
                titleFindTV.setTextColor(this.getResources().getColorStateList(R.color.black));
                titleLoveTV.setTextColor(this.getResources().getColorStateList(R.color.black));
                titleSameCityTV.setTextColor(this.getResources().getColorStateList(R.color.orange));
                nowSelect=GETSAMECITYDATA;
                dataSet(GETSAMECITYDATA);
                break;
        }
    }

    private final int NOLOGIN = 0;
    private final int ISLOGIN = 1;
    private final int SHOWMODELSRL = 2;
    private final int SHOWLOVELL = 3;
    private final int SHOWREFRESH = 4;
    private final int HIDEREFRESH = 5;
    private final int FINDISCLICK = 6;
    private final int LOVEISCLICK = 7;
    private final int SAMECITYISCLICK = 8;
    private Handler handler = new Handler();

    private void dataSet(int what) {
        switch (what) {
            case GETNOLOGINDATA:
                viewSet(SHOWMODELSRL);
                viewSet(SHOWREFRESH);
                handler.post(runnable);
                break;
            case GETFINDDATA:
                viewSet(SHOWMODELSRL);
                viewSet(SHOWREFRESH);

                handler.post(runnable);
                break;
            case GETSAMECITYDATA:
                viewSet(SHOWMODELSRL);
                viewSet(SHOWREFRESH);

                handler.post(runnable);
                break;
            case GETLOVADATE:
                break;
        }
    }

    private final int GETNOLOGINDATA = 0;
    private final int GETFINDDATA = 1;
    private final int GETSAMECITYDATA = 2;
    private final int GETLOVADATE = 3;
    private int nowSelect=0;
    private final int NET_TYPE_PUBLIC = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String useId = "";
            if (appUser != null) {
                useId = appUser.getId();
            } else {
                useId = "";
            }
            NetToSend.getInstance().tempaltePrivate(useId, MainActivity.this, NET_TYPE_PUBLIC);
            viewSet(HIDEREFRESH);
        }
    };
    private LianHeServiceDao lianHeServiceDao;
    private long clickTime = 0; //记录第一次点击的时间

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            showToast("再点击一次退出");//Again click to exit the program
            clickTime = System.currentTimeMillis();
        } else {
            LG.i("", "exit application");
            this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        lianHeServiceDao = LianHeServiceDao.getInstance(context);
        lianHeServiceDao.startService();

        HXSubscriptionSubject.getInstence().attach(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appUser = MyGson.getInstance().fromJson(getIntentString(), UserBean.class);
        if (appUser == null) {
            if (appUserExit || isFirst) {
                appUserExit = false;
                isFirst = false;
                viewSet(NOLOGIN);
            }
        } else {
            new HXLoginThread(appUser).start();
            if (!appUserExit || isFirst) {
                appUserExit = true;
                isFirst = false;
                viewSet(ISLOGIN);
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {

        }

    }

    @Override
    protected void initView() {
        //title
        titleNoLoginRL = (RelativeLayout) findViewById(R.id.rl_main_title_no_login);
        titleNoLoginRL.findViewById(R.id.tv_main_title_to_login_activity).setOnClickListener(this);
        titleIsLoginRL = (RelativeLayout) findViewById(R.id.rl_main_title_is_login);
        titleIsLoginRL.findViewById(R.id.iv_main_title_to_me_activity).setOnClickListener(this);
        titleIsLoginRL.findViewById(R.id.iv_main_title_to_camera_activity).setOnClickListener(this);
        titleFindTV = titleIsLoginRL.findViewById(R.id.tv_main_title_find);
        titleFindTV.setOnClickListener(this);
        titleLoveTV = titleIsLoginRL.findViewById(R.id.tv_main_title_love);
        titleLoveTV.setOnClickListener(this);
        titleSameCityTV = titleIsLoginRL.findViewById(R.id.tv_main_title_same_city);
        titleSameCityTV.setOnClickListener(this);
        //body
        bodyShowModelSRL = (SwipeRefreshLayout) findViewById(R.id.srl_main_body_show_model);
        bodyShowModelSRL.setOnRefreshListener(this);
        bodyShowModelRV = (RecyclerView) findViewById(R.id.rv_main_body_show_model);
        bodyShowLoveLL = (LinearLayout) findViewById(R.id.ll_main_body_show_love);
    }

    @Override
    protected void initData() {
        nowSelect=GETFINDDATA;
        mainActivityModelRVAdapter = new MainActivityRVAdapter(this, templateBeans, new ScreenUtil(this).getPoint().x / 2 - 2,nowSelect);
        bodyShowModelRV.setLayoutManager(new GridLayoutManager(this, 2));
        bodyShowModelRV.setItemAnimator(new DefaultItemAnimator());
        bodyShowModelRV.setAdapter(mainActivityModelRVAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_main_title_to_login_activity:
                toActivity(AllLoginActivity.class);
                break;
            case R.id.iv_main_title_to_me_activity:
                toActivity(MeActivity.class);
                break;
            case R.id.iv_main_title_to_camera_activity:

                toActivity(MyCameraActivity.class);

                break;
            case R.id.tv_main_title_find:
                viewSet(FINDISCLICK);
                break;
            case R.id.tv_main_title_love:
                viewSet(LOVEISCLICK);
                break;
            case R.id.tv_main_title_same_city:
                viewSet(SAMECITYISCLICK);
                break;
        }
    }

    @Override
    public void onRefresh() {
        dataSet(GETNOLOGINDATA);
    }

    @Override
    public void requestFailure(Request request, IOException e) {

    }

    private ResultTemplateBean publicTemplates;
    private ResultTemplateBean templateBeans = new ResultTemplateBean();

    @Override
    public void requestSuccess(String result, int netbs) throws Exception {
        Msg msg = NetToGet.getInstance().getMessageBean(result);
        if (msg.getC() == 1) {
            publicTemplates = MyGson.getInstance().fromJson(msg.getO(), ResultTemplateBean.class);
            itemSetDate();
            mainActivityModelRVAdapter = new MainActivityRVAdapter(this, templateBeans, new ScreenUtil(this).getPoint().x / 2 - 2,nowSelect);
            bodyShowModelRV.setAdapter(mainActivityModelRVAdapter);
        }
    }

    private void itemSetDate() {
        templateBeans = new ResultTemplateBean();
        for (int i = 0; i < publicTemplates.getList().size(); i++) {
            TemplateBean templateBean = publicTemplates.getList().get(i);
            if (templateBean.getType() == 0) {
                //图片

            } else if (templateBean.getType() == 1) {
                //视频
                templateBeans.add(templateBean);
            } else if (templateBean.getType() == 2) {
                //WEB

            }
        }
    }

    @Override
    public void update(String message, String userId) {
        showToast("有你的消息");
    }
}
