package com.xuhc.mvvm.view;

import android.content.Context;
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
    private ImageView indicatorRun;
    private boolean isFirst = true;
    private int distance;
    private int indicatorPointNumber;
    private int pageSelectPosition;

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
        setListener();
    }

    private void bindView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.linearlayout_indicator_view, this, true);
        llIndicatorView = findViewById(R.id.ll_indicator_view);
        indicatorRun = findViewById(R.id.indicator_run);
    }

    private void setListener(){
        indicatorRun.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                distance =  getIndicatorLeftMargin();
                if (isFirst){
                    initIndicatorPointMovePosition(pageSelectPosition);
                    isFirst = false;
                }
            }
        });
    }

    public void initIndicatorPointMovePosition(int number){
        float leftMargin = distance * number;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) indicatorRun.getLayoutParams();
        params.leftMargin = Math.round(leftMargin);
        indicatorRun.setLayoutParams(params);
    }

    public void setIndicatorPointNumber(int indicatorPointNumber) {
        this.indicatorPointNumber = indicatorPointNumber;
        showIndicator();
    }

    private void showIndicator(){
        for (int i = 0; i<indicatorPointNumber; i++){
            IndicatorPointView indicatorPointView = new IndicatorPointView(mContext);
            indicatorPointView.setClickable(true);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0){
                layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.indicator_point_margin_left);
            }
            llIndicatorView.addView(indicatorPointView,layoutParams);
        }
    }

    public void indicatorPointMove(int distance,int position, float positionOffset){
        float leftMargin = distance * (position + positionOffset);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) indicatorRun.getLayoutParams();
        params.leftMargin = Math.round(leftMargin);
        indicatorRun.setLayoutParams(params);

        setIndicatorAlpha(position,positionOffset);
    }

    public void setIndicatorSelectNotInit(int select){
        llIndicatorView.getChildAt(select).setSelected(true);
    }

    public void setIndicatorSelect(int select){
        initialIndicator();
        llIndicatorView.getChildAt(select).setSelected(true);
    }

    public void initialIndicator(){
        int childCount = llIndicatorView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            llIndicatorView.getChildAt(i).setSelected(false);
        }
    }

    public void setIndicatorAlpha(int select,float alpha){
        if (llIndicatorView.getChildAt(select) != null){
            llIndicatorView.getChildAt(select).setAlpha(alpha);
        }
        if (llIndicatorView.getChildAt(select + 1) != null){
            llIndicatorView.getChildAt(select + 1).setAlpha(1 - alpha);
        }
    }

    public int getIndicatorLeftMargin(){
        return llIndicatorView.getChildAt(1).getLeft() - llIndicatorView.getChildAt(0).getLeft();
    }

    public void setPageSelectPosition(int pageSelectPosition) {
        this.pageSelectPosition = pageSelectPosition;
    }

    public int getPageSelectPosition() {
        return pageSelectPosition;
    }

    public int getDistance() {
        return distance;
    }
}
