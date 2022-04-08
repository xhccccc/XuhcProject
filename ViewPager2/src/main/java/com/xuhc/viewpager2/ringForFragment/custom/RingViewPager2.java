package com.xuhc.viewpager2.ringForFragment.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class RingViewPager2 extends FrameLayout {

    private ViewPager2 mViewPager2;

    private boolean canAutoTurning = false;
    private long autoTurningTime;
    private boolean isTurning = false;
    private AutoTurningRunnable mAutoTurningRunnable;

    public RingViewPager2(@NonNull Context context) {
        super(context);
        initialize(context, null);
    }

    public RingViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public RingViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(@NonNull Context context, @Nullable AttributeSet attrs) {
        mViewPager2 = new ViewPager2(context);
        mViewPager2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mViewPager2.setOffscreenPageLimit(1);

        RingOnPageChangeCallback mRingOnPageChangeCallback = new RingOnPageChangeCallback();
        mViewPager2.registerOnPageChangeCallback(mRingOnPageChangeCallback);

        mAutoTurningRunnable = new AutoTurningRunnable(this);

        addView(mViewPager2);
    }

    private class RingOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        private static final int INVALID_ITEM_POSITION = -1;
        private boolean isBeginPagerChange;
        private int mTempPosition = INVALID_ITEM_POSITION;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (isBeginPagerChange) {
                mTempPosition = position;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if (state == ViewPager2.SCROLL_STATE_DRAGGING
                    || (isTurning && state == ViewPager2.SCROLL_STATE_SETTLING)) {
                isBeginPagerChange = true;
            } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                isBeginPagerChange = false;
                int fixCurrentItem = getFixCurrentItem(mTempPosition);
                if (fixCurrentItem != INVALID_ITEM_POSITION && fixCurrentItem != mTempPosition) {
                    mTempPosition = INVALID_ITEM_POSITION;
                    setCurrentItem(fixCurrentItem, false);
                }
            }

        }

        private int getFixCurrentItem(final int position) {
            if (position == INVALID_ITEM_POSITION) {
                return INVALID_ITEM_POSITION;
            }
            int fixPosition = INVALID_ITEM_POSITION;
            if (getAdapter() != null) {
                final int lastPosition = getAdapter().getItemCount() - 1;
                if (position == 0) {
                    fixPosition = lastPosition == 0 ? 0 : lastPosition - 1;
                } else if (position == lastPosition) {
                    fixPosition = 1;
                }
            }
            return fixPosition;
        }
    }

    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        if (mViewPager2.getAdapter() == adapter) {
            return;
        }
        mViewPager2.setAdapter(adapter);
        setCurrentItem(1, false);
    }

    @Nullable
    public RecyclerView.Adapter getAdapter() {
        return mViewPager2.getAdapter();
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mViewPager2.setCurrentItem(item, smoothScroll);
    }

    public int getCurrentItem() {
        return mViewPager2.getCurrentItem();
    }

    static class AutoTurningRunnable implements Runnable {
        private final WeakReference<RingViewPager2> reference;

        AutoTurningRunnable(RingViewPager2 ringViewPager2) {
            this.reference = new WeakReference<>(ringViewPager2);
        }

        @Override
        public void run() {
            RingViewPager2 ringViewPager2 = reference.get();
            if (ringViewPager2 != null && ringViewPager2.canAutoTurning && ringViewPager2.isTurning) {
                if (ringViewPager2.getAdapter() != null) {
                    int itemCount = ringViewPager2.getAdapter().getItemCount();
                    if (itemCount == 0) return;
                    int nextItem = (ringViewPager2.getCurrentItem() + 1) % itemCount;
                    ringViewPager2.setCurrentItem(nextItem, true);
                    ringViewPager2.postDelayed(ringViewPager2.mAutoTurningRunnable, ringViewPager2.autoTurningTime);
                }
            }
        }
    }

    public void setAutoTurning(long autoTurningTime) {
        setAutoTurning(true, autoTurningTime);
    }

    public void setAutoTurning(boolean canAutoTurning, long autoTurningTime) {
        this.canAutoTurning = canAutoTurning;
        this.autoTurningTime = autoTurningTime;
        stopAutoTurning();
        startAutoTurning();
    }

    public void startAutoTurning() {
        if (!canAutoTurning || autoTurningTime <= 0 || isTurning) {
            return;
        }
        isTurning = true;
        postDelayed(mAutoTurningRunnable, autoTurningTime);
    }

    public void stopAutoTurning() {
        isTurning = false;
        removeCallbacks(mAutoTurningRunnable);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            if (canAutoTurning && isTurning) {
                stopAutoTurning();
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE) {
            if (canAutoTurning) {
                startAutoTurning();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
