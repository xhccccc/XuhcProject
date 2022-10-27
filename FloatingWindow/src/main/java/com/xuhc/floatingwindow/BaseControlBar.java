package com.xuhc.floatingwindow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import java.lang.reflect.Method;

public abstract class BaseControlBar extends RelativeLayout {

    private Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private boolean isAnimating;
    public int direction;
    public static final int ORIENTATION_LEFT = 0;
    public static final int ORIENTATION_RIGHT = 1;
    public static final int SINGLE_CLICK_MAX_SCOPE = 2;
    private OnSideBarTouchListener mOnSideBarTouchListener;

    public BaseControlBar(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        bindView(context);
        initLayoutParams();
        setClickable(false);
        setFocusable(false);
        init();
    }

    /**
     * 布局ID
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 贴边变化
     */
    public abstract void changeEdge(int direction);

    /**
     * 初始化
     */
    public abstract void init();

    private void bindView(Context context) {
        LayoutInflater.from(context).inflate(getLayoutId(), this);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    private void initLayoutParams() {
        mLayoutParams = new WindowManager.LayoutParams();
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        ;
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        mLayoutParams.windowAnimations = android.R.style.Animation_Dialog;
        mLayoutParams.x = (int) getResources().getDisplayMetrics().widthPixels - mLayoutParams.width;
        direction = ORIENTATION_RIGHT;
    }

    public WindowManager.LayoutParams getWindowLayoutParams() {
        return this.mLayoutParams;
    }

    public class FloatingOnTouchListener implements OnTouchListener {
        private int endX;
        private int endY;
        private int startX;
        private int startY;
        private View moveView;
        /**
         * 判断是否响应单击SingleTapUp，就是判断是否是轻击一下屏幕，不能有移动等操作
         */
        private boolean mIsSingleTapUp = true;

        public FloatingOnTouchListener(View view) {
            moveView = view;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (mOnSideBarTouchListener != null) {
                mOnSideBarTouchListener.onTouch();
            }
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_OUTSIDE:
                    if (mOnSideBarTouchListener != null) {
                        mOnSideBarTouchListener.onTouchOutSide();
                    }
                case MotionEvent.ACTION_DOWN:
                    mIsSingleTapUp = true;
                    startX = (int) motionEvent.getRawX();
                    startY = (int) motionEvent.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!isAnimating) {
                        endX = (int) motionEvent.getRawX();
                        endY = (int) motionEvent.getRawY();
                        int dx = endX - startX;
                        int dy = endY - startY;
                        startX = endX;
                        startY = endY;
                        if (Math.abs(dx) > SINGLE_CLICK_MAX_SCOPE || Math.abs(dy) > SINGLE_CLICK_MAX_SCOPE) {
                            // 移动范围超过1不算单击
                            mIsSingleTapUp = false;
                        }

                        getWindowLayoutParams().x += dx;
                        getWindowLayoutParams().y += dy;
                        try {
                            mWindowManager.updateViewLayout(moveView, getWindowLayoutParams());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (getWindowLayoutParams().x + (moveView.getWidth() / 2) > getResources().getDisplayMetrics().widthPixels / 2) {
                        changeEdge(ORIENTATION_RIGHT);
                        moveViewToEdge(moveView, ORIENTATION_RIGHT);
                    } else {
                        changeEdge(ORIENTATION_LEFT);
                        moveViewToEdge(moveView, ORIENTATION_LEFT);
                    }
                    if (!mIsSingleTapUp) {
                        view.setPressed(false);
                        return true;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    public void moveViewToEdge(final View view, final int orientation) {
        int endX;
        if (!isAnimating) {
            direction = orientation;
            int startX = getWindowLayoutParams().x;
            if (orientation == ORIENTATION_LEFT) {
                endX = 0;
            } else {
                endX = getResources().getDisplayMetrics().widthPixels - view.getWidth();
            }
            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setIntValues(startX, endX);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.setDuration(200);
            valueAnimator.addUpdateListener(valueAnim -> {
                try {
                    if (view.isAttachedToWindow()) {
                        getWindowLayoutParams().x = (Integer) valueAnim.getAnimatedValue();
                        mWindowManager.updateViewLayout(view, getWindowLayoutParams());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    isAnimating = false;
                }

                @Override
                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    isAnimating = true;
                }
            });
            valueAnimator.start();

            //不让小球拖动到状态栏位置
            if (getWindowLayoutParams().y < 0) {
                int endY;
                int startY = getWindowLayoutParams().y;
                if (Math.abs(getWindowLayoutParams().y) + getStatusBarHeight() > getResources().getDisplayMetrics().heightPixels / 2) {
                    endY = -(getResources().getDisplayMetrics().heightPixels / 2) + getStatusBarHeight() + getNavigationBarHeight(mContext);
                    ValueAnimator valueAnimatorY = new ValueAnimator();
                    valueAnimatorY.setIntValues(startY, endY);
                    valueAnimatorY.setInterpolator(new AccelerateDecelerateInterpolator());
                    valueAnimatorY.setDuration(200);
                    valueAnimatorY.addUpdateListener(valueAnim -> {
                        try {
                            if (view.isAttachedToWindow()) {
                                getWindowLayoutParams().y = (Integer) valueAnim.getAnimatedValue();
                                mWindowManager.updateViewLayout(view, getWindowLayoutParams());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    valueAnimatorY.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            isAnimating = false;
                        }

                        @Override
                        public void onAnimationStart(Animator animator) {
                            super.onAnimationStart(animator);
                            isAnimating = true;
                        }
                    });
                    valueAnimatorY.start();
                }
            }
        }
    }

    public void setPosition(int directionForOther, int lastY) {
        direction = directionForOther;
        getWindowLayoutParams().y = lastY;
        if (direction == ORIENTATION_LEFT) {
            getWindowLayoutParams().x = 0;
        } else {
            getWindowLayoutParams().x = getResources().getDisplayMetrics().widthPixels - getWindowLayoutParams().width;
        }
        try {
            mWindowManager.updateViewLayout(this, getWindowLayoutParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getDirection() {
        return direction;
    }

    /**
     * 获取状态栏高度
     */
    private int getStatusBarHeight() {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取虚拟按键的高度
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public void setOnSideBarTouchListener(OnSideBarTouchListener onSideBarTouchListener) {
        mOnSideBarTouchListener = onSideBarTouchListener;
    }

    public interface OnSideBarTouchListener {
        /**
         * 点击触摸了window内部的回调
         */
        void onTouch();

        /**
         * 点击了window外部的回调
         */
        void onTouchOutSide();
    }

}
