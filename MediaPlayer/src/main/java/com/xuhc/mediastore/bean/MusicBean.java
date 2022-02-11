package com.xuhc.mediastore.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author hekh
 * @Date 21/10/22 9:29
 */
public class MusicBean implements Parcelable {
    private Long id;
    String name;
    String path;
    private String author;

    private String title;

    private boolean isSelect = false;

    public MusicBean() {
    }

    public MusicBean(String name, String title, String path, String author) {
        this.name = name;
        this.path = path;
        this.author = author;
        this.title = title;
        this.isSelect = false;
    }

    public MusicBean(String name, String title, String path, String author, boolean isSelect) {
        this.name = name;
        this.path = path;
        this.author = author;
        this.title = title;
        this.isSelect = isSelect;
    }

    protected MusicBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        path = in.readString();
        author = in.readString();
        title = in.readString();
        isSelect = in.readByte() != 0;
    }

    public MusicBean(Long id, String name, String path, String author, String title, boolean isSelect) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.author = author;
        this.title = title;
        this.isSelect = isSelect;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "author='" + author + '\'' +
                ", isSelect=" + isSelect +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MusicBean musicBean = (MusicBean) o;
        return this.path.equals(musicBean.path);
    }


    public boolean getIsSelect() {
        return this.isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
