package com.china.kuaiyou.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.china.kuaiyou.R;
import com.china.kuaiyou.mybase.BaseWelcomeActivity;
import com.china.kuaiyou.mybase.MySharePreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class WelcomeActivity extends BaseWelcomeActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TO_MAIN:
                    if (TextUtils.isEmpty(userGsonStr)) {
                        userGsonStr = "";
                    }
                    toActivity(MainActivity.class, userGsonStr);
                    finish();
                    break;
                case GET_PERSSIONS_OK:
                    handler.postDelayed(runnable, 2000);
                    break;
            }

        }
    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            userGsonStr = MySharePreferences.getInstance().getString("appuser");
            handler.sendEmptyMessage(TO_MAIN);
        }
    };
    private final int TO_MAIN = 0;
    private final int GET_PERSSIONS_OK = 1;
    private String userGsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_welcome_layout, null);
        setContentView(view);
        initPermissions();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }


    //权限处理
    // 要申请的权限
    List<String> permissions;
    private AlertDialog dialog;
    private int getPermissionsActivityType = 52013;

    @TargetApi(Build.VERSION_CODES.M)
    private void initPermissions() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initNeedPermissions();

            if (checkPermissions()) {
                showToast("获取全部权限成功");
                sendPermissionIsOkMessage();
            } else {
                showToast("获取全部权限失败");
                showDialogTipUserRequestPermission();
            }
        } else {
            sendPermissionIsOkMessage();
        }
    }

    private void initNeedPermissions() {
        permissions = new ArrayList<>();
        //A字头
        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        //C字头
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.CHANGE_NETWORK_STATE);
        permissions.add(Manifest.permission.CHANGE_WIFI_STATE);
        //I字头
        permissions.add(Manifest.permission.INTERNET);
        //V字头
        permissions.add(Manifest.permission.VIBRATE);
        //R字头
        permissions.add(Manifest.permission.READ_PHONE_STATE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        //W字头
        permissions.add(Manifest.permission.WAKE_LOCK);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    //检测是否全部授权
    private boolean checkPermissions() {
        for (int i = 0; i < permissions.size(); i++) {
            if (!checkPermission(permissions.get(i))) {
                return false;
            }
        }
        return true;
    }

    //检测单个权限是否授权
    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermission(String permission) {
        // 检查该权限是否已经获取
        int a = this.checkSelfPermission(permission);
        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
        if (a == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    //发送权限OK的消息
    private void sendPermissionIsOkMessage() {
        handler.sendEmptyMessage(GET_PERSSIONS_OK);
    }

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setTitle("有必须权限不可用")
                .setMessage("由于APP需要获取对应权限才能操作；\n否则，您将无法正常使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        String[] strs = new String[permissions.size()];
        strs = permissions.toArray(strs);
        ActivityCompat.requestPermissions(this, strs, getPermissionsActivityType);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        showToast("返回界面了");
        if (requestCode == getPermissionsActivityType) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean canStar = true;
                for (int i = 0; i < grantResults.length; i++) {
                    canStar = (grantResults[i] == PackageManager.PERMISSION_GRANTED);
                }
                if (canStar) {
                    sendPermissionIsOkMessage();
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                } else {
                    showDialogTipUserGoToAppSettting();
                }

            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("权限不可用")
                .setMessage("请在-应用设置-权限-中，允许使用权限来操作APP")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                if (checkPermissions()) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    sendPermissionIsOkMessage();
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
