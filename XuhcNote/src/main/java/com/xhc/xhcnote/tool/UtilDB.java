package com.xhc.xhcnote.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangqie on 2015/6/26.
 */

public class UtilDB {

    public static final String DATABASE_NAME = "User";//库名

    public static final String DATABASE_TABLE = "UserInfo";//表单名

    public static final int DATABASE_VERION = 1;//版本

    //字段
    public static final String USER_ID = "id";

    public static final String USER_CONTENT = "content";

    public static final String USER_YEAR_TIME = "useryear";

    //建表Sql语句
    public static final String showCreateSql(){
        return "create table "+DATABASE_TABLE+"("+USER_ID+" integer primary key autoincrement,"
                +USER_CONTENT+ " text," + USER_YEAR_TIME+ " text)";
    }

    /***
     * 获取当前日历
     * @return
     */
    public static final String showTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日-hh:mm");
        return simpleDateFormat.format(new Date());
    }

}
