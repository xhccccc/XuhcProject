package com.xuhc.mvvm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.xuhc.mvvm.R;

import androidx.annotation.Nullable;

public class IndicatorPointView extends FrameLayout {
    public IndicatorPointView(Context context) {
        super(context);
        init(context);
    }

    public IndicatorPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IndicatorPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setFocusable(false);
        bindView(context);
    }

    private void bindView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.indiacator_point_view, this, true);
    }

}
