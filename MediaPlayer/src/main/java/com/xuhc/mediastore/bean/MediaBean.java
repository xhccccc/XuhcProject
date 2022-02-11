package com.xuhc.mediastore.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;


/**
 * @Author hekh
 * @Date 21/10/25 9:39
 */
@JsonAdapter(MediaBeanDeserializerAdapter.class)
public class MediaBean implements Parcelable {
    String name;
    String path;

    public MediaBean() {
    }

    public MediaBean(String name, String path) {
        this.name = name;
        this.path = path;
    }

    protected MediaBean(Parcel in) {
        name = in.readString();
        path = in.readString();
    }

    public static final Creator<MediaBean> CREATOR = new Creator<MediaBean>() {
        @Override
        public MediaBean createFromParcel(Parcel in) {
            return new MediaBean(in);
        }

        @Override
        public MediaBean[] newArray(int size) {
            return new MediaBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
    }
}
