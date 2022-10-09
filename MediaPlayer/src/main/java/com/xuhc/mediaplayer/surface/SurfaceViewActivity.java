package com.xuhc.mediaplayer.surface;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xuhc.mediaplayer.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class SurfaceViewActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener
    ,MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,MediaPlayer.OnBufferingUpdateListener
    ,MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener
    ,View.OnClickListener{

    private static final String TAG = "xhccc" + SurfaceViewActivity.class.getSimpleName();

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private String filePath;

    private ProgressBar progressBar;
    private SeekBar seekBar;
    private TextView videoTimeTextView;
    private String videoTimeString;

    private Button playButton;
    private boolean seekBarAutoFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        filePath = getIntent() != null ? getIntent().getStringExtra("filePath") : null;

        Log.d(TAG,"filePath: " + filePath);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        // 设置surfaceHolder
        surfaceHolder = surfaceView.getHolder();
        // 设置Holder类型,该类型表示surfaceView自己不管理缓存区,虽然提示过时，但最好还是要设置
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 设置surface回调
        surfaceHolder.addCallback(new SurfaceCallback());

        playButton = findViewById(R.id.button_play);
        progressBar = findViewById(R.id.progressBar);
        seekBar = findViewById(R.id.seekbar);
        videoTimeTextView = findViewById(R.id.textView_showTime);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_play){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }


    // SurfaceView的callBack
    private class SurfaceCallback implements SurfaceHolder.Callback {
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // SurfaceView的大小改变
        }

        public void surfaceCreated(SurfaceHolder holder) {
            // surfaceView被创建
            // 设置播放资源
            playVideo();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // surfaceView销毁
            // 如果MediaPlayer没被销毁，则销毁mediaPlayer
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }

    /**
     * 播放视频
     */
    public void playVideo() {
        // 初始化MediaPlayer
        mediaPlayer = new MediaPlayer();
        // 重置mediaPlay,建议在初始滑mediaPlay立即调用。
        mediaPlayer.reset();
        // 设置声音效果
        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setAudioAttributes(new AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());

        // 设置显示到屏幕
        mediaPlayer.setDisplay(surfaceHolder);
        // 设置surfaceView保持在屏幕上
        mediaPlayer.setScreenOnWhilePlaying(true);
        surfaceHolder.setKeepScreenOn(true);

        // 设置播放完成监听
        mediaPlayer.setOnCompletionListener(this);
        // 设置媒体加载完成以后回调函数。
        mediaPlayer.setOnPreparedListener(this);
        // 错误监听回调函数
        mediaPlayer.setOnErrorListener(this);
        // 设置缓存变化监听
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnVideoSizeChangedListener(this);
        try {
            mediaPlayer.setDataSource(filePath);
            // 设置异步加载视频，包括两种方式 prepare()同步，prepareAsync()异步
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion");
    }

    /**
     * 视频加载完毕监听
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "onPrepared");
        // 当视频加载完毕以后，隐藏加载进度条
        progressBar.setVisibility(View.GONE);
//        // 判断是否有保存的播放位置,防止屏幕旋转时，界面被重新构建，播放位置丢失。
//        if (Constants.playPosition >= 0) {
//            mediaPlayer.seekTo(Constants.playPosition);
//            Constants.playPosition = -1;
//            // surfaceHolder.unlockCanvasAndPost(Constants.getCanvas());
//        }
        // 播放视频
        mediaPlayer.start();
        // 设置控制条,放在加载完成以后设置，防止获取getDuration()错误
        seekBar.setProgress(0);
        seekBar.setMax(mediaPlayer.getDuration());
        // 设置播放时间
        videoTimeString = getShowTime(mediaPlayer.getDuration());
        videoTimeTextView.setText("00:00:00/" + videoTimeString);
        // 设置拖动监听事件
        seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
        // 设置按钮监听事件
//        // 重新播放
//        replayButton.setOnClickListener(this);
        // 暂停和播放
        playButton.setOnClickListener(this);
        seekBarAutoFlag = true;
        // 开启线程 刷新进度条
        thread.start();
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onInfo播放信息what=" + what);
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(TAG, "onBufferingUpdate播放缓存=" + percent + "%");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onError播放异常what: " + what);
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.d(TAG, "onVideoSizeChanged");
        if (mediaPlayer.getVideoWidth() != surfaceView.getWidth() ||
                mediaPlayer.getVideoHeight() != surfaceView.getHeight()) {
            autoFitVideoSize(surfaceView.getWidth(),surfaceView.getHeight());
        }
    }

    /**
     * 滑动条变化线程
     */
    private Thread thread = new Thread() {

        public void run() {
            // TODO Auto-generated method stub
            super.run();
            // 增加对异常的捕获，防止在判断mediaPlayer.isPlaying的时候，报IllegalStateException异常
            try {
                while (seekBarAutoFlag) {
                    /*
                     * mediaPlayer不为空且处于正在播放状态时，使进度条滚动。
                     * 通过指定类名的方式判断mediaPlayer防止状态发生不一致
                     */
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

    };

    /**
     * seekBar拖动监听类
     *
     * @author shenxiaolei
     */
    @SuppressWarnings("unused")
    private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // TODO Auto-generated method stub
            if (progress >= 0) {
                // 如果是用户手动拖动控件，则设置视频跳转。
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                // 设置当前播放时间
                videoTimeTextView.setText(getShowTime(progress) + "/" + videoTimeString);
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

    }


    /**
     * 转换播放时间
     *
     * @param milliseconds 传入毫秒值
     * @return 返回 hh:mm:ss或mm:ss格式的数据
     */
    @SuppressLint("SimpleDateFormat")
    public String getShowTime(long milliseconds) {
        // 获取日历函数
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat dateFormat = null;
        // 判断是否大于60分钟，如果大于就显示小时。设置日期格式
        if (milliseconds / 60000 > 60) {
            dateFormat = new SimpleDateFormat("hh:mm:ss");
        } else {
            dateFormat = new SimpleDateFormat("mm:ss");
        }
        return dateFormat.format(calendar.getTime());
    }

    public void autoFitVideoSize(float surfaceWidth, float surfaceHeight) {
        int videoWidth = mediaPlayer.getVideoWidth();
        int videoHeight = mediaPlayer.getVideoHeight();

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
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();
        params.width = videoWidth;
        params.height = videoHeight;
//        surfaceView.setScaleX((float) 1.1);//不会显得太狭小
//        surfaceView.setScaleY((float) 1.1);
        surfaceView.setLayoutParams(params);
    }

}