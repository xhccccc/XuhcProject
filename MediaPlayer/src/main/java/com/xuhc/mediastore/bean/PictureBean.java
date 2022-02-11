package com.xuhc.mediastore.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author hekh
 * @Date 21/10/25 9:44
 */
public class PictureBean extends MediaBean implements Parcelable {
    String size;

    public PictureBean() {
    }

    public PictureBean(String name, String path, String size) {
        super(name, path);
        this.size = size;
    }

    protected PictureBean(Parcel in) {
        super(in);
        size = in.readString();
    }

    public static final Creator<PictureBean> CREATOR = new Creator<PictureBean>() {
        @Override
        public PictureBean createFromParcel(Parcel in) {
            return new PictureBean(in);
        }

        @Override
        public PictureBean[] newArray(int size) {
            return new PictureBean[size];
        }
    };

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "size='" + size + '\'' +
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
        dest.writeString(size);
    }
}
