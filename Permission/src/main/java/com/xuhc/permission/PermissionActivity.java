package com.xuhc.permission;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + PermissionActivity.class.getSimpleName();

    private TextView tvPermissionTip;
    public static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    ActivityResultLauncher<String> requestPermissionActivityResultLauncher;
    ActivityResultLauncher<String[]> requestPermissionActivityResultLauncher_multiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        tvPermissionTip = findViewById(R.id.tv_permission_tip);

        //新方法
        initActivityResult();
        Button newRequestPermission = findViewById(R.id.bt_request_permission_new);
        newRequestPermission.setOnClickListener(v -> {

            //单个申请
//            for (String needPermission : STORAGE_PERMISSIONS){
//                requestPermissionActivityResultLauncher.launch(needPermission);
//            }

            //多个申请
            requestPermissionActivityResultLauncher_multiple.launch(STORAGE_PERMISSIONS);
        });

        //旧方法
        Button oldRequestPermission = findViewById(R.id.bt_request_permission_old);
        oldRequestPermission.setOnClickListener(v -> {
            boolean hasPermission = PermissionUtils.checkPermission(this, STORAGE_PERMISSIONS,PermissionUtils.REQUEST_PERMISSION_CODE);
            if (hasPermission){
                tvPermissionTip.setText("已授予权限");
            }
        });

    }

    public void initActivityResult(){
        //单个申请回调
        ActivityResultContracts.RequestPermission requestPermission  = new ActivityResultContracts.RequestPermission();
        requestPermissionActivityResultLauncher = registerForActivityResult(requestPermission, new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                Log.d(TAG, "One_ActivityResultContracts_onActivityResult: " + result);
                tvPermissionTip.setText(result.toString());
            }
        });

        //多个申请回调
        ActivityResultContracts.RequestMultiplePermissions requestMultiplePermission  = new ActivityResultContracts.RequestMultiplePermissions();
        requestPermissionActivityResultLauncher_multiple = registerForActivityResult(requestMultiplePermission, new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Log.d(TAG, "Multiple_ActivityResultContracts_onActivityResult: " + result.toString());
                tvPermissionTip.setText(result.toString());
            }
        });
    }


    //被弃用
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
                tvPermissionTip.setText("已授予权限——onRequestPermissionsResult");
            }
        }
    }
}