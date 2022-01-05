package com.xuhc.utils.log;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * @Description: 调试信息工具文件
 * @author xhccc
 * @date 2022.01.05
 */
public class LogUtil {

    private static final String TAG = "LogUtil_";

    private static boolean isEnableLog(){
        return getBoolean("custom.custom" , false);
    }

    /**
     * @param tag , String msg
     * @return void
     */
    public static void i(String tag , String msg){
        if(isEnableLog()){
            Log.i(TAG + tag, msg);
        }
    }

    /**
     * @param tag , String msg
     * @return void
     */
    public static void d(String tag , String msg){
        if(isEnableLog()){
            Log.d(TAG + tag, msg);
        }
    }

    /**
     * @param tag , msg
     * @return void
     */
    public static void w(String tag , String msg){
        if(isEnableLog()){
            Log.w(TAG + tag, msg);
        }
    }

    /**
     * @param tag , String msg
     * @return void
     */
    public static void e(String tag , String msg){
        if(isEnableLog()){
            Log.e(TAG + tag, msg);
        }
    }

    /**
     * @param tag , String msg
     * @return void
     */
    public static void p(String tag , String msg){
        Log.i(TAG + tag, msg);
    }

    /**
     * @description 获取boolean类型属性
     * @param key,def
     * @return boolean
     */
    private static boolean getBoolean(final String key, final boolean def) {
        boolean value = false;
        try {
            Class c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("getBoolean", String.class, boolean.class);
            value = (boolean)(get.invoke(c, key, def));
        } catch (Exception e) {
            LogUtil.e(TAG, "getBoolean error: " + e.toString());
            e.printStackTrace();
        }
        return value;
    }
}
