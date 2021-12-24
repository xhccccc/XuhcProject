package com.xuhc.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.xuhc.viewpager.fragment.BaseFragment;
import com.xuhc.viewpager.fragment.FragmentAdapter;
import com.xuhc.viewpager.fragment.MainFragment;
import com.xuhc.viewpager.view.IndicatorView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    public ViewPager vpMain;
    private final List<BaseFragment> mViewPageList = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private int INIT_VIEWPAGER_CUR_NUMBER = 1;

    private IndicatorView mIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
        initViewpager();
    }

    private void initView() {
        vpMain = findViewById(R.id.vp_main);

    }

    public void initViewpager(){
        vpMain.setOffscreenPageLimit(3);
        mViewPageList.clear();
        mViewPageList.add(new MainFragment("第一个"));
        mViewPageList.add(new MainFragment("第二个"));
        mViewPageList.add(new MainFragment("第三个"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fragmentManager, mViewPageList);

        vpMain.setAdapter(fragmentAdapter);
        vpMain.setCurrentItem(INIT_VIEWPAGER_CUR_NUMBER);

        mIndicatorView = findViewById(R.id.indicator);
        mIndicatorView.setIndicatorPointNumber(fragmentAdapter.getCount());
        mIndicatorView.setIndicatorSelect(vpMain.getCurrentItem());
        mIndicatorView.setPageSelectPosition(INIT_VIEWPAGER_CUR_NUMBER);

        vpMain.addOnPageChangeListener(mOnPageChangeListener);

        doIndicatorViewAnimation();
    }

    /**
     * ViewPager监听
     */
    public ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mIndicatorView.indicatorPointMove(mIndicatorView.getDistance(),position,positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            mIndicatorView.setPageSelectPosition(position);
            mIndicatorView.setIndicatorSelect(position);
            
            fragmentAdapter.getBaseItem(position).pageSelect();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 1){
                mIndicatorView.initialIndicator();
            } else if (state == 2){
                mIndicatorView.setIndicatorSelectNotInit(mIndicatorView.getPageSelectPosition());
            }
        }
    };

    private void doIndicatorViewAnimation(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator transYAnim = ObjectAnimator.ofFloat(mIndicatorView, "translationY", 400, 0);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(transYAnim);
                set.setDuration(650);
                set.start();
            }
        },100);
    }
}