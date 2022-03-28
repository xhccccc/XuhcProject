package com.xuhc.animationexample.clean;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.xuhc.animationexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ArvinYoung
 * @date 2020/12/9
 * @description 自定义清理动画控件
 */
public class MovingCircleView extends ViewGroup {
    private List<SmallCircleDot> mSmallDots; //存放小点的List
    private int mSmallDotsCount; //小点总数
    private int mCenterDotRadius; //中心大点的半径
    private Drawable mCenterDotRes; //中心大点的资源文件
    private int mSmallDotColor; //小点的颜色
    private Paint mPaint;
    private int mMaxSmallDotRadius; //最大小点半径
    private int mMinSmallDotRadius; //最小小点半径
    private int mMaxSmallDotSpeed; //最大小点速度
    private int mMinSmallDotSpeed; //最小小点速度
    private int mWidth;
    private int mProgress; //当前进度
    private int mProgressTextSize; //进度文字大小
    private int mProgressTextColor; //进度文字颜色
    private long mAnimatorDuration; //动画持续的时间
    private CenterCircle mCenterDot; //中心点组件
    private OnAnimatorChangeListener mAnimChangeListener; //动画改变监听器
    
    private ValueAnimator valueAnimator;//动画对象

    public MovingCircleView(Context context) {
        this(context, null);
    }

    public MovingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(true);
        initObtainStyled(context, attrs);
        mCenterDot = new CenterCircle(getContext(), mCenterDotRadius * 2);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        getChildAt(0).layout(getWidth() / 2 - mCenterDotRadius, getWidth() / 2 - mCenterDotRadius, getWidth() / 2 + mCenterDotRadius, getWidth() / 2 + mCenterDotRadius);
    }

    private void initObtainStyled(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MovingCircleView);
        mSmallDotsCount = array.getInteger(R.styleable.MovingCircleView_mcv_dot_count, 20);
        mCenterDotRadius = (int) array.getDimension(R.styleable.MovingCircleView_mcv_center_dot_radius, 0);
        mCenterDotRes = array.getDrawable(R.styleable.MovingCircleView_mcv_center_dot_back);
        mSmallDotColor = array.getColor(R.styleable.MovingCircleView_mcv_dot_color, getResources().getColor(R.color.color_small_dot));
        mMaxSmallDotRadius = (int) array.getDimension(R.styleable.MovingCircleView_mcv_dot_max_radius, SizeUtil.Dp2Px(getContext(), 20));
        mMinSmallDotRadius = (int) array.getDimension(R.styleable.MovingCircleView_mcv_dot_min_radius, SizeUtil.Dp2Px(getContext(), 5));
        mMaxSmallDotSpeed = array.getInteger(R.styleable.MovingCircleView_mcv_dot_max_speed, 10);
        mMinSmallDotSpeed = array.getInteger(R.styleable.MovingCircleView_mcv_dot_min_speed, 3);
        mProgressTextSize = (int) array.getDimension(R.styleable.MovingCircleView_mcv_text_size, SizeUtil.Sp2Px(getContext(), 70));
        mAnimatorDuration = array.getInteger(R.styleable.MovingCircleView_mcv_animator_duration, 5000);
        mProgressTextColor = array.getColor(R.styleable.MovingCircleView_mcv_text_color, getResources().getColor(R.color.color_progress_text));
        array.recycle();

        mSmallDots = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mProgress = 50;
        setBackgroundColor(Color.TRANSPARENT);//设置整体背景
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //假设情况是用户至少会给宽或者高指定一个确定的值,否则抛出异常
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            try {
                throw new SizeNotDeterminedException("not allow wrap_content");
            } catch (SizeNotDeterminedException e) {
                e.printStackTrace();
            }
        }

        mWidth = Math.min(widthSize, heightSize);
        setMeasuredDimension(mWidth, mWidth);
        SmallCircleDot.WIDTH = mWidth;
        SmallCircleDot.SPEED = mMinSmallDotSpeed;
        SmallCircleDot.sMaxDotRadius = mMaxSmallDotRadius;
        SmallCircleDot.sMinDotRadius = mMinSmallDotRadius;
        
    }

    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mCenterDotRadius == 0) {
            mCenterDotRadius = w / 4;
        }
        for (int i = 0; i < mSmallDotsCount; i++) {
            mSmallDots.add(new SmallCircleDot());
        }
        mCenterDot = new CenterCircle(getContext(), mCenterDotRadius * 2);
        mCenterDot.setClickable(true);
        if (mCenterDotRes == null) {
            mCenterDotRes = getResources().getDrawable(R.drawable.moving_center_dot_bg);
        }
        mCenterDot.setBackground(mCenterDotRes);
        mCenterDot.setProgressTextSize(mProgressTextSize);
        mCenterDot.setProgressTextColor(mProgressTextColor);
        mCenterDot.setProgress(mProgress);
        addView(mCenterDot);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mSmallDotColor);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        for (int j = 0; j < mSmallDots.size(); j++) {
            SmallCircleDot dot = mSmallDots.get(j);
            float progress = (float) ((dot.getZ() - mCenterDotRadius) / (new SmallCircleDot(-getWidth() / 2, -getWidth() / 2, 0).getZ() - mCenterDotRadius));//1~0
            if (progress > 1) {
                progress = 1;
            }
            if (progress < 0) {
                progress = 0;
            }
            int alpha = (int) ((1 - progress) * 200 + 75);
            mPaint.setAlpha(alpha > 255 ? 255 : alpha);
            canvas.drawCircle(dot.getX(), dot.getY(), dot.getRadius(), mPaint);
            dot.checkAndChange();
        }
        postInvalidateDelayed(10);
        canvas.restore();
    }

    /*************************************对外接口****************************************/
    /**
     *TODO 设置小点总数
     *@return void
     */
     public void setDotsCount(int dotsCount) {
         mSmallDotsCount = dotsCount;
     }

     /**
      *TODO 设置中心圆点半径
      *@return void
      */
     public void setCenterDotRadius(int centerDotRadius) {
         mCenterDotRadius = centerDotRadius;
     }

     /**
      *TODO 设置中心圆点背景
      *@return void
      */
     public void setCenterDotRes(Drawable centerDotRes) {
         mCenterDotRes = centerDotRes;
     }

     /**
      *TODO 设置小圆点的颜色
      *@return void
      */
     public void setDotColor(int dotColor) {
         mSmallDotColor = dotColor;
     }

     /**
      *TODO 设置小圆点的最大半径
      *@return void
      */
     public void setMaxDotRadius(int maxDotRadius) {
         mMaxSmallDotRadius = maxDotRadius;
         SmallCircleDot.sMaxDotRadius = maxDotRadius;
     }

     /**
      *TODO 设置小圆点的最小半径
      *@return void
      */
     public void setMinDotRadius(int minDotRadius) {
         mMinSmallDotRadius = minDotRadius;
     }

     /**
      *TODO 设置小圆点的最大速度
      *@return void
      */
     public void setMaxDotSpeed(int maxDotSpeed) {
         mMaxSmallDotSpeed = maxDotSpeed;
     }

     /**
      *TODO 设置小圆点的最大速度(1~10)
      *@return void
      */
     public void setMinDotSpeed(int minDotSpeed) {
         mMinSmallDotSpeed = minDotSpeed;
     }

     /**
      *TODO 设置显示进度的文字大小
      *@return void
      */
     public void setTextSize(int textSize) {
         mProgressTextSize = textSize;
     }

     /**
      *TODO 设置进度显示文字颜色
      *@return void
      */
     public void setTextColor(int textColor) {
         mProgressTextColor = textColor;
     }

     /**
      *TODO 设置动画持续的时间
      *@return void
      */
     public void setAnimatorDuration(long animatorDuration) {
         mAnimatorDuration = animatorDuration;
     }
     
     private int toProgress;
     private boolean isCleaning = false;

     /**
    *TODO 正在清理
    *@return void
    */
    public boolean isCleaning() {
         return isCleaning;
     }
     
    /**
     *TODO 清理前总进度
     *@return void
     */
     public void setProgress(int progress) {
         mProgress = progress;
     }
     
     /**
      *TODO 清理至终点进度
      *@return void
      */
     public void setToProgress(int toProgress) {
         this.toProgress = toProgress;
     }

     /**
      *TODO 开始清理
      *@return void
      */
     public void startClean() {
         startAnimation(0, 1, toProgress);
         isCleaning = true;
     }

     /**
      *TODO 回退至清理前进度
      *@return void
      */
     public void backClean() {
         startAnimation(0.9f, 0, toProgress);
         isCleaning = false;
     }

     /**
      *TODO 动画中性监听器
      *@return void
      */
      public void setChangeListener(OnAnimatorChangeListener changeListener) {
          mAnimChangeListener = changeListener;
      }
     
     /**
      *TODO 开始执行清理动画
      *@return void
      */
     public void startAnimation(float from, float to, final int toProgress) {
         valueAnimator = ValueAnimator.ofFloat(from, to);
         valueAnimator.setDuration(mAnimatorDuration);
         valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
         valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
             @Override
             public void onAnimationUpdate(ValueAnimator valueAnimator) {
                 float progress = (float) valueAnimator.getAnimatedValue();
                 SmallCircleDot.SPEED = (int) (mMinSmallDotSpeed + progress * (mMaxSmallDotSpeed - mMinSmallDotSpeed));
                 mCenterDot.setAnimationPogress(progress);
                 int dotProgress = (int) (mProgress + progress * (toProgress - mProgress));
                 mCenterDot.setProgress(dotProgress);
                 if (mAnimChangeListener != null) {
                     mAnimChangeListener.onProgressChanged(dotProgress);
                 }
                 if(dotProgress == toProgress){
                	 isCleaning = false;
                 }
             }
         });
         valueAnimator.start();
     }
     
     /**
      *TODO 清理完成时复位小圆点速度
      *@return void
      */
     public void stopAnimation() {
    	 mMaxSmallDotSpeed = 1;
    	 mMinSmallDotSpeed = 1;
    	 if(valueAnimator != null && !isCleaning){
    		 valueAnimator.cancel();
			 /*if (Build.VERSION.SDK_INT < 26) {
					valueAnimator.clearAllAnimations();
        	 }*/
    		 
    	 }
     }

}
