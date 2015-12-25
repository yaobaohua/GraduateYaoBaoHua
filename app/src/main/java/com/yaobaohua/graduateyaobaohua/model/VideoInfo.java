package com.yaobaohua.graduateyaobaohua.model;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/24 22：50
 * @DESC :
 */
public class VideoInfo {
    public VideoInfo() {

    }

    public static abstract class Video {
        // 表名
        public static final String TABLE_NAME = "play_record_table";
        public static final String VIDEO_ID = "video_id";
        public static final String VIDEO_NAME = "video_Name";
        public static final String VIDEO_SIZE = "video_Size";
        public static final String VIDEO_PATH = "video_Path";
        public static final String VIDEO_PROGRESS = "video_progress";
        public static final String VIDEO_TYPE = "video_type";


    }
}
