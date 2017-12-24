package com.china.kuaiyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.eventbus.MessageEvent;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.mybase.MySharePreferences;
import com.china.kuaiyou.netbean.Msg;
import com.china.kuaiyou.netbean.user.AppLogin;
import com.china.kuaiyou.netbean.user.UserBean;
import com.china.kuaiyou.netutil.NetToGet;
import com.china.kuaiyou.netutil.NetToSend;
import com.china.kuaiyou.okhttp.OkHttpManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class LoginActivty extends BaseCompatActivity implements OkHttpManager.DataCallBack, View.OnClickListener {

    private EditText loginStrET, passWordET;
    private Button loginBT;
    private TextView registerTV, forgetPassWordTV;
    private LinearLayout wxLoginLL;
    private String loginStr, passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        initData();
        initView();
    }




    private void initData() {
        //实现自动登陆
        String str = MySharePreferences.getInstance().getString("user");
        if (!TextUtils.isEmpty(str)) {
            AppLogin appLoginBean = MyGson.getInstance().fromJson(str, AppLogin.class);
            NetToSend.getInstance().appUserToLogin(appLoginBean.getlS(), appLoginBean.getpW(), this, 1);
        }
//        //注册
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        loginStrET = (EditText) findViewById(R.id.et_loginstr);
        passWordET = (EditText) findViewById(R.id.et_password);
        loginBT = (Button) findViewById(R.id.bt_applogin);
        loginBT.setOnClickListener(this);
        registerTV = (TextView) findViewById(R.id.tv_register);
        registerTV.setOnClickListener(this);
        forgetPassWordTV = (TextView) findViewById(R.id.tv_forgetpassword);
        forgetPassWordTV.setOnClickListener(this);
//        wxLoginLL = (LinearLayout) findViewById(R.id.ll_wx_login);
//        wxLoginLL.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_applogin) {
            loginStr = loginStrET.getText().toString();
            passWord = passWordET.getText().toString();
            if (!TextUtils.isEmpty(loginStr) && !TextUtils.isEmpty(passWord)) {
                NetToSend.getInstance().appUserToLogin(loginStr, passWord, this, 1);
            } else {
                showToast(getString(R.string.applogin_username_or_password_is_emp));
            }
        } else if (id == R.id.tv_register) {
            toActivity(AppRegisterActivity.class);
        } else if (id == R.id.tv_forgetpassword) {
            toActivity(AppFindPassWordActivity.class);
        } else if (id == R.id.iv_wx_login) {

        }
    }

    //注册页面返回消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void registrResult(MessageEvent messageEvent) {
        AppLogin appLoginBean = MyGson.getInstance().fromJson(messageEvent.getStr(), AppLogin.class);
        loginStr = appLoginBean.getlS();
        passWord = appLoginBean.getpW();
        NetToSend.getInstance().appUserToLogin(loginStr, passWord, this, 1);
    }

    @Override
    public void requestFailure(Request request, IOException e) {
        showToast(getString(R.string.net_error));
    }

    @Override
    public void requestSuccess(String result, int netbs) throws Exception {
        Msg msg = NetToGet.getInstance().getMessageBean(result);
        if (msg.getC() == 1) {
            String str = msg.getO();
            UserBean appUserBean = MyGson.getInstance().fromJson(str, UserBean.class);
            if (appUserBean != null) {
                String showStr = "";
                if (msg.getM().equals("0")) {
                    showStr = getString(R.string.applogin_login_is_lhcx);
                } else if (msg.getM().equals("1")) {
                    showStr = getString(R.string.applogin_username_type_error);
                } else if (msg.getM().equals("2")) {
                    showStr = getString(R.string.applogin_login_is_merchant_group);
                } else if (msg.getM().equals("3")) {
                    showStr = getString(R.string.applogin_login_is_merchant);
                } else if (msg.getM().equals("4")) {
                    showStr = getString(R.string.applogin_login_is_employee);
                }
                showToast("登录中..");
                if (!TextUtils.isEmpty(loginStr) && !TextUtils.isEmpty(passWord)) {
                    MySharePreferences.getInstance().putString("user", MyGson.getInstance().toGsonStr(new AppLogin(loginStr, passWord, "", "")));
                }
                MySharePreferences.getInstance().putString("appuser", str);
                toActivity(MainActivity.class,str);
                finish();
            } else {
                showToast("数据解析错误");
            }

        } else {
            showToast(msg.getM());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
