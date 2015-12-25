package com.yaobaohua.graduateyaobaohua.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;

import java.text.DecimalFormat;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/23 11：25
 * @DESC :
 */
public class FileSizeFormatUtils {
    public static String formatSize(long size) {
        if (size < 1048576L)
            return new DecimalFormat("##0").format((float) size / 1024f) + "K";
        else if (size < 1073741824L)
            return new DecimalFormat("###0.##").format((float) size / 1048576f)
                    + "M";
        else
            return new DecimalFormat("#######0.##")
                    .format((float) size / 1073741824f) + "G";
    }

    public static String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public  static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;

    }
}