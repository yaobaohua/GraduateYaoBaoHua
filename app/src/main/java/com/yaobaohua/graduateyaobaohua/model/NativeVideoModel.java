package com.yaobaohua.graduateyaobaohua.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * @Author yaobaohuae
 * @CreatedTime 2015/12/22 22：53
 * @DESC :
 */
@Table(name = "NativeVideoModel")
public class NativeVideoModel {

    @Column(name = "id", isId = true)
    private String id;
    @Column(name = "videoName")
    private String videoName;

    @Column(name = "videoPath", property = "UNIQUE")
    private String videoPath;
    @Column(name = "cutUrlPath")
    private String cutUrlPath;
    @Column(name = "videoSize")
    private String videoSize;

    public String getVideoSize() {
        return videoSize;
    }

    public NativeVideoModel() {
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

    @Override
    public String toString() {
        return "NativeVideoModel{" +
                "videoName='" + videoName + '\'' +
                ", videoPath='" + videoPath + '\'' +
                ", cutUrlPath='" + cutUrlPath + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NativeVideoModel(String id, String videoName, String videoPath, String cutUrlPath, String videoSize) {
        this.id = id;
        this.videoName = videoName;
        this.videoPath = videoPath;
        this.videoSize = videoSize;
        this.cutUrlPath = cutUrlPath;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setCutUrlPath(String cutUrlPath) {
        this.cutUrlPath = cutUrlPath;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getCutUrlPath() {
        return cutUrlPath;
    }
}
