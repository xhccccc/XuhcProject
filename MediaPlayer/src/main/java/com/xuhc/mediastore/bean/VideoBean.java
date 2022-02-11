package com.xuhc.mediastore.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author hekh
 * @Date 21/10/25 9:42
 */
public class VideoBean extends MediaBean {
    String duration;

    public VideoBean() {
    }

    public VideoBean(String name, String path, String duration) {
        super(name, path);
        this.duration = duration;
    }

    protected VideoBean(Parcel in) {
        super(in);
        duration = in.readString();
    }

    public static final Parcelable.Creator<VideoBean> CREATOR = new Parcelable.Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "duration='" + duration + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(duration);
    }
}
