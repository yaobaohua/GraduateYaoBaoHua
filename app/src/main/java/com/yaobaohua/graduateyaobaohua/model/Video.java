package com.yaobaohua.graduateyaobaohua.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/24 22：30
 * @DESC :
 */
public class Video implements Parcelable {

    private int video_id;

    private String video_Name;

    private String video_Size;

    private String video_Path;

    private String video_progress;

    /*
     * 1:直播，不要进度条，不要下载条
     * 2.本地，要进度条，不要下载条
     * 3.网络，都要
     */
    private String video_type;

    public Video() {
    }

    protected Video(Parcel in) {
        video_id = in.readInt();
        video_Name = in.readString();
        video_Size = in.readString();
        video_Path = in.readString();
        video_progress = in.readString();
        video_type = in.readString();
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
                "video_id=" + video_id +
                ", video_Name='" + video_Name + '\'' +
                ", video_Size='" + video_Size + '\'' +
                ", video_Path='" + video_Path + '\'' +
                ", video_progress='" + video_progress + '\'' +
                ", video_type='" + video_type + '\'' +
                '}';
    }

    public Video(String video_Name, String video_Size, String video_Path, String video_progress, String video_type) {
        this.video_Name = video_Name;
        this.video_Size = video_Size;
        this.video_Path = video_Path;
        this.video_progress = video_progress;
        this.video_type = video_type;
    }

    public Video(int video_id, String video_Name, String video_Size, String video_Path, String video_progress, String video_type) {
        this.video_id = video_id;
        this.video_Name = video_Name;
        this.video_Size = video_Size;
        this.video_Path = video_Path;
        this.video_progress = video_progress;
        this.video_type = video_type;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
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
        return video_progress;
    }

    public void setVideo_progress(String video_progress) {
        this.video_progress = video_progress;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(video_id);
        dest.writeString(video_Name);
        dest.writeString(video_Size);
        dest.writeString(video_Path);
        dest.writeString(video_progress);
        dest.writeString(video_type);
    }
}
