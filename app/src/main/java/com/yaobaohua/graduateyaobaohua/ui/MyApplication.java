package com.yaobaohua.graduateyaobaohua.ui;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.lidroid.xutils.BitmapUtils;
import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.utils.ActivityManager;

import java.io.File;
import java.util.Random;

/**
 * Created by yaobaohua on 2015/12/18 0018.
 * Email 2584899504@qq.com
 */
public class MyApplication extends Application {
    private ActivityManager activityManager = null;

    public ActivityManager getActivityManager() {
        return activityManager;
    }

    private static MyApplication instance;
    public BitmapUtils bitmapUtils;

    public static MyApplication getInstance() {
        return instance;
    }

    public String headIconPath = Environment.getExternalStorageDirectory().getPath() + "/banking/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        bitmapUtils = new BitmapUtils(this);
        bitmapUtils.configDefaultLoadingImage(R.mipmap.plugin_camera_no_pictures);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.plugin_camera_no_pictures);
        //设置Log是否显示
//        MULog.setDEBUG(true);
//        File headIconFile = new File(headIconPath);
//        if (!headIconFile.exists()) {
//            headIconFile.mkdirs();
//        }
//        //ImageLoader注入
//        VVImageLoader imageManager = new VVImageLoader();
//        imageManager.initDefault(getApplicationContext());
//
//        MUFileUtil.makeDir(headIconPath);
        // 初始化自定义Activity管理器
       activityManager = ActivityManager.getActivityManager();
    }

    /**
     * 注销登录清除sp缓存
     */
    public void clearPref() {
//        ActionCommon.removePreference(getApplicationContext(), ConstantPref.USERNAME);
//        ActionCommon.removePreference(getApplicationContext(), ConstantPref.USERPWD);
    }

    /**
     * 获取版本号
     * @return
     */
    public String getAppVersion() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            if (pi.versionName == null || pi.versionName.length() <= 0) {
                versionName = "1.0";
            } else {
                versionName = pi.versionName;
            }
        } catch (Exception e) {
            versionName = "1.0";
        }
        return versionName;
    }

    /**
     * 获取设备号
     * @return
     */
//    public String getIMEI() {
//        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        String imei = "";
//        try {
//            imei = telephonyManager.getDeviceId().toLowerCase();
//            if (imei.equals("000000000000000")) {
//                imei = "emu" + (long) (Math.random() * 1000000000000L);
//            }
//            imei += "_iwindreporter";
//            MULog.i("deviceid", "deviceId=" + imei);
//            ActionCommon.writePreference(this, ConstantPref.EMULATOR_DEVICE_ID, imei);
//        } catch (Exception e) {
//
//            String tmp = ActionCommon.readPreference(this, ConstantPref.EMULATOR_DEVICE_ID, "");
//            if (tmp.equals("")) {
//                imei = (new StringBuilder("emu")).append((new Random(System.currentTimeMillis())).nextLong()).toString();
//                MULog.i("deviceid", "Exception not contains deviceId=" + imei);
//                ActionCommon.writePreference(this, ConstantPref.EMULATOR_DEVICE_ID, imei);
//            } else {
//                imei = tmp;
//            }
//        }
//
//        return imei;
//    }

    public void exit() {
        activityManager.popAllActivity();
    }
}
