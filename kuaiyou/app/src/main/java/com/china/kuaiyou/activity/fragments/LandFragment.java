package com.china.kuaiyou.activity.fragments;


import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.AppFindPassWordActivity;
import com.china.kuaiyou.activity.MainActivity;
import com.china.kuaiyou.activity.view.ClearEditText;
import com.china.kuaiyou.mybase.MyGson;
import com.china.kuaiyou.mybase.MySharePreferences;
import com.china.kuaiyou.netbean.Msg;
import com.china.kuaiyou.netbean.user.AppLogin;
import com.china.kuaiyou.netbean.user.UserBean;
import com.china.kuaiyou.netutil.NetToGet;
import com.china.kuaiyou.netutil.NetToSend;
import com.china.kuaiyou.okhttp.OkHttpManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.IOException;

import okhttp3.Request;


/**
 * @className: LandFragment
 * @purpose: 登录 碎片
 * @athor :
 * @date:2017/11/11 13:23
 */
public class LandFragment extends BaseFragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, OkHttpManager.DataCallBack {

    private static final String TAG = "===" + LandFragment.class.getSimpleName();

    @ViewInject(R.id.weichat_image_view)
    private ImageView weiChatImaga;
    @ViewInject(R.id.qq_image_view)
    private ImageView qqImage;
    @ViewInject(R.id.more_image_view)
    private ImageView moreImage;
    @ViewInject(R.id.email_image_view)
    private ImageView emileImage;
    @ViewInject(R.id.sina_image_view)
    private ImageView sinaImage;
    @ViewInject(R.id.forget_button)
    private Button forgetButton;
    @ViewInject(R.id.land_button)
    private Button landButton;
    @ViewInject(R.id.phone_text_view)
    private ClearEditText phoneView;
    @ViewInject(R.id.password_text_view)
    private ClearEditText passWordView;
    @ViewInject(R.id.pass_show_hide_view)
    private CheckBox showPassV;
    @ViewInject(R.id.area_show_view)
    private Button showareaV;

    private String loginStr, passWord;

    @Override
    protected int getLayout() {
        return R.layout.fragment_land;
    }

    @Override
    protected void initView() {
        showPassV.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initObject() {
        //实现自动登陆
        String str = MySharePreferences.getInstance().getString("user");
        if (!TextUtils.isEmpty(str)) {
            AppLogin appLoginBean = MyGson.getInstance().fromJson(str, AppLogin.class);
            NetToSend.getInstance().appUserToLogin(appLoginBean.getlS(), appLoginBean.getpW(), this, 1);
        }

    }

    @OnClick({R.id.weichat_image_view, R.id.qq_image_view, R.id.more_image_view,
            R.id.email_image_view, R.id.sina_image_view, R.id.forget_button, R.id.land_button
            , R.id.area_show_view})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weichat_image_view:
                break;
            case R.id.qq_image_view:
                break;
            case R.id.more_image_view:
                moreImage.setVisibility(View.GONE);
                emileImage.setVisibility(View.VISIBLE);
                sinaImage.setVisibility(View.VISIBLE);
                break;
            case R.id.email_image_view:
                break;
            case R.id.sina_image_view:
                break;
            case R.id.forget_button:
                toActivity(AppFindPassWordActivity.class);
                break;
            case R.id.land_button:
                loginStr = phoneView.getText().toString();
                passWord = passWordView.getText().toString();
                if (!TextUtils.isEmpty(loginStr) && !TextUtils.isEmpty(passWord)) {
                    NetToSend.getInstance().appUserToLogin(loginStr, passWord, this, 1);
                } else {
                    print(getString(R.string.applogin_username_or_password_is_emp));
                }
                break;
            case R.id.area_show_view:
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            passWordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else
            passWordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    public void requestFailure(Request request, IOException e) {
        print(getString(R.string.net_error));
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
                print("登录中..");
                if (!TextUtils.isEmpty(loginStr) && !TextUtils.isEmpty(passWord)) {
                    MySharePreferences.getInstance().putString("user", MyGson.getInstance().toGsonStr(new AppLogin(loginStr, passWord, "", "")));
                }
                MySharePreferences.getInstance().putString("appuser", str);
                toActivity(MainActivity.class, str);
                getActivity().finish();
            } else {
                print("数据解析错误");
            }

        } else {
            print(msg.getM());
        }
    }

}
