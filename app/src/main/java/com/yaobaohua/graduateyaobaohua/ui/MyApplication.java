package com.yaobaohua.graduateyaobaohua.ui;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.utils.ActivityManager;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.x;

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

    public static MyApplication getInstance() {
        return instance;
    }

    public static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("yaobd")
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                }
            });

    public static DbManager db;

    public  static DbManager getDbManager() {
        return db;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        db = x.getDb(MyApplication.daoConfig);

        // 初始化自定义Activity管理器
        activityManager = ActivityManager.getActivityManager();
    }

    /**
     * 注销登录清除sp缓存
     */
    public void clearUserNameAndPassWord() {

        SPUtils.remove(getApplicationContext(), Constants.USERNAME);
        SPUtils.remove(getApplicationContext(), Constants.PASSWORD);
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public String getAppVersion() {
        String versionName = "";
        try {
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
     *
     * @return
     */
    public String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "";
        try {
            imei = telephonyManager.getDeviceId().toLowerCase();
            if (imei.equals("000000000000000")) {
                imei = "device_number" + (long) (Math.random() * 1000000000000L);
            }
            imei += "_yaobaohua";
            SPUtils.put(this, Constants.EMULATOR_DEVICE_ID, imei);
        } catch (Exception e) {
            String tmp = (String) SPUtils.get(this, Constants.EMULATOR_DEVICE_ID, "");
            if (tmp.equals("")) {
                imei = (new StringBuilder("device_number")).append((new Random(System.currentTimeMillis())).nextLong()).toString();
                SPUtils.put(this, Constants.EMULATOR_DEVICE_ID, imei);
            } else {
                imei = tmp;
            }
        }

        return imei;
    }

    public void exit() {
        activityManager.popAllActivity();
    }
}
