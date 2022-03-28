package com.xuhc.animationexample.clean;

import android.os.Bundle;

import com.xuhc.animationexample.R;

import androidx.appcompat.app.AppCompatActivity;

public class CleanActivity extends AppCompatActivity {

    private MovingCircleView mMovingCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);
        initMovingView();
    }

    /**
     * TODO 初始化清理动画控件
     *
     * @return void
     */
    private void initMovingView() {
        mMovingCircleView = (MovingCircleView) findViewById(R.id.movingView);
        mMovingCircleView.setProgress(100);
        mMovingCircleView.setToProgress(0);
        mMovingCircleView.setChangeListener(new OnAnimatorChangeListener() {
            @Override
            public void onProgressChanged(float progress) {
                if (progress == 0 && !mMovingCircleView.isCleaning()) {
                    mMovingCircleView.stopAnimation();
                    if (android.os.Build.VERSION.SDK_INT >= 26) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }
            }
        });

        //开始执行清理动画
        if (!mMovingCircleView.isCleaning()) {
            mMovingCircleView.startClean();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMovingCircleView != null && !mMovingCircleView.isCleaning()) {
            mMovingCircleView.stopAnimation();
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                finish();
            }
        }
        super.onDestroy();
    }
}