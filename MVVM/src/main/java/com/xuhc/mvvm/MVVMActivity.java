package com.xuhc.mvvm;

import android.os.Bundle;

import com.xuhc.mvvm.adapter.FragmentAdapter;
import com.xuhc.mvvm.databinding.ActivityMVVMBinding;
import com.xuhc.mvvm.fragment.ExpandFragment;
import com.xuhc.mvvm.fragment.ExpandFragment2;
import com.xuhc.mvvm.fragment.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

public class MVVMActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + MVVMActivity.class.getSimpleName();

    private ActivityMVVMBinding mActivityMVVMBinding;
    private FragmentAdapter fragmentAdapter;
    private int INIT_VIEWPAGER_CUR_NUMBER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMVVMBinding = DataBindingUtil.setContentView(this, R.layout.activity_m_v_v_m);
        initViewPager2();
    }

    private void initViewPager2() {
        fragmentAdapter = new FragmentAdapter(this);
        mActivityMVVMBinding.viewPager2.setAdapter(fragmentAdapter);
        fragmentAdapter.addFragment(new HomeFragment());
        fragmentAdapter.addFragment(new ExpandFragment());
        fragmentAdapter.addFragment(new ExpandFragment2());
        mActivityMVVMBinding.viewPager2.setCurrentItem(INIT_VIEWPAGER_CUR_NUMBER);
        //初始化indicator
        mActivityMVVMBinding.indicator.setIndicatorPointNumber(fragmentAdapter.getItemCount(), mActivityMVVMBinding.viewPager2.getCurrentItem());
        mActivityMVVMBinding.viewPager2.registerOnPageChangeCallback(mOnPageChangeCallback);
    }

    /**
     * ViewPager2监听
     */
    public ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            mActivityMVVMBinding.indicator.indicatorPointMove(mActivityMVVMBinding.indicator.getDistance(), position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mActivityMVVMBinding.indicator.setIndicatorSelect(position);
            fragmentAdapter.getBaseItem(position).pageSelect();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if (state == 1) {
                mActivityMVVMBinding.indicator.initialIndicator();
            }
        }
    };
}