package com.xuhc.mediastore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xuhc.mediaplayer.R;
import com.xuhc.mediastore.bean.MusicBean;
import com.xuhc.permission.PermissionActivity;
import com.xuhc.permission.PermissionUtils;

import java.util.List;

public class MediaStoreTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "xhccc" + MediaStoreTestActivity.class.getSimpleName();

    private FileManager fileManager;

    private List<MusicBean> allMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_store_test);

        fileManager = FileManager.getInstance(getApplicationContext());

        boolean hasPermission = PermissionUtils.checkPermission(this, PermissionUtils.STORAGE_PERMISSIONS,PermissionUtils.REQUEST_PERMISSION_CODE);
        if (hasPermission){
            //TODO
            Log.d(TAG,"已授予权限");
        }

        findViewById(R.id.bt_get_music).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_get_music){
            allMusicList = fileManager.getMusics();

            Log.d(TAG,"allMusicList.size: " + allMusicList.size());
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
                Log.d(TAG,"已授予权限——onRequestPermissionsResult");
            }

        }
    }
}