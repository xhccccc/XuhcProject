package com.xuhc.floatingwindow;

import android.app.Instrumentation;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * @author As40046
 */
public class SideBar {

    private Context mContext;
    private ControlSmallBar mControlSmallBar;
    private ControlBar mControlBar;
    private WindowManager mWindowManager;
    private boolean isCanMove = true;

    public SideBar(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public SideBar(Context context, boolean isCanMove) {
        mContext = context;
        this.isCanMove = isCanMove;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public void addBar() {
        if (mControlSmallBar == null) {
            mControlSmallBar = new ControlSmallBar(mContext, isCanMove);
            mControlSmallBar.setOnClickListener(v -> unfoldBar());
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            mWindowManager.addView(mControlSmallBar, mControlSmallBar.getWindowLayoutParams());
        }
    }

    private void unfoldBar() {
        mControlSmallBar.setVisibility(View.GONE);
        if (mControlBar == null) {
            mControlBar = new ControlBar(mContext, isCanMove);
            mControlBar.setOnControlBarClickListener(new ControlBar.OnControlBarClickListener() {
                @Override
                public void backEvent() {
                    handleBackEvent();
                }

                @Override
                public void killEvent() {
                    //do what you want to do
                }

                @Override
                public void rightArrowEvent() {
                    mControlBar.setVisibility(View.GONE);
                    mControlSmallBar.setPosition(mControlBar.getDirection(), mControlBar.getWindowLayoutParams().y);
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

                @Override
                public void onTouch() {

                }

                @Override
                public void onTouchOutSide() {

                }
            });
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            mWindowManager.addView(mControlBar, mControlBar.getWindowLayoutParams());
            mControlBar.setPosition(mControlSmallBar.getDirection(), mControlSmallBar.getWindowLayoutParams().y);
        } else {
            mControlBar.setPosition(mControlSmallBar.getDirection(), mControlSmallBar.getWindowLayoutParams().y);
            mControlBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 处理返回键
     */
    private void handleBackEvent() {
        sendKeyEvent(KeyEvent.KEYCODE_BACK);
    }

    /**
     * 模拟按键发送
     * @param keyCode
     * @return void
     */
    public void sendKeyEvent(final int keyCode) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
