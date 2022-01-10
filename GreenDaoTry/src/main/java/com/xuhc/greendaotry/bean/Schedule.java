package com.xuhc.greendaotry.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

@Entity //@Entity 将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
public class Schedule {

    //@Id：主键，通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Property(nameInDb = "_id")
    @Id(autoincrement = true)
    private Long myId;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int remind;
    
    @NotNull// @NotNull 设置数据库表当前列不能为空
    private String details;

    @Generated(hash = 1394940136)
    public Schedule(Long myId, int year, int month, int day, int hour, int minute,
            int remind, @NotNull String details) {
        this.myId = myId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.remind = remind;
        this.details = details;
    }

    @Generated(hash = 729319394)
    public Schedule() {
    }

    public Long getMyId() {
        return this.myId;
    }

    public void setMyId(Long myId) {
        this.myId = myId;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getRemind() {
        return this.remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}