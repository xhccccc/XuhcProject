package com.xuhc.animationexample.clean;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.xuhc.animationexample.R;

/**
 * @author ArvinYoung
 * @date 2020/12/9
 * @description 定义中心大圆
 */
public class CenterCircle extends View {
    private int mRadius; //半径
    private int mProgressTextColor; //进度文字颜色
    private int mProgressTextSize; //进度文字大小
    private Paint mPaint;
    private int mWidth; //组件宽度
    private int mYOffset; //上文字下按钮的偏移量
    private int mProgress; // 当前进度
    private int mMaxYOffset; //偏移量的最大值

    public CenterCircle(Context context, int width) {
        this(context, null, width);
    }

    public CenterCircle(Context context, AttributeSet attrs, int width) {
        super(context, attrs);
        mWidth = width;
        init();
    }

    /**
    *TODO 设置动画
    *@return void
    */
    public void setAnimationPogress(float progress) {
        mYOffset = (int) (mMaxYOffset * (1 - progress));
        setScaleX((float) (1 - progress * 0.2));
        setScaleY((float) (1 - progress * 0.2));
        invalidate();
    }

    /**
     *TODO 设置进度值文字
     *@return void
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    /**
     *TODO 设置进度文字大小
     *@return void
     */
    public void setProgressTextSize(int progressTextSize) {
        mProgressTextSize = progressTextSize;
    }

    /**
     *TODO 设置进度文字颜色
     *@return void
     */
    public void setProgressTextColor(int progressTextColor) {
        mProgressTextColor = progressTextColor;
    }

    /**
     *TODO 初始化画笔等
     *@return void
     */
    private void init() {
        mRadius = mWidth / 2;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        setBackgroundResource(R.drawable.moving_center_dot_bg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String text = mProgress + "";
        Rect textBound = new Rect();
        canvas.save();
        canvas.translate(mWidth / 2, mWidth / 2);
        mPaint.setTextSize(mProgressTextSize);
        mPaint.setColor(mProgressTextColor);
        mPaint.getTextBounds(text, 0, text.length(), textBound);
        //绘制进度值内容
        canvas.drawText(text, -(textBound.right + textBound.left) / 2, -(textBound.top + textBound.bottom) / 2 - mYOffset, mPaint);

        //绘制%
        String percentText = "%";
        mPaint.setTextSize(mProgressTextSize/3);
        Rect percentBound = new Rect();
        mPaint.getTextBounds(percentText, 0, percentText.length(), percentBound);
        canvas.drawText(percentText, (textBound.right + textBound.left) / 2, -mYOffset, mPaint);

        canvas.restore();
    }
}
