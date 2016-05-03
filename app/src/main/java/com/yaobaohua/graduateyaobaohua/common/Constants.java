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
    public static final String USERNAME = "username";
    public static final String ISONLINE = "isOnline";
    public final static String PASSWORD = "password";
    public final static String IS_FIRST_NATIVE = "is_first";

    public final static String QQ_OPEN_ID = "user_openId";
    public final static String USER_OBJECT_ID = "objectId";

    public final static String  VIDEO_USER_ID="video_userId";
    public final static String  VIDEO_PATH="video_Path";
    public final static String  ISLOGIN="is_login";

    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String EMULATOR_DEVICE_ID = "EMULATOR_DEVICE_ID";
    public static final String VERSION_NUMBER = "VERSION_NUMBER";
    public static final String USERPWD = "USERPWD";


    //app公用的字段
    public final static String APP_ID = "appId";
    public final static String APP_Version = "appVersion";
    public final static String ACCESS_TOKEN = "accessToken";
    public final static String QQ_APP_ID = "1105049644";
    public final static String QQ_SCOPE = "all";


    public final static String DOWN_LOAD_PATH = Environment.getExternalStorageDirectory() + File.separator + "yaoMovie";

    public final static String REQUEST_URL="http://192.168.0.101:8080/GraduateYao/api.jsp?";


}
