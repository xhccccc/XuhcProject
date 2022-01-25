package com.xuhc.mediaplayer.texture;

import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.RelativeLayout;

import com.xuhc.mediaplayer.R;

import androidx.appcompat.app.AppCompatActivity;

public class TextureViewActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + TextureViewActivity.class.getSimpleName();

    private TextureView mTextureView;
    private Surface mSurface;
    private String filePath;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_view);

        filePath = getIntent() != null ? getIntent().getStringExtra("filePath") : null;

        initTextureView();
    }

    @Override
    protected void onPause() {
        //先判断是否正在播放
        if (mMediaPlayer.isPlaying()) {
            //如果正在播放我们就先保存这个播放位置
            mMediaPlayer.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
        super.onDestroy();
    }

    private void initTextureView(){
        mTextureView = findViewById(R.id.texture_view);

        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                playVideo(surface);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    private void playVideo(SurfaceTexture surfaceTexture) {
        if (filePath != null){
            try {
                // Create a new media player and set the listeners
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(filePath);
                if (mSurface == null) {
                    mSurface = new Surface(surfaceTexture);
                }
                mMediaPlayer.setSurface(mSurface);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepareAsync();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.d(TAG, "onCompletion播放完成");
                        onBackPressed();
                    }
                });
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Log.d(TAG, "onPrepared播放准备完成");
                        mMediaPlayer.start();
                    }
                });
                mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.d(TAG, "onError播放异常");
                        return false;
                    }
                });
                mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        Log.d(TAG, "onBufferingUpdate播放缓存=" + percent + "%");
                    }
                });
                mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        Log.d(TAG, "onInfo播放信息what=" + what);
                        return false;
                    }
                });
                mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        Log.d(TAG, "onVideoSizeChanged");
                        if (mMediaPlayer.getVideoWidth() != mTextureView.getWidth()
                                || mMediaPlayer.getVideoHeight() != mTextureView.getHeight()) {
                            autoFitVideoSize(mTextureView.getWidth(),mTextureView.getHeight());
                        }
                    }
                });
                setVolumeControlStream(AudioManager.STREAM_MUSIC);

            } catch (Exception e) {
                Log.e(TAG, "error: " + e.getMessage(), e);
            }
        } else {
            Log.d(TAG, "视频不存在");
        }
    }

    public void autoFitVideoSize(float surfaceWidth, float surfaceHeight) {
        int videoWidth = mMediaPlayer.getVideoWidth();
        int videoHeight = mMediaPlayer.getVideoHeight();

        //根据视频尺寸去计算->视频可以在surfaceView中放大的最大倍数。
        float max;
        if (getResources().getConfiguration().orientation== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            //竖屏模式下按视频宽度计算放大倍数值
            max = Math.max((float) videoWidth / (float) surfaceWidth,(float) videoHeight / (float) surfaceHeight);
        } else {
            //横屏模式下按视频高度计算放大倍数值
            max = Math.max(((float) videoHeight/(float) surfaceHeight),(float) videoWidth/(float) surfaceWidth);
        }

        //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
        videoWidth = (int) Math.ceil((float) videoWidth / max);
        videoHeight = (int) Math.ceil((float) videoHeight / max);

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到 surfaceView 让视频自动填充。
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTextureView.getLayoutParams();
        params.width = videoWidth;
        params.height = videoHeight;
//        surfaceView.setScaleX((float) 1.1);//不会显得太狭小
//        surfaceView.setScaleY((float) 1.1);
        mTextureView.setLayoutParams(params);
    }
}