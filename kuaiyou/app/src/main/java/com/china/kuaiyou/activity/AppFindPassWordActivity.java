package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
 * Created by Administrator on 2017/8/24 0024.
 */

public class AppFindPassWordActivity extends BaseCompatActivity implements OkHttpManager.DataCallBack, View.OnClickListener {
    private EditText needFindPhoneNameET, sucerityCodeET;
    private Button getSucerityCodeBT, resetPassWordBT;
    private boolean canGetPhoneMessageCode;
    private String needFindPhoneNameStr, getSucertuCodePhoneName,sucerityCode;
    private final int sendSucerityCodeNetType = 0;
    private Handler handler;
    private StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword_layout);
        myWindowSet.setFlatStyleColoredBars();
        initTitle(getString(R.string.appfindpassword_title));
        initDate();
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

    private void initDate() {
        canGetPhoneMessageCode = true;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    canGetPhoneMessageCode = true;
                    getSucerityCodeBT.setText(R.string.appregister_bt_getphonemessagecode);
                } else {
                    sb = new StringBuilder();
                    sb.append(getText(R.string.appregister_bt_getphonemessagecodewait).toString());
                    sb.append(msg.what);
                    sb.append("S");
                    getSucerityCodeBT.setText(sb.toString());
                }

            }
        };
    }

    private void initView() {
        needFindPhoneNameET = (EditText) findViewById(R.id.et_phonename);
        sucerityCodeET = (EditText) findViewById(R.id.et_suceritycode);
        getSucerityCodeBT = (Button) findViewById(R.id.bt_getsuceritycode);
        getSucerityCodeBT.setOnClickListener(this);
        resetPassWordBT = (Button) findViewById(R.id.bt_resetpassword);
        resetPassWordBT.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_getsuceritycode) {
            if (canGetPhoneMessageCode) {
                needFindPhoneNameStr = needFindPhoneNameET.getText().toString();
                if (!TextUtils.isEmpty(needFindPhoneNameStr)) {
                    if (needFindPhoneNameStr.length() == 11) {
                        getSucertuCodePhoneName = needFindPhoneNameStr;
                        canGetPhoneMessageCode = false;
                        NetToSend.getInstance().smsSendSucerityCode(needFindPhoneNameStr, this, sendSucerityCodeNetType);
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
        } else if (id == R.id.bt_resetpassword) {

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
            }
        } else {
            showToast(msg.getM());
        }
    }
}
