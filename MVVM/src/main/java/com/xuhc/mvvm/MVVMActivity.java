package com.xuhc.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.xuhc.mvvm.adapter.FragmentAdapter;
import com.xuhc.mvvm.databinding.ActivityMVVMBinding;
import com.xuhc.mvvm.fragment.ExpandFragment;
import com.xuhc.mvvm.fragment.ExpandFragment2;
import com.xuhc.mvvm.fragment.HomeFragment;

public class MVVMActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + MVVMActivity.class.getSimpleName();

    private ActivityMVVMBinding mActivityMVVMBinding;
    private FragmentAdapter fragmentAdapter;
    private int INIT_VIEWPAGER_CUR_NUMBER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMVVMBinding = DataBindingUtil.setContentView(this,R.layout.activity_m_v_v_m);
        initViewPager2();
    }

    private void initViewPager2(){
        fragmentAdapter = new FragmentAdapter(this);
        mActivityMVVMBinding.viewPager.setAdapter(fragmentAdapter);
        fragmentAdapter.addFragment(new HomeFragment());
        fragmentAdapter.addFragment(new ExpandFragment());
        fragmentAdapter.addFragment(new ExpandFragment2());
        mActivityMVVMBinding.viewPager.setCurrentItem(INIT_VIEWPAGER_CUR_NUMBER);

        mActivityMVVMBinding.indicator.setIndicatorPointNumber(fragmentAdapter.getItemCount());
        mActivityMVVMBinding.indicator.setIndicatorSelect(mActivityMVVMBinding.viewPager.getCurrentItem());
        mActivityMVVMBinding.indicator.setPageSelectPosition(INIT_VIEWPAGER_CUR_NUMBER);

        mActivityMVVMBinding.viewPager.registerOnPageChangeCallback(mOnPageChangeCallback);
    }

    /**
     * ViewPager2监听
     */
    public ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mActivityMVVMBinding.indicator.indicatorPointMove(mActivityMVVMBinding.indicator.getDistance(),position,positionOffset);
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            mActivityMVVMBinding.indicator.setPageSelectPosition(position);
            mActivityMVVMBinding.indicator.setIndicatorSelect(position);

            fragmentAdapter.getBaseItem(position).pageSelect();

            super.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 1){
                mActivityMVVMBinding.indicator.initialIndicator();
            } else if (state == 2){
                mActivityMVVMBinding.indicator.setIndicatorSelectNotInit(mActivityMVVMBinding.indicator.getPageSelectPosition());
            }
            super.onPageScrollStateChanged(state);
        }
    };
}