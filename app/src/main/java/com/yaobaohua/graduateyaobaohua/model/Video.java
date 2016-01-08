package com.yaobaohua.graduateyaobaohua.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/26 17：36
 * @DESC :
 */
public class Video extends BmobObject implements Serializable {
    private int video_Id;

    private String video_userId;
    private String video_previewImg;

    public String getVideo_previewImg() {
        return video_previewImg;
    }

    public void setVideo_previewImg(String video_previewImg) {
        this.video_previewImg = video_previewImg;
    }

    public String getVideo_userId() {
        return video_userId;
    }

    public void setVideo_userId(String video_userId) {
        this.video_userId = video_userId;
    }

    public Video(String video_userId, String video_Name, String video_Size, String video_Path,String video_previewImg, String video_DownFlag, String video_Played, String video_Progress, String video_Type) {
        this.video_userId = video_userId;
        this.video_previewImg=video_previewImg;
        this.video_Name = video_Name;
        this.video_Size = video_Size;
        this.video_Path = video_Path;
        this.video_DownFlag = video_DownFlag;
        this.video_Played = video_Played;
        this.video_Progress = video_Progress;
        this.video_Type = video_Type;
    }


    public Video(String video_Name, String video_Size, String video_Path, String video_DownFlag, String video_Played, String video_Progress, String video_Type) {
        this.video_Name = video_Name;
        this.video_Size = video_Size;
        this.video_Path = video_Path;
        this.video_DownFlag = video_DownFlag;
        this.video_Played = video_Played;
        this.video_Progress = video_Progress;
        this.video_Type = video_Type;
    }

    private String video_Name;

    private String video_Size;

    private String video_Path;

    private String video_DownFlag;

    /**
     * 1.播放过
     */
    private String video_Played;



    public Video(int video_Id, String video_Name, String video_Size, String video_Path, String video_DownFlag, String video_Played, String video_Progress, String video_Type) {
        this.video_Id = video_Id;
        this.video_Name = video_Name;
        this.video_Size = video_Size;
        this.video_Path = video_Path;
        this.video_DownFlag = video_DownFlag;
        this.video_Played = video_Played;
        this.video_Progress = video_Progress;
        this.video_Type = video_Type;
    }

    @Override
    public String toString() {
        return "Video{" +
                "video_Id=" + video_Id +
                ", video_Name='" + video_Name + '\'' +
                ", video_Size='" + video_Size + '\'' +
                ", video_Path='" + video_Path + '\'' +
                ", video_DownFlag='" + video_DownFlag + '\'' +
                ", video_Played='" + video_Played + '\'' +
                ", video_Progress='" + video_Progress + '\'' +
                ", video_Type='" + video_Type + '\'' +
                '}';
    }

    public String getVideo_Played() {
        return video_Played;
    }

    public void setVideo_Played(String video_Played) {
        this.video_Played = video_Played;
    }

    public String getVideo_DownFlag() {
        return video_DownFlag;
    }

    public void setVideo_DownFlag(String video_DownFlag) {
        this.video_DownFlag = video_DownFlag;
    }


    private String video_Progress;


    /*
         * 1:直播，不要进度条，不要下载条
         * 2.本地，要进度条，不要下载条
         * 3.网络，都要
         */
    private String video_Type;

    /**
     * 1.是本地
     * 2.不是本地
     */

    public Video() {
    }




    public int getVideo_Id() {
        return video_Id;
    }

    public void setVideo_Id(int video_Id) {
        this.video_Id = video_Id;
    }

    public String getVideo_Name() {
        return video_Name;
    }

    public void setVideo_Name(String video_Name) {
        this.video_Name = video_Name;
    }

    public String getVideo_Size() {
        return video_Size;
    }

    public void setVideo_Size(String video_Size) {
        this.video_Size = video_Size;
    }

    public String getVideo_Path() {
        return video_Path;
    }

    public void setVideo_Path(String video_Path) {
        this.video_Path = video_Path;
    }

    public String getVideo_Progress() {
        return video_Progress;
    }

    public void setVideo_Progress(String video_Progress) {
        this.video_Progress = video_Progress;
    }

    public String getVideo_Type() {
        return video_Type;
    }

    public void setVideo_Type(String video_Type) {
        this.video_Type = video_Type;
    }

}
