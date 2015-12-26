package com.yaobaohua.graduateyaobaohua.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yaobaohua.graduateyaobaohua.model.VideoInfo;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/24 22：57
 * @DESC :
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String DATABASE_NAME = "Yao_Record";
    public static int DATABASE_VERSION = 1;

    // 创建表
    private static final String url = "create table if not exists "
            + VideoInfo.Video.TABLE_NAME + "(" + VideoInfo.Video.VIDEO_ID
            + " integer primary key autoincrement,"
            + VideoInfo.Video.VIDEO_NAME + " text unique,"
            + VideoInfo.Video.VIDEO_PATH
            + " text unique," + VideoInfo.Video.VIDEO_PROGRESS + " text,"
            + VideoInfo.Video.VIDEO_SIZE + " text," + VideoInfo.Video.VIDEO_TYPE + " text,"
            + VideoInfo.Video.VIDEO_NATIVE + " text)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(url);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + VideoInfo.Video.TABLE_NAME);
        onCreate(db);
    }


}
