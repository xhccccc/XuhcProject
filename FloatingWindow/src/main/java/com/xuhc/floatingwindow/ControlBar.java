package com.xuhc.floatingwindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class ControlBar extends BaseControlBar implements BaseControlBar.OnSideBarTouchListener {

    private ImageView backBtn,killBtn,arrowBtn,magnifyBtn,quitBtn;

    private OnControlBarClickListener mOnControlBarClickListener;

    private boolean isCanMove = true;

    public void setOnControlBarClickListener(OnControlBarClickListener onControlBarClickListener) {
        mOnControlBarClickListener = onControlBarClickListener;
    }

    @Override
    public void onTouch() {
        if (mOnControlBarClickListener != null) {
            mOnControlBarClickListener.onTouch();
        }
    }

    @Override
    public void onTouchOutSide() {
        if (mOnControlBarClickListener != null) {
            mOnControlBarClickListener.onTouchOutSide();
        }
    }

    public interface OnControlBarClickListener{
        void backEvent();
        void killEvent();
        void rightArrowEvent();
        void magnifyEvent();
        void quitEvent();

        /**
         * 点击触摸了
         */
        void onTouch();

        /**
         * 点击触摸了窗口外部
         */
        void onTouchOutSide();
    }

    public ControlBar(Context context) {
        super(context);
    }

    public ControlBar(Context context, boolean isCanMove) {
        super(context);
        this.isCanMove = isCanMove;
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_control_bar;
    }

    @Override
    public void init() {
        getWindowLayoutParams().width = (int) getResources().getDimension(R.dimen.control_bar_bg_width);
        getWindowLayoutParams().height = (int) getResources().getDimension(R.dimen.control_bar_bg_height);
        addListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addListener() {
        backBtn = findViewById(R.id.control_bar_back);
        killBtn = findViewById(R.id.control_bar_kill);
        arrowBtn = findViewById(R.id.control_bar_arrow);
        magnifyBtn = findViewById(R.id.control_bar_magnify);
        quitBtn = findViewById(R.id.control_bar_quit);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnControlBarClickListener != null){
                    mOnControlBarClickListener.backEvent();
                }
            }
        });
        killBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnControlBarClickListener != null){
                    mOnControlBarClickListener.killEvent();
                }
            }
        });
        arrowBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnControlBarClickListener != null){
                    mOnControlBarClickListener.rightArrowEvent();
                }
            }
        });
        magnifyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnControlBarClickListener != null){
                    magnifyBtn.requestFocus();
                    mOnControlBarClickListener.magnifyEvent();
                }
            }
        });
        quitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnControlBarClickListener != null){
                    mOnControlBarClickListener.quitEvent();
                }
            }
        });

        if (isCanMove) {
            backBtn.setOnTouchListener(new FloatingOnTouchListener(this));
            killBtn.setOnTouchListener(new FloatingOnTouchListener(this));
            arrowBtn.setOnTouchListener(new FloatingOnTouchListener(this));
            magnifyBtn.setOnTouchListener(new FloatingOnTouchListener(this));
            quitBtn.setOnTouchListener(new FloatingOnTouchListener(this));
            setOnTouchListener(new FloatingOnTouchListener(this));
        }
    }

    @Override
    public void changeEdge(int direction) {
        if (direction == ORIENTATION_LEFT){
            arrowBtn.setBackgroundResource(R.drawable.control_bar_left_arrow_bg);
        } else {
            arrowBtn.setBackgroundResource(R.drawable.control_bar_right_arrow_bg);
        }
    }

    public void updateButtonStatus(boolean clickable) {
        killBtn.setClickable(clickable);
        killBtn.setAlpha(clickable ? 1 : 0.5f);
        magnifyBtn.setClickable(clickable);
        magnifyBtn.setAlpha(clickable ? 1 : 0.5f);
        quitBtn.setClickable(clickable);
        quitBtn.setAlpha(clickable ? 1 : 0.5f);
    }
}
