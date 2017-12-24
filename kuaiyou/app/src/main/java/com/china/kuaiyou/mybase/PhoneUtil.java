package com.china.kuaiyou.mybase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by xiefuning on 2017/4/13.
 * about:
 */

public class PhoneUtil implements ActivityCompat.OnRequestPermissionsResultCallback {
    private TelephonyManager manager;
    private Context context;
    private PackageInfo packageInfo;
    private static PhoneUtil phoneUtil;

    private PhoneUtil() {
        context = BaseAppliction.getContext();
        manager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static PhoneUtil getInstance() {
        if (phoneUtil == null) {
            synchronized (PhoneUtil.class) {
                if (phoneUtil == null) {
                    phoneUtil = new PhoneUtil();
                }
            }
        }
        return phoneUtil;
    }


    public String getImei() {
        String imei="";
        if (textPermission())
            imei = manager.getDeviceId();
        return TextUtils.isEmpty(imei) ? "" : imei;
    }

    private boolean textPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    public String getImsi() {
        String imsi="";
        if (textPermission())
            imsi = manager.getDeviceId();
        return TextUtils.isEmpty(imsi) ? "" : imsi;
    }

    public String getUUid() {
        String uuidStr = MySharePreferences.getInstance().getString("uuid");
        if (TextUtils.isEmpty(uuidStr) || uuidStr.length() == 0) {
            UUID uuid = new UUID(getMac().hashCode(), getImei().hashCode());
        }
        return uuidStr;
    }


//    public String getCid() {
//        String channelName = AnalyticsConfig.getChannel(content);
//        return channelName;
//    }

    public String getMac() {
        String mac = WifiAdmin.getInstance().getMacAddress();
        return TextUtils.isEmpty(mac) ? "" : mac;
    }

    /**
     * VersionCode升级App版本时用
     *
     * @return
     */
    public String getVersionCode() {
        if (packageInfo != null)
            return packageInfo.versionCode + "";
        return "";
    }
    //VersionCode升级App版本时用，VersionName显示给用户

    /**
     * VersionName显示给用户
     *
     * @return
     */
    public String getVersionName() {
        if (packageInfo != null)
            return packageInfo.versionName;
        return "";
    }

    public String getAppName() {
        if (packageInfo != null) {
            //获取albelRes
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } else {
            return "";
        }

    }

    public String getCountryId() {
        String CountryID="";
        if (textPermission())
            CountryID= manager.getSimCountryIso().toUpperCase();
        if (TextUtils.isEmpty(CountryID)) {
            if (CountryID.length() > 0)
                return CountryID;
        }
        return manager.getNetworkCountryIso();
    }

    public String getDeviceId() {
        String imsi="";
        if (textPermission())
            imsi = manager.getDeviceId();
        return TextUtils.isEmpty(imsi) ? "" : imsi;
    }

    public String getDeviceName() {
        return Build.MODEL;
    }


    public String getLang() {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }


    public String getSDK_INT() {
        return Build.VERSION.SDK_INT + "";
    }

    public String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getIp() {
        return WifiAdmin.getInstance().getIpAddressStr();
    }

    public String getRatio(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        StringBuilder sb = new StringBuilder();
        sb.append(width);
        sb.append("*");
        sb.append(height);
        return sb.toString();
    }

    /**
     * 屏幕是否横屏
     */
    public boolean screenIsLand(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (width > height) {
            return true;
        }
        return false;
    }

    public String getAndroidId() {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return TextUtils.isEmpty(androidId) ? "" : androidId;
    }

    /**
     * 0是无网,1WIFI.2为2G，3为3G,4为4G
     */
    public String getNetType() {
        String netType = AppNetUtil.getInstance().getNetWorkStatus() + "";
        return netType;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}
