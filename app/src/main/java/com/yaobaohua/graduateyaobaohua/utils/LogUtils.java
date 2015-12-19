package com.yaobaohua.graduateyaobaohua.utils;

import android.text.TextUtils;
import android.util.Log;

import com.yaobaohua.graduateyaobaohua.BuildConfig;


//Logcat统一管理类
public class LogUtils {

    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = BuildConfig.DEBUG;// 是否需要打印bug，debug与release的buildconfig，也可以自定义
    private static final String TAG = "YBH";//默认情况下的前缀

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(TAG) ? tag : TAG + ":" + tag;
        return tag;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (!isDebug) return;
        String TAG = generateTag(Thread.currentThread().getStackTrace()[3]);
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (!isDebug) return;
        String TAG = generateTag(Thread.currentThread().getStackTrace()[3]);
        Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (!isDebug) return;
        String TAG = generateTag(Thread.currentThread().getStackTrace()[3]);
        Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (!isDebug) return;
        String TAG = generateTag(Thread.currentThread().getStackTrace()[3]);
        Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }
}