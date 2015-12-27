package com.yaobaohua.graduateyaobaohua.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/26 17：36
 * @DESC :
 */
public class Video implements Parcelable {
    private int video_Id;

    private String video_Name;

    private String video_Size;

    private String video_Path;

    private String video_DownFlag;

    /**
     * 1.播放过
     */
    private String video_Played;

    public Video(String video_Name, String video_Size, String video_Path, String video_DownFlag, String video_Played, String video_Progress, String video_Type) {
        this.video_Name = video_Name;
        this.video_Size = video_Size;
        this.video_Path = video_Path;
        this.video_DownFlag = video_DownFlag;
        this.video_Played = video_Played;
        this.video_Progress = video_Progress;
        this.video_Type = video_Type;
    }

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


    protected Video(Parcel in) {
        video_Id = in.readInt();
        video_Name = in.readString();
        video_Size = in.readString();
        video_Path = in.readString();
        video_Progress = in.readString();
        video_Type = in.readString();
        video_Played = in.readString();
        video_DownFlag = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(video_Id);
        dest.writeString(video_Name);
        dest.writeString(video_Size);
        dest.writeString(video_Path);
        dest.writeString(video_Progress);
        dest.writeString(video_Type);
        dest.writeString(video_Played);
        dest.writeString(video_DownFlag);

    }
}
