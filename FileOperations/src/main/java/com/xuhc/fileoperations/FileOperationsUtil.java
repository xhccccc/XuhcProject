package com.xuhc.fileoperations;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileOperationsUtil {

    private static final String TAG = FileOperationsUtil.class.getSimpleName();
    private FileOperationsUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getCacheDirPath(Context context){
        return context.getCacheDir().getAbsolutePath();
    }

    public static String getFilesDirPath(Context context){
        return context.getFilesDir().getAbsolutePath();
    }

    public static String getExternalCacheDirPath(Context context){
        return context.getExternalCacheDir().getAbsolutePath();
    }

    //Deprecated
    public static String getExternalStorageDirectoryPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    //Deprecated
    public static String getExternalStoragePublicDirectoryPath(String type){
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }

    public static String getExternalFilesDirPath(Context context,String type){
        return context.getExternalFilesDir(type).getAbsolutePath();
    }

    /**
     * 检查是否已挂载SD卡镜像（是否存在SD卡）
     *
     * @return
     */
    public static boolean isMountedSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            Log.w(TAG, "SDCARD is not MOUNTED !");
            return false;
        }
    }
}
