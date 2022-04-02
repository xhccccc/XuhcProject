package com.xuhc.mvvm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xuhc.mvvm.R;

import androidx.annotation.Nullable;

public class IndicatorPointView extends FrameLayout {

    private FrameLayout flPoint;
    private ImageView ivPoint;

    public IndicatorPointView(Context context) {
        super(context);
        init(context, null);
    }

    public IndicatorPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setFocusable(false);
        bindView(context);
    }

    private void bindView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.indiacator_point_view, this, true);
        flPoint = findViewById(R.id.fl_point);
        ivPoint = findViewById(R.id.iv_point);
    }

    public void setPointBackground(int resId) {
        ivPoint.setBackgroundResource(resId);
    }

    public void setPointRadius(int radius) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(radius, radius);
        params.gravity = Gravity.CENTER;
        ivPoint.setLayoutParams(params);
    }

    public void setFrameLayoutSize(int frameWidth, int frameHeight) {
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(frameWidth, frameHeight);
        flPoint.setLayoutParams(frameParams);
    }

}
