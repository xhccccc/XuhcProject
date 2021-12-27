package com.xuhc.sharedpreferenceutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * sp工具类
 * 相同key目前只支持覆盖存储
 **/
public class SharedPreferenceUtil {

    /**
     * key值，按需求自定义
     **/
    public static final String SP_TRY = "sp_try";

    /**
     * TABLE值
     **/
    public static final String TABLE_INT = "sp_int";
    public static final String TABLE_STRING = "sp_string";
    public static final String TABLE_BOOLEAN = "sp_boolean";
    public static final String TABLE_LIST = "sp_list";

    /*** Start------sp存储int类型------Start ***/
    public static void setIntKey(Context context, String key, int value) {
        setIntKey(context,TABLE_INT,key,value);
    }

    public static int getIntKey(Context context, String key, int defaultValue) {
        return getIntKey(context,TABLE_INT,key,defaultValue);
    }

    public static void setIntKey(Context context, String table, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(table, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static int getIntKey(Context context, String table, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(table, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }


    /*** Start------sp存储String类型------Start ***/
    public static void setStringKey(Context context, String key, String value) {
        setStringKey(context,TABLE_STRING,key,value);
    }

    public static String getStringKey(Context context, String key, String defaultValue) {
        return getStringKey(context,TABLE_STRING,key,defaultValue);
    }

    public static void setStringKey(Context context, String table, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(table, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getStringKey(Context context, String table, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(table, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }


    /*** Start------sp存储boolean类型------Start ***/
    public static void setBooleanKey(Context context, String key, boolean value) {
        setBooleanKey(context,TABLE_BOOLEAN,key,value);
    }

    public static boolean getBooleanKey(Context context, String key, boolean defaultValue) {
        return getBooleanKey(context,TABLE_BOOLEAN,key,defaultValue);
    }

    public static void setBooleanKey(Context context, String table, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(table, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanKey(Context context, String table, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(table, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }


    /*** Start------sp存储List<>类型(AppInfo为例)------Start ***/
    public static void setListKey(Context context, String key, List<AppInfo> value){
        Gson gson = new Gson();
        String json = gson.toJson(value);
        setListKey(context,TABLE_LIST,key,json);
    }

    public static void setListKey(Context context, String table, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(table, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static List<AppInfo> getListKey(Context context, String key) {
        Gson gson = new Gson();
        String result = getListKey(context,key,"error");
        if (!result.equals("error")){
            return gson.fromJson(result,new TypeToken<List<AppInfo>>(){}.getType());
        } else {
            return new ArrayList<>();
        }
    }

    public static String getListKey(Context context, String key, String defaultValue){
        return getListKey(context,TABLE_LIST,key,defaultValue);
    }

    public static String getListKey(Context context, String table, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(table, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

}