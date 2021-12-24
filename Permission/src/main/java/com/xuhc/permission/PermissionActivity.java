package com.xuhc.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + PermissionActivity.class.getSimpleName();

    private TextView tvPermissionTip;
    public static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        tvPermissionTip = findViewById(R.id.tv_permission_tip);
        boolean hasPermission = PermissionUtils.checkPermission(this, STORAGE_PERMISSIONS,PermissionUtils.REQUEST_PERMISSION_CODE);
        if (hasPermission){
            //TODO
            tvPermissionTip.setText("已授予权限");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_PERMISSION_CODE) {
            Log.d(TAG, "onRequestPermissionsResult:");
            boolean isCanRun = true;
            if (grantResults.length == PermissionUtils.STORAGE_PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        isCanRun = false;
                        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }
                }
            }
            if (isCanRun){
                //TODO
                tvPermissionTip.setText("已授予权限——onRequestPermissionsResult");
            }

        }
    }
}