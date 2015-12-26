package com.yaobaohua.graduateyaobaohua.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/24 22：30
 * @DESC :
 */
public class Video implements Parcelable {

    private int video_Id;

    private String video_Name;

    private String video_Size;

    private String video_Path;

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
    private String video_Native;

    public String getVideo_Native() {
        return video_Native;
    }

    public void setVideo_Native(String video_Native) {
        this.video_Native = video_Native;
    }

    public Video() {
    }

    protected Video(Parcel in) {
        video_Id = in.readInt();
        video_Name = in.readString();
        video_Size = in.readString();
        video_Path = in.readString();
        video_Progress = in.readString();
        video_Type = in.readString();
        video_Native=in.readString();
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

    @Override
    public String toString() {
        return "Video{" +
                "video_Id=" + video_Id +
                ", video_Name='" + video_Name + '\'' +
                ", video_Size='" + video_Size + '\'' +
                ", video_Path='" + video_Path + '\'' +
                ", video_Progress='" + video_Progress + '\'' +
                ", video_Type='" + video_Type + '\'' +
                ", video_Native='" + video_Native + '\'' +
                '}';
    }

    public Video(String video_Name, String video_Size, String video_Path, String video_progress, String video_type, String video_Native) {
        this.video_Name = video_Name;
        this.video_Size = video_Size;
        this.video_Path = video_Path;
        this.video_Progress = video_progress;
        this.video_Type = video_type;
        this.video_Native=video_Native;
    }

    public Video(int video_id, String video_Name, String video_Size, String video_Path, String video_progress, String video_Type,String video_Native) {
        this.video_Id = video_id;
        this.video_Name = video_Name;
        this.video_Size = video_Size;
        this.video_Path = video_Path;
        this.video_Progress = video_progress;
        this.video_Type = video_Type;
        this.video_Native=video_Native;
    }

    public int getVideo_id() {
        return video_Id;
    }

    public void setVideo_id(int video_id) {
        this.video_Id = video_id;
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

    public String getVideo_progress() {
        return video_Progress;
    }

    public void setVideo_progress(String video_progress) {
        this.video_Progress = video_progress;
    }

    public String getVideo_type() {
        return video_Type;
    }

    public void setVideo_type(String video_type) {
        this.video_Type = video_type;
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
        dest.writeString(video_Native);
    }
}
