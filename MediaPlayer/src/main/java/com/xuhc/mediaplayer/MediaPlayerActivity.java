package com.xuhc.mediaplayer;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xuhc.mediaplayer.surface.SurfaceViewActivity;
import com.xuhc.mediaplayer.texture.TextureViewActivity;
import com.xuhc.mediaplayer.videoview.VideoViewActivity;
import com.xuhc.mediastore.MediaStoreTestActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ActivityResultLauncher<String[]> requestPermissionActivityResultLauncher_multiple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        initActivityResult();
        requestPermissionActivityResultLauncher_multiple.launch(STORAGE_PERMISSIONS);

        findViewById(R.id.bt_texture_view).setOnClickListener(this);
        findViewById(R.id.bt_surface_view).setOnClickListener(this);
        findViewById(R.id.bt_video_view).setOnClickListener(this);
        findViewById(R.id.bt_media_store).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_texture_view){
            Intent intent = new Intent(this,TextureViewActivity.class);
            intent.putExtra("filePath", getAssetsCacheFile(this,"dy1.mp4"));
            startActivity(intent);
        } else if (id == R.id.bt_surface_view){
            Intent intent = new Intent(this, SurfaceViewActivity.class);
//            intent.putExtra("filePath", getAssetsCacheFile(this,"4ktest.ts"));
            intent.putExtra("filePath", "/storage/emulated/0/2885/samba/ts/5.ts");
            startActivity(intent);
        } else if (id == R.id.bt_video_view){
            Intent intent = new Intent(this, VideoViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.bt_media_store){
            Intent intent = new Intent(this, MediaStoreTestActivity.class);
            startActivity(intent);
        }
    }

    public String getAssetsCacheFile(Context context, String fileName){
        File cacheFile = new File(context.getCacheDir(), fileName);
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheFile.getAbsolutePath();
    }

    public void initActivityResult(){


        //多个申请回调
        ActivityResultContracts.RequestMultiplePermissions requestMultiplePermission  = new ActivityResultContracts.RequestMultiplePermissions();
        requestPermissionActivityResultLauncher_multiple = registerForActivityResult(requestMultiplePermission, new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Log.d("xhccc", "Multiple_ActivityResultContracts_onActivityResult: " + result.toString());
//                tvPermissionTip.setText(result.toString());
            }
        });
    }
}