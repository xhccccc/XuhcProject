package com.xhc.xhcnote.bean;

/**
 * Created by zhangqie on 2015/6/26.
 */

public class UserInfo {

    private String id;
    private String userContent;
    private String userYearTime;
    private boolean Select;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }

    public String getUserYearTime() {
        return userYearTime;
    }

    public void setUserYearTime(String userYearTime) {
        this.userYearTime = userYearTime;
    }

    public boolean isSelect() {
        return Select;
    }

    public void setSelect(boolean select) {
        Select = select;
    }
}
