package com.xuhc.floatingwindow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FloatWindowActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addBtn;
    private ControlSmallBar mControlSmallBar;
    private ControlBar mControlBar;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);

        initView();
        addListener();
    }

    private void initView() {
        addBtn = findViewById(R.id.bt_add);
    }

    private void addListener() {
        addBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_add) {
            addControlBar();
        }
    }

    private void addControlBar(){
        if (mControlSmallBar == null){
            mControlSmallBar = new ControlSmallBar(this);
            mControlSmallBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unfoldControlBar();
                }
            });
            mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            mWindowManager.addView(mControlSmallBar, mControlSmallBar.getWindowLayoutParams());
        }
    }

    private void unfoldControlBar(){
        mControlSmallBar.setVisibility(View.GONE);
        if (mControlBar == null){
            mControlBar = new ControlBar(this);
            mControlBar.setOnControlBarClickListener(new ControlBar.OnControlBarClickListener() {
                @Override
                public void backEvent() {
                    //do what you want to do
                }

                @Override
                public void killEvent() {
                    //do what you want to do
                }

                @Override
                public void rightArrowEvent() {
                    mControlBar.setVisibility(View.GONE);
                    mControlSmallBar.setPosition(mControlBar.getDirection(),mControlBar.getWindowLayoutParams().y);
                    mControlSmallBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void magnifyEvent() {
                    //do what you want to do
                }

                @Override
                public void quitEvent() {
                    //do what you want to do
                }
            });
            mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            mWindowManager.addView(mControlBar, mControlBar.getWindowLayoutParams());
            mControlBar.setPosition(mControlSmallBar.getDirection(),mControlSmallBar.getWindowLayoutParams().y);
        } else {
            mControlBar.setPosition(mControlSmallBar.getDirection(),mControlSmallBar.getWindowLayoutParams().y);
            mControlBar.setVisibility(View.VISIBLE);
        }
    }
}