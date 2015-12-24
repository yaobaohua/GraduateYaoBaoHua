package com.yaobaohua.graduateyaobaohua.common;

import android.os.Environment;

import java.io.File;

/**
 * @create yaobaohua
 * @Description: 常量类
 * @date 2015年3月26日 下午5:51:37
 */
public class Constants {
    /**
     * 是否是第一次进程序的标志
     */
    public static final String IS_FIRST = "IS_FIRST";
    public static final String BASE_URL = "";
    public static final String USERNAME = "username";
    public static final String ISONLINE = "isOnline";
    public final static String PASSWORD = "password";
    public final static String IS_FIRST_NATIVE = "is_first";


    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String EMULATOR_DEVICE_ID = "EMULATOR_DEVICE_ID";
    public static final String VERSION_NUMBER = "VERSION_NUMBER";
    public static final String USERPWD = "USERPWD";
    public static final String USERTYPE = "USERTYPE";
    public static final String LASTTIME_AFFAIRMAIN = "lastTimeAffairMain";
    public static final String COOKIE_KEY = "COOKIE_KEY";
    public static final String COOKIE_VALUE = "COOKIE_VALUE";
    public static final String PUSH = "PUSH_SWITCH";
    public static final String APPCODE_KEY = "APPCODE_KEY";
    public static final String NOTIFICATION_ICON = "NOTIFICATION_ICON";


    //app公用的字段
    public final static String APP_ID = "appId";
    public final static String APP_Version = "appVersion";
    public final static String ACCESS_TOKEN = "accessToken";

    public final static String DOWN_LOAD_PATH = Environment.getExternalStorageDirectory() + File.separator + "yaoMovie";


}
