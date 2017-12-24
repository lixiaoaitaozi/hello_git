package com.china.kuaiyou.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.view.ClearEditText;
import com.china.kuaiyou.netbean.Msg;
import com.china.kuaiyou.netutil.NetToGet;
import com.china.kuaiyou.netutil.NetToSend;
import com.china.kuaiyou.okhttp.OkHttpManager;
import com.china.kuaiyou.thread.WaitThread;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.IOException;

import okhttp3.Request;

/**
 * @className: EnrollNextActivity
 * @purpose: 注册下一步的页面
 * @athor :
 * @date:2017/11/11 16:27
 */
@ContentView(R.layout.activity_enroll_next)
public class EnrollNextActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, OkHttpManager.DataCallBack {

    private static final String TAG = "===" + EnrollNextActivity.class.getSimpleName();

    @ViewInject(R.id.iv_title_back)
    private ImageView blockImage;
    @ViewInject(R.id.tv_title)
    private TextView title;
    @ViewInject(R.id.hint_enroll_view)
    private TextView hintText;
    @ViewInject(R.id.explain_text_view)
    private TextView explainView;
    @ViewInject(R.id.code_text_view)
    private ClearEditText codeView;
    @ViewInject(R.id.code_show_hide_view)
    private Button codeButton;
    @ViewInject(R.id.password_text_view)
    private ClearEditText passWordV;
    @ViewInject(R.id.land_button)
    private Button landButtom;
    @ViewInject(R.id.pass_show_hide_view)
    private CheckBox showPassV;

    private boolean canGetPhoneMessageCode;
    private Handler handler;
    private StringBuilder sb;
    private String passWordStr;
    private String sucerityCode;
    private final int sendSucerityCodeNetType = 0;
    private final int appRegisterNetType = 1;
    private String phoneName;//接收 传输手机号变量

    @Override
    protected void initView() {
        title.setVisibility(View.GONE);
        hintText.setText("验证码已发送至+86 15113491725");
        showPassV.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initObject() {
        phoneName = getIntent().getStringExtra("phone");
        print(phoneName);
        setTextClick();
        canGetPhoneMessageCode = true;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    canGetPhoneMessageCode = true;
                    codeButton.setText(R.string.appregister_bt_getphonemessagecode);
                } else {
                    sb = new StringBuilder();
                    sb.append(getText(R.string.appregister_bt_getphonemessagecodewait).toString());
                    sb.append(msg.what);
                    sb.append("S");
                    codeButton.setText(sb.toString());
                }
            }
        };
    }

    @OnClick({R.id.iv_title_back, R.id.code_show_hide_view, R.id.land_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.code_show_hide_view:
                if (canGetPhoneMessageCode) {
                    if (!TextUtils.isEmpty(phoneName)) {
                        if (phoneName.length() == 11) {
                            canGetPhoneMessageCode = false;
                            NetToSend.getInstance().smsSendSucerityCode(phoneName, this, sendSucerityCodeNetType);
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
                break;
            case R.id.land_button:
                passWordStr = passWordV.getText().toString();
                String phoneMessageCodeStr = codeView.getText().toString();
                //检测字符串是否正确
                String str = textRegisterInfo(phoneName, passWordStr, phoneMessageCodeStr);
                if (str.equals("")) {
                    NetToSend.getInstance().appUserToRegister(phoneName, passWordStr, this, appRegisterNetType);
                } else {
                    showToast(str);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            passWordV.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else
            passWordV.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    public void requestFailure(Request request, IOException e) {
        print(e.toString());
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
                    if (!TextUtils.isEmpty(phoneName) && !TextUtils.isEmpty(passWordStr)) {
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
            print(msg.getM());
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
        if (!phoneNameStr.equals(phoneName))
            return "获取验证码的手机号码和现在的手机号码不一致，请重新获取";
        if (TextUtils.isEmpty(phoneMessageCodeStr))
            return "手机验证码为空";
        if (!phoneMessageCodeStr.equals(sucerityCode))
            return "手机验证码错误";
        return "";
    }

    /**
     * 设置文本点击事件
     */
    private void setTextClick() {
        String text1 = "点击“确定”即代表你已经阅读并同意";
        String text2 = "《用户服务协议》";
        String text3 = "及其";
        String text4 = "附属文件";
        SpannableString spannableString = new SpannableString(text1 + text2 + text3 + text4);
        //设置点击事件  参数一：事件   参数二： 事件开始的文字的索引  参数三：设置事件文字的结束索引   参数四：一个标记
        spannableString.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                toActivity(UserContractActivity.class);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // TODO Auto-generated method stub
                ds.setColor(Color.RED); //点击的文字的颜色
                ds.setUnderlineText(false); //是否有下划线
            }
        }, text1.length(), text1.length() + text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置点击事件  参数一：事件   参数二： 事件开始的文字的索引  参数三：设置事件文字的结束索引   参数四：一个标记
        spannableString.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                toActivity(LawClauseActivity.class);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // TODO Auto-generated method stub
                ds.setColor(Color.RED); //点击的文字的颜色
                ds.setUnderlineText(false); //是否有下划线
            }
        }, text1.length() + text2.length() + text3.length(), text1.length() + text2.length() + text3.length() + text4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        explainView.setText(spannableString);
        explainView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置否则无效
    }
}
