package com.xuhc.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xuhc.mediaplayer.surface.SurfaceViewActivity;
import com.xuhc.mediaplayer.texture.TextureViewActivity;
import com.xuhc.mediaplayer.videoview.VideoViewActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        findViewById(R.id.bt_texture_view).setOnClickListener(this);
        findViewById(R.id.bt_surface_view).setOnClickListener(this);
        findViewById(R.id.bt_video_view).setOnClickListener(this);
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
            intent.putExtra("filePath", getAssetsCacheFile(this,"dy1.mp4"));
            startActivity(intent);
        } else if (id == R.id.bt_video_view){
            Intent intent = new Intent(this, VideoViewActivity.class);
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
}