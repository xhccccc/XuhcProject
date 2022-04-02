package com.xuhc.mvvm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xuhc.mvvm.R;

import androidx.annotation.Nullable;

public class IndicatorView extends LinearLayout {

    private Context mContext;
    private LinearLayout llIndicatorView;
    private ImageView selectIndicatorRun;
    private boolean isFirst = true;
    private int distance;
    private int indicatorPointNumber;
    private int pageSelectPosition;
    private int pointRadius;
    private int pointFrameWidth;
    private int pointSelectBackground;
    private int pointBackground;

    public IndicatorView(Context context) {
        super(context);
        init(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        setFocusable(false);
        bindView(context);
        getAttributes(context, attrs);
        setListener();
    }

    private void bindView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.indicator_view, this, true);
        llIndicatorView = findViewById(R.id.ll_indicator_view);
        selectIndicatorRun = findViewById(R.id.indicator_run);
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        if (attributes != null) {
            int defaultSelectWidth = (int) getResources().getDimension((R.dimen.indicator_point_select_width));
            int defaultHeight = (int) getResources().getDimension((R.dimen.indicator_point_radius));
            int selectWidth = (int) attributes.getDimension(R.styleable.IndicatorView_select_width, defaultSelectWidth);
            int height = (int) attributes.getDimension(R.styleable.IndicatorView_select_height, defaultHeight);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(selectWidth, height);
            selectIndicatorRun.setLayoutParams(params);

            int defaultSelectBackground = R.drawable.indicator_select;
            pointSelectBackground = (int) attributes.getResourceId(R.styleable.IndicatorView_select_point_background, defaultSelectBackground);
            selectIndicatorRun.setBackgroundResource(pointSelectBackground);

            int defaultPointRadius = (int) getResources().getDimension((R.dimen.indicator_point_radius));
            pointRadius = (int) attributes.getDimension(R.styleable.IndicatorView_point_radius, defaultPointRadius);

            int defaultFrameWidth = (int) getResources().getDimension((R.dimen.indicator_point_select_width));
            pointFrameWidth = (int) attributes.getDimension(R.styleable.IndicatorView_point_frame_width, defaultFrameWidth);

            int defaultBackground = R.drawable.indicator_points;
            pointBackground = (int) attributes.getResourceId(R.styleable.IndicatorView_point_background, defaultBackground);

            attributes.recycle();
        }
    }

    private void setListener() {
        selectIndicatorRun.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                distance = getIndicatorLeftMargin();
                if (isFirst) {
                    initIndicatorPointMovePosition(pageSelectPosition);
                    isFirst = false;
                }
            }
        });
    }

    /**
     * 初始滑动块所在位置
     */
    public void initIndicatorPointMovePosition(int number) {
        float leftMargin = distance * number;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) selectIndicatorRun.getLayoutParams();
        params.leftMargin = Math.round(leftMargin);
        selectIndicatorRun.setLayoutParams(params);
    }

    /**
     * 设置indicator指示器数量
     */
    public void setIndicatorPointNumber(int indicatorPointNumber, int initSelect) {
        this.indicatorPointNumber = indicatorPointNumber;
        showIndicator();
        setIndicatorSelect(initSelect);
    }

    /**
     * indicator指示器的添加
     */
    private void showIndicator() {
        for (int i = 0; i < indicatorPointNumber; i++) {
            IndicatorPointView indicatorPointView = new IndicatorPointView(mContext);
            indicatorPointView.setPointRadius(pointRadius);
            indicatorPointView.setPointBackground(pointBackground);
            indicatorPointView.setFrameLayoutSize(pointFrameWidth, pointRadius);
            indicatorPointView.setClickable(true);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.indicator_point_margin_left);
            }
            llIndicatorView.addView(indicatorPointView, layoutParams);
        }
    }

    /**
     * viewpager滑动时point移动处理
     */
    public void indicatorPointMove(int distance, int position, float positionOffset) {
        float leftMargin = distance * (position + positionOffset);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) selectIndicatorRun.getLayoutParams();
        params.leftMargin = Math.round(leftMargin);
        selectIndicatorRun.setLayoutParams(params);

        setIndicatorAlpha(position, positionOffset);
    }

    /**
     * 设置Indicator选中的item
     */
    public void setIndicatorSelect(int select) {
        setPageSelectPosition(select);
        initialIndicator();
        llIndicatorView.getChildAt(select).setSelected(true);
    }

    /**
     * Indicator所有select状态置为false
     */
    public void initialIndicator() {
        int childCount = llIndicatorView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            llIndicatorView.getChildAt(i).setSelected(false);
        }
    }

    /**
     * viewpager滑动时,Indicator透明度控制
     */
    public void setIndicatorAlpha(int select, float alpha) {
        if (llIndicatorView.getChildAt(select) != null) {
            llIndicatorView.getChildAt(select).setAlpha(alpha);
        }
        if (llIndicatorView.getChildAt(select + 1) != null) {
            llIndicatorView.getChildAt(select + 1).setAlpha(1 - alpha);
        }
    }

    public int getIndicatorLeftMargin() {
        return llIndicatorView.getChildAt(1).getLeft() - llIndicatorView.getChildAt(0).getLeft();
    }

    /**
     * 设置当前选中位置
     */
    public void setPageSelectPosition(int pageSelectPosition) {
        this.pageSelectPosition = pageSelectPosition;
    }

    public int getDistance() {
        return distance;
    }
}
