package com.xuhc.customview.seekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.xuhc.customview.R;

import java.util.LinkedList;
import java.util.Queue;

public class SeekSettingView extends RelativeLayout {

//    private TextView mTitle;
//    private TextView mValue;
    private ImageView leftImg;
    private SeekBar mSeekBar;

    private int mMinValue = 0;
    private int mMaxValue = 100;
    private int mStep = 1;
    private boolean mEnable = true;
    private boolean mSeekBarVisibility = false;
    private Queue<Integer> mKeyQueue = new LinkedList<Integer>();
    private OnSeekListener mListener;

    public SeekSettingView(Context context) {
        super(context);
    }

    public SeekSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SeekSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        bindView(context);
        setClickable(true);
        setFocusable(true);
//        setFocusableInTouchMode(true);
        setClipChildren(false);
        getAttributes(context, attrs);
        addListener();
    }

    private void bindView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_setting_seek, this, true);
//        mTitle = (TextView) findViewById(R.id.seek_setting_view_title);
//        mValue = (TextView) findViewById(R.id.seek_setting_view_value);
        leftImg = (ImageView) findViewById(R.id.seek_setting_view_left_img);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SeekSettingView);
        if (attributes != null) {

            String title = attributes.getString(R.styleable.SeekSettingView_title);
            if (!TextUtils.isEmpty(title)) {
//                mTitle.setText(title);
            }

            int left = attributes.getResourceId(R.styleable.SeekSettingView_left,0);
            leftImg.setBackgroundResource(left);

            mMaxValue = attributes.getInteger(R.styleable.SeekSettingView_max_value, 100);
            mSeekBar.setMax(mMaxValue);

            mMinValue = attributes.getInteger(R.styleable.SeekSettingView_min_value, 0);
            mSeekBar.setMin(mMinValue);

            mStep = attributes.getInteger(R.styleable.SeekSettingView_step, 1);

            String value = attributes.getString(R.styleable.SeekSettingView_value);
            if (!TextUtils.isEmpty(value)) {
//                mValue.setText(String.valueOf(value));
                mSeekBar.setProgress(Integer.parseInt(value));
            }

//            setBackgroundResource(R.drawable.setting_view_bg_normal);

            attributes.recycle();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addListener() {

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            if (mEnable) {
                                increaseValue();
                                requestFocus();
                                if (mListener != null) {
                                    mListener.onSeek(v, mSeekBar.getProgress());
                                }
                            }
                            return true;

                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            if (mEnable) {
                                decreaseValue();
                                requestFocus();
                                if (mListener != null) {
                                    mListener.onSeek(v, mSeekBar.getProgress());
                                }
                            }
                            return true;
                    }
                }
                return false;
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
//                    mValue.setText(String.valueOf(progress));
                    if (mListener != null) {
                        mListener.onSeek(seekBar, progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
//                mValue.setText(String.valueOf(progress));
                if (mListener != null) {
                    mListener.onSeek(seekBar, progress);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rect seekRect = new Rect();
                mSeekBar.getHitRect(seekRect);

                if ((event.getY() >= (seekRect.top - 500)) && (event.getY() <= (seekRect.bottom + 500))) {
                    float y = seekRect.top + seekRect.height() / 2;
                    //seekBar only accept relative x
                    float x = event.getX() - seekRect.left;
                    if (x < 0) {
                        x = 0;
                    } else if (x > seekRect.width()) {
                        x = seekRect.width();
                    }
                    MotionEvent me = MotionEvent.obtain(event.getDownTime(), event.getEventTime(),
                            event.getAction(), x, y, event.getMetaState());
                    return mSeekBar.onTouchEvent(me);
                }
                return false;
            }
        });

//        setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                setBackgroundResource(hasFocus ? R.drawable.seek_select : 0);
//            }
//        });
    }

    public void setTitle(String title) {
//        mTitle.setText(title);
    }

    public void setValue(int value) {
        if (value >= mMinValue && value <= mMaxValue) {
//            mValue.setText(String.valueOf(value));
            mSeekBar.setProgress(value);
        }
    }

    public void setValueText(String text) {
//        mValue.setText(text);
    }

    public int getValue() {
        return mSeekBar.getProgress();
    }

    private void increaseValue() {
        int value = mSeekBar.getProgress();
        value = value + mStep;
        if (value < mMaxValue)
            setValue(value);
        else
            setValue(mMaxValue);
    }

    private void decreaseValue() {
        int value = mSeekBar.getProgress();
        value = value - mStep;
        if (value > mMinValue)
            setValue(value);
        else
            setValue(mMinValue);
    }

    public void setOnSeekListener(OnSeekListener listener) {
        mListener = listener;
    }

    public interface OnSeekListener {
        void onSeek(View view, int value);
    }

    public void setSeekEnable(boolean enable){
        setClickable(enable);
        setFocusable(enable);
        setEnabled(enable);
        mSeekBar.setClickable(enable);
        mSeekBar.setFocusable(enable);
        mSeekBar.setEnabled(enable);
    }
}