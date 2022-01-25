package com.xuhc.mediaplayer.videoview;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.xuhc.mediaplayer.R;

public class VideoViewActivity extends AppCompatActivity {

    private VideoView mVideoLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        mVideoLocal = (VideoView) findViewById(R.id.video_local);
        initLocalVideo();
    }

    //播放本地视频
    private void initLocalVideo() {
        //设置有进度条可以拖动快进
        MediaController localMediaController = new MediaController(this);
        mVideoLocal.setMediaController(localMediaController);
        String uri = ("android.resource://" + getPackageName() + "/" + R.raw.dy2);
        mVideoLocal.setVideoURI(Uri.parse(uri));
        mVideoLocal.start();
    }
}