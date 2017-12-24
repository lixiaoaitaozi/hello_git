package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.netbean.Msg;
import com.china.kuaiyou.netutil.NetToGet;
import com.china.kuaiyou.netutil.NetToSend;
import com.china.kuaiyou.okhttp.OkHttpManager;
import com.china.kuaiyou.thread.WaitThread;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class AppRegisterActivity extends BaseCompatActivity implements OkHttpManager.DataCallBack, View.OnClickListener, View.OnTouchListener {
    private EditText phoneNameET, passWordET, phoneMessageCodeET;
    private ImageView showPassWordIV;
    private boolean isShowPassWord;
    private Button getPhoneMessageCodeBT, appRegisterBT;
    private boolean canGetPhoneMessageCode;
    private Handler handler;
    private StringBuilder sb;
    private String phoneNameStr, passWordStr;
    private String sucerityCode = "";
    private String getSucertuCodePhoneName = "";
    private final int sendSucerityCodeNetType = 0;
    private final int appRegisterNetType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        myWindowSet.setFlatStyleColoredBars();
        initTitle(getString(R.string.appregister_title));
        initData();
        initView();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void initTitle(String titleString) {
        ImageView titleBackIV = (ImageView) findViewById(R.id.iv_title_back);
        titleBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView titleTV = (TextView) findViewById(R.id.tv_title);
        titleTV.setText(titleString);
    }

    private void initData() {
        canGetPhoneMessageCode = true;
        isShowPassWord=false;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    canGetPhoneMessageCode = true;
                    getPhoneMessageCodeBT.setText(R.string.appregister_bt_getphonemessagecode);
                } else {
                    sb = new StringBuilder();
                    sb.append(getText(R.string.appregister_bt_getphonemessagecodewait).toString());
                    sb.append(msg.what);
                    sb.append("S");
                    getPhoneMessageCodeBT.setText(sb.toString());
                }

            }
        };
    }

    private void initView() {
        phoneNameET = (EditText) findViewById(R.id.et_phonename);
        passWordET = (EditText) findViewById(R.id.et_password);
        phoneMessageCodeET = (EditText) findViewById(R.id.et_phonemessagecode);
        getPhoneMessageCodeBT = (Button) findViewById(R.id.bt_getphonemessagecode);
        getPhoneMessageCodeBT.setOnClickListener(this);
        appRegisterBT = (Button) findViewById(R.id.bt_appregister);
        appRegisterBT.setOnClickListener(this);
        showPassWordIV= (ImageView) findViewById(R.id.iv_show_password);
        showPassWordIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_getphonemessagecode) {
            if (canGetPhoneMessageCode) {
                phoneNameStr = phoneNameET.getText().toString();
                if (!TextUtils.isEmpty(phoneNameStr)) {
                    if (phoneNameStr.length() == 11) {
                        getSucertuCodePhoneName = phoneNameStr;
                        canGetPhoneMessageCode = false;
                        NetToSend.getInstance().smsSendSucerityCode(phoneNameStr, this, sendSucerityCodeNetType);
                        new WaitThread(handler, 60).start();
                        return;
                    } else {
                        showToast("手机号码长度不对");
                        return;
                    }
                }
                showToast("手机号码不能为空");
                return;
            } else {
                showToast("已经获取过验证码，请稍后在试");
            }
        } else if (id == R.id.bt_appregister) {
            phoneNameStr = phoneNameET.getText().toString();
            passWordStr = passWordET.getText().toString();

            String phoneMessageCodeStr = phoneMessageCodeET.getText().toString();
            //检测字符串是否正确
            String str = textRegisterInfo(phoneNameStr, passWordStr, phoneMessageCodeStr);
            if (str.equals("")) {
                NetToSend.getInstance().appUserToRegister(phoneNameStr, passWordStr, this, appRegisterNetType);
            } else {
                showToast(str);
            }
        } else if(id == R.id.iv_show_password){
            isShowPassWord=!isShowPassWord;
            showToast(isShowPassWord+"");
            if(isShowPassWord){
                passWordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else{
                passWordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }

        }
    }

    @Override
    public void requestFailure(Request request, IOException e) {
        showToast(e.toString());
    }

    @Override
    public void requestSuccess(String result, int netbs) throws Exception {
        Msg msg = NetToGet.getInstance().getMessageBean(result);
        if (msg.getC() == 1) {
            switch (netbs) {
                case sendSucerityCodeNetType:
                    sucerityCode = msg.getO().toString();
                    break;
                case appRegisterNetType:
                    if (!TextUtils.isEmpty(phoneNameStr) && !TextUtils.isEmpty(passWordStr)) {
//                        EventBus.getDefault().post(new MessageEvent(1, MyGson.getInstance().toGsonStr(new AppLoginBean(phoneNameStr, passWordStr, "", ""))));
                        showToast("注册成功，登录中");
                        finish();
                    } else {
                        //时间太久了，信息被注销掉了，需要用户自己去手动登录
                        finish();
                    }
                    break;
            }
        } else {
            showToast(msg.getM());
        }
    }

    //检测注册信息是否正常，返回""为正常

    public String textRegisterInfo(String phoneNameStr, String passWordStr, String phoneMessageCodeStr) {
        if (TextUtils.isEmpty(phoneNameStr))
            return "手机号不能为空";
        if (phoneNameStr.length() != 11)
            return "手机号长度不对";
        if (TextUtils.isEmpty(passWordStr))
            return "密码不能为空";
        if (passWordStr.length() < 6)
            return "密码长度必须大于等于6位";
        if (!phoneNameStr.equals(getSucertuCodePhoneName))
            return "获取验证码的手机号码和现在的手机号码不一致，请重新获取";
        if (TextUtils.isEmpty(phoneMessageCodeStr))
            return "手机验证码为空";
        if (!phoneMessageCodeStr.equals(sucerityCode))
            return "手机验证码错误";
        return "";
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
