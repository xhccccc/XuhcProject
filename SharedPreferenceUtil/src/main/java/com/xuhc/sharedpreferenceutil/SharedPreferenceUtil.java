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
     * key值,按需求自定义,一个key值对应一个value
     **/
    protected static final String SP_TRY = "sp_try";

    /**
     * TABLE值
     **/
    private static final String TABLE_FILE_NAME = "sp_file_name";

    private volatile static SharedPreferenceUtil instance;
    private final SharedPreferences sp;

    private SharedPreferenceUtil(Context context) {
        sp = context.getSharedPreferences(TABLE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPreferenceUtil.class) {
                if (instance == null) {
                    instance = new SharedPreferenceUtil(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private final List<IPreferenceCallback> preferenceCallbackList = new ArrayList<>();

    protected void addPreferenceChangeListener(IPreferenceCallback callback) {
        if (!preferenceCallbackList.contains(callback)) {
            preferenceCallbackList.add(callback);
        }
    }

    public void removePreferenceChangeListener(IPreferenceCallback callback) {
        preferenceCallbackList.remove(callback);
    }

    private void onPreferenceChange(String key) {
        for (IPreferenceCallback preferenceCallback : preferenceCallbackList) {
            preferenceCallback.onPreferenceChange(key);
        }
    }

    /*** Start------sp存储int类型------Start ***/
    protected void setIntKey(String key, int value) {
        sp.edit().putInt(key,value).apply();
        onPreferenceChange(key);
    }

    protected int getIntKey(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /*** Start------sp存储String类型------Start ***/
    protected void setStringKey(String key, String value) {
        sp.edit().putString(key,value).apply();
        onPreferenceChange(key);
    }

    protected String getStringKey(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /*** Start------sp存储boolean类型------Start ***/
    protected void setBooleanKey(String key, boolean value) {
        sp.edit().putBoolean(key,value).apply();
        onPreferenceChange(key);
    }

    protected boolean getBooleanKey(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /*** Start------sp存储float类型------Start ***/
    protected void setFloatKey(String key, float value) {
        sp.edit().putFloat(key,value).apply();
        onPreferenceChange(key);
    }

    protected float getFloatKey(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /*** Start------sp存储List<>类型(AppInfo为例)------Start ***/
    protected void setListKey(String key, List<AppInfo> value){
        Gson gson = new Gson();
        String json = gson.toJson(value);

        sp.edit().putString(key,json).apply();
        onPreferenceChange(key);
    }

    protected List<AppInfo> getListKey(Context context, String key) {
        Gson gson = new Gson();
        String result = getListKey(key,"error");
        if (!result.equals("error")){
            return gson.fromJson(result,new TypeToken<List<AppInfo>>(){}.getType());
        } else {
            return new ArrayList<>();
        }
    }

    private String getListKey(String key, String defaultValue){
        return sp.getString(key, defaultValue);
    }

}