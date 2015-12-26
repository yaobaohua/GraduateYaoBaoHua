package com.yaobaohua.graduateyaobaohua.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.model.VideoInfo;
import com.yaobaohua.graduateyaobaohua.utils.ToastUtils;

import java.util.ArrayList;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/24 23：04
 * @DESC :
 */
public class VideoDBManager {


    MyDataBaseHelper dbHelper;
    Context context;

    public VideoDBManager(Context context) {
        dbHelper = new MyDataBaseHelper(context);
        this.context = context;
    }

    /**
     * 添加
     */
    public void insert(Video video) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = VideoInfo.Video.TABLE_NAME;
        ContentValues values = new ContentValues();
        values.put(VideoInfo.Video.VIDEO_NAME, video.getVideo_Name());
        values.put(VideoInfo.Video.VIDEO_PATH, video.getVideo_Path());
        values.put(VideoInfo.Video.VIDEO_PROGRESS, video.getVideo_progress());
        values.put(VideoInfo.Video.VIDEO_SIZE, video.getVideo_Size());
        values.put(VideoInfo.Video.VIDEO_TYPE, video.getVideo_type());
        values.put(VideoInfo.Video.VIDEO_NATIVE, video.getVideo_Native());
        db.execSQL("PRAGMA foreign_keys=1");
        long temp = db.insert(table, null, values);
        if (temp == -1) {
            ToastUtils.show(context, "插入失败", Toast.LENGTH_LONG);
        } else {
            ToastUtils.show(context, "插入成功", Toast.LENGTH_LONG);
        }
        db.close();
    }

    /**
     * 删除
     */
    public void delete(int video_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = VideoInfo.Video.TABLE_NAME;
        String whereClause = VideoInfo.Video.VIDEO_ID + "=?";
        String[] whereArgs = {String.valueOf(video_id)};
        db.execSQL("PRAGMA foreign_keys=1");
        int count = db.delete(table, whereClause, whereArgs);
        if (count > 0) {
            ToastUtils.show(context, "删除成功", Toast.LENGTH_LONG);
        } else {
            ToastUtils.show(context, "删除成功", Toast.LENGTH_LONG);
        }
        db.close();
    }

    /**
     * 更新操作
     */
    public void update(Video video) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = VideoInfo.Video.TABLE_NAME;
        ContentValues values = new ContentValues();

        values.put(VideoInfo.Video.VIDEO_NAME, video.getVideo_Name());
        values.put(VideoInfo.Video.VIDEO_PATH, video.getVideo_Path());
        values.put(VideoInfo.Video.VIDEO_PROGRESS, video.getVideo_progress());
        values.put(VideoInfo.Video.VIDEO_SIZE, video.getVideo_Size());
        values.put(VideoInfo.Video.VIDEO_TYPE, video.getVideo_type());
        values.put(VideoInfo.Video.VIDEO_NATIVE, video.getVideo_Native());

        String whereClause = VideoInfo.Video.VIDEO_ID + "=?";
        String[] whereArgs = {String.valueOf(video.getVideo_id())};
        db.update(table, values, whereClause, whereArgs);
        db.close();
    }

    /**
     * 根据指定Id查找
     *
     * @return
     */
    public Video query(int video_id) {
        Video video = new Video();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = VideoInfo.Video.TABLE_NAME;
        // 返回列表的列,传递null将返回所有列
        String[] columns = null;
        // 一个过滤器声明哪些行返回,格式化为一个SQL WHERE子句(不包括自身)的地方。传递null将返回给定表的所有行。
        String selection = VideoInfo.Video.VIDEO_ID + "=?";
        // 从selectionArgs值所取代,以便他们出现在选择。将被绑定的值作为字符串。
        String[] selectionArgs = new String[]{String.valueOf(video_id)};
        // 一个过滤器声明如何组行,格式化为一个SQL group BY子句(不含集团本身)。传递null将导致行分组。
        String groupBy = null;
        // 过滤器声明哪些行组包含指针,如果使用行分组,格式化SQL有条款(不包括自身)。传递null会导致包括所有行组,分组时需要行并不是被使用
        String having = null;
        // 排序方式
        String orderBy = null;
        Cursor cursor = db.query(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToNext()) {
            video.setVideo_id(cursor.getInt(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_ID)));
            video.setVideo_Name(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_NAME)));
            video.setVideo_Path(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_PATH)));
            video.setVideo_progress(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_PROGRESS)));
            video.setVideo_Size(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_SIZE)));
            video.setVideo_type(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_TYPE)));
            video.setVideo_Native(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_NATIVE)));
        }
        // 释放资源
        cursor.close();
        db.close();
        return video;
    }

    /**
     * 根据指定Id查找
     *
     * @return
     */
    public ArrayList<Video> queryByVideoId(int video_id) {

        ArrayList<Video> listData = new ArrayList<Video>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = VideoInfo.Video.TABLE_NAME;
        // 返回列表的列,传递null将返回所有列
        String[] columns = null;
        // 一个过滤器声明哪些行返回,格式化为一个SQL WHERE子句(不包括自身)的地方。传递null将返回给定表的所有行。
        String selection = VideoInfo.Video.VIDEO_ID + "=?";
        // 从selectionArgs值所取代,以便他们出现在选择。将被绑定的值作为字符串。
        String[] selectionArgs = new String[]{String.valueOf(video_id)};
        // 一个过滤器声明如何组行,格式化为一个SQL group BY子句(不含集团本身)。传递null将导致行分组。
        String groupBy = null;
        // 过滤器声明哪些行组包含指针,如果使用行分组,格式化SQL有条款(不包括自身)。传递null会导致包括所有行组,分组时需要行并不是被使用
        String having = null;
        // 排序方式
        String orderBy = null;
        Cursor cursor = db.query(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        while (cursor.moveToNext()) {
            Video video = new Video();
            video.setVideo_id(cursor.getInt(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_ID)));
            video.setVideo_Name(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_NAME)));
            video.setVideo_Path(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_PATH)));
            video.setVideo_progress(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_PROGRESS)));
            video.setVideo_Size(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_SIZE)));
            video.setVideo_type(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_TYPE)));
            video.setVideo_Native(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_NATIVE)));
            listData.add(video);
        }
        // 释放资源
        cursor.close();
        db.close();
        return listData;
    }

    /**
     * 查找所有
     *
     * @return
     */
    public ArrayList<Video> queryAll() {
        ArrayList<Video> list = new ArrayList<Video>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = VideoInfo.Video.TABLE_NAME;
        // 返回列表的列,传递null将返回所有列
        String[] columns = null;
        // 一个过滤器声明哪些行返回,格式化为一个SQL WHERE子句(不包括自身)的地方。传递null将返回给定表的所有行。
        String selection = null;
        // 从selectionArgs值所取代,以便他们出现在选择。将被绑定的值作为字符串。
        String[] selectionArgs = null;
        // 一个过滤器声明如何组行,格式化为一个SQL group BY子句(不含集团本身)。传递null将导致行分组。
        String groupBy = null;
        // 过滤器声明哪些行组包含指针,如果使用行分组,格式化SQL有条款(不包括自身)。传递null会导致包括所有行组,分组时需要行并不是被使用
        String having = null;
        // 排序方式
        String orderBy = null;
        Cursor cursor = db.query(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        while (cursor.moveToNext()) {
            Video video = new Video();
            video.setVideo_id(cursor.getInt(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_ID)));
            video.setVideo_Name(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_NAME)));
            video.setVideo_Path(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_PATH)));
            video.setVideo_progress(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_PROGRESS)));
            video.setVideo_Size(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_SIZE)));
            video.setVideo_type(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_TYPE)));
            video.setVideo_Native(cursor.getString(cursor
                    .getColumnIndexOrThrow(VideoInfo.Video.VIDEO_NATIVE)));
            list.add(video);
        }
        // 释放资源
        cursor.close();
        db.close();
        return list;
    }
}
