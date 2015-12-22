package com.yaobaohua.graduateyaobaohua.model;

import android.graphics.Bitmap;

/**
 * @Author yaobaohuae
 * @CreatedTime 2015/12/22 16：01
 * @DESC :
 */
public class LiveModel {

    private String name;
    private String videoUrl;
    private String logoUrl;

    @Override
    public String toString() {
        return "LiveModel{" +
                "name='" + name + '\'' +
                ", url='" + videoUrl + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }

    public LiveModel(String name, String videoUrl, String logoUrl) {
        this.name = name;
        this.videoUrl = videoUrl;
        this.logoUrl = logoUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public String getvideoUrl() {
        return videoUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
