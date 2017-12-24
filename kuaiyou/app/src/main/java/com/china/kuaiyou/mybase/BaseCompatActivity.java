package com.china.kuaiyou.mybase;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;


/**
 * @author 黎潇
 * @ClassName: 自定义BASEACITIVITY
 * @Description:哥就是这么拽
 * @date 2015-11-26 11:00:03
 */
public class BaseCompatActivity extends AppCompatActivity {
    protected String tag;
    protected Context context;
    protected MyWindowSet myWindowSet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityClose.getInstance().add(this);
        if (myWindowSet == null) {
            myWindowSet = new MyWindowSet(this);
        }
        myWindowSet.setKeepScreenOn();
        myWindowSet.setScreenVertical();
        if (tag == null) {
            tag = this.getClass().toString() + ">>>";
        }
        context = this;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        LG.d(tag, "onStart()");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        LG.d(tag, "onResume()");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        LG.d(tag, "onPause()");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LG.d(tag, "onStop()");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LG.d(tag, "onDestroy()");
        ActivityClose.getInstance().delete(this);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        LG.d(tag, "onRestart()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        LG.d(tag, "onNewIntent()");
    }


    // 打印弹窗消息print
    protected void showToast(String messge) {
        ToastUtil.showToast(messge);
    }

    // 打印弹窗消息print
    protected void showToastLong(String messge) {
        Toast.makeText(this, messge, Toast.LENGTH_LONG).show();
    }

    // 直接跳转活动
    protected void toActivity(Class toClass) {
        Intent intent = new Intent(this, toClass);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//            overridePendingTransition(R.anim.anim_enter,
//                    R.anim.anim_exit);
//        ActivityChangeAnim.set(this);
    }

    // 带数据跳转活动
    protected void toActivity(Class toClass, String gsonString) {
        Intent intent = new Intent(this, toClass);
        intent.putExtra("tb", gsonString);
        startActivity(intent);
    }

    //获得传的数据
    protected String getIntentString() {
        return getIntent().getStringExtra("tb");
    }


}
