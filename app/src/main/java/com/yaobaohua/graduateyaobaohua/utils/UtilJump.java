package com.yaobaohua.graduateyaobaohua.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

/**
 * @Author yaobaohuae
 * @CreatedTime 2015/12/21 17/08
 * @DESC :
 */

public class UtilJump {
    public UtilJump() {
    }

    public static void jump2Act4Int(Context fromAct, Class<?> toAct, String k, int v) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra(k, v);
        fromAct.startActivity(intent);
    }

    public static void jump2Act4Boolean(Context fromAct, Class<?> toAct, String k, boolean v) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra(k, v);
        fromAct.startActivity(intent);
    }

    public static void jump2Act4Parcelable(Context fromAct, Class<?> toAct, String k, Parcelable v) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra(k, v);
        fromAct.startActivity(intent);
    }

    public static void jump2Act(Context fromAct, Class<?> toAct, String... args) {
        Intent intent = new Intent(fromAct, toAct);
        int count = args.length / 2;

        for (int i = 0; i < count; ++i) {
            intent.putExtra(args[i * 2], args[i * 2 + 1]);
        }

        fromAct.startActivity(intent);
    }

    public static void jump2ActForResult(Activity fromAct, int requestCode, Class<?> toAct, String... args) {
        Intent intent = new Intent(fromAct, toAct);
        int count = args.length / 2;

        for (int i = 0; i < count; ++i) {
            intent.putExtra(args[i * 2], args[i * 2 + 1]);
        }

        fromAct.startActivityForResult(intent, requestCode);
    }

    public static void jump2ActForResult(Activity fromAct, int requestCode, Class<?> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        fromAct.startActivityForResult(intent, requestCode);
    }

    public static void jump2Act(Context fromAct, Class<?> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        fromAct.startActivity(intent);
    }

    public static void jump2ActFromNoAct(Context fromAct, Class<?> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.addFlags(276824064);
        fromAct.startActivity(intent);
    }

    public static void jump2ActFromNoActForResult(Context fromAct, Class<?> toAct, String... args) {
        Intent intent = new Intent(fromAct, toAct);
        int count = args.length / 2;

        for (int i = 0; i < count; ++i) {
            intent.putExtra(args[i * 2], args[i * 2 + 1]);
        }

        intent.addFlags(276824064);
        fromAct.startActivity(intent);
    }

    public static void JumpBackNew(Context fromAct, Class<?> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.addFlags(67108864);
        fromAct.startActivity(intent);
    }

    public static void JumpBackNew(Context fromAct, Class<?> toAct, String... args) {
        Intent intent = new Intent(fromAct, toAct);
        intent.addFlags(67108864);
        int count = args.length / 2;

        for (int i = 0; i < count; ++i) {
            intent.putExtra(args[i * 2], args[i * 2 + 1]);
        }

        fromAct.startActivity(intent);
    }

    public static void JumpBySingle(Context fromAct, Class<?> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.addFlags(131072);
        fromAct.startActivity(intent);
    }

    public static void JumpBySingle(Context fromAct, Class<?> toAct, String... args) {
        Intent intent = new Intent(fromAct, toAct);
        intent.addFlags(131072);
        int count = args.length / 2;

        for (int i = 0; i < count; ++i) {
            intent.putExtra(args[i * 2], args[i * 2 + 1]);
        }

        fromAct.startActivity(intent);
    }

    public static void JumpBySingle(Context fromAct, Class<?> toAct, String key, int value) {
        Intent intent = new Intent(fromAct, toAct);
        intent.addFlags(131072);
        intent.putExtra(key, value);
        fromAct.startActivity(intent);
    }

    public static void JumpBack(Context fromAct, Class<?> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.setFlags(67108864);
        intent.addFlags(536870912);
        fromAct.startActivity(intent);
    }

    public static void JumpBack(Context fromAct, Class<?> toAct, String... args) {
        Intent intent = new Intent(fromAct, toAct);
        intent.setFlags(67108864);
        intent.addFlags(536870912);
        int count = args.length / 2;

        for (int i = 0; i < count; ++i) {
            intent.putExtra(args[i * 2], args[i * 2 + 1]);
        }

        fromAct.startActivity(intent);
    }

    public static void jumpHome(Context fromAct) {
        Intent i = new Intent("android.intent.action.MAIN");
        i.setFlags(268435456);
        i.addCategory("android.intent.category.HOME");
        fromAct.startActivity(i);
        ((Activity) fromAct).overridePendingTransition(17432576, 17432577);
    }

}
