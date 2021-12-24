package com.xuhc.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {

    public static final int REQUEST_PERMISSION_CODE = 100;

    public static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static boolean checkPermission(Activity activity, String[] permissions, int requestCode) {
        if (!hasPermissionGranted(activity, permissions)) {
            requestPermission(activity,permissions,requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 请求权限
     */
    private static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     * 检查权限组中的每个权限是否授权
     *
     * @param activity
     * @param permissions
     * @return
     */
    private static boolean hasPermissionGranted(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
