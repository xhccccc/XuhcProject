package com.xuhc.viewpager2.ringForFragment;

import android.graphics.Color;
import android.os.Bundle;

import com.xuhc.viewpager2.R;
import com.xuhc.viewpager2.ringForFragment.adapter.TestAdapter;
import com.xuhc.viewpager2.ringForFragment.custom.RingViewPager2;
import com.xuhc.viewpager2.ringForFragment.custom.draw.IndicatorView;
import com.xuhc.viewpager2.ringForFragment.fragment.Test2Fragment;
import com.xuhc.viewpager2.ringForFragment.fragment.Test3Fragment;
import com.xuhc.viewpager2.ringForFragment.fragment.Test4Fragment;
import com.xuhc.viewpager2.ringForFragment.fragment.TestFragment;

import androidx.appcompat.app.AppCompatActivity;

public class RingActivity extends AppCompatActivity {

    TestAdapter testAdapter;

    private RingViewPager2 mRingViewPager2;

    TestFragment testFragment;
    Test2Fragment test2Fragment;
    Test3Fragment test3Fragment;
    Test4Fragment test4Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        initFragment();

        testAdapter = new TestAdapter(this);
        testAdapter.addFragment(test4Fragment);//f0
        testAdapter.addFragment(testFragment);//f1
        testAdapter.addFragment(test2Fragment);//f2
        testAdapter.addFragment(test3Fragment);//f3
        testAdapter.addFragment(test4Fragment);//f0
        testAdapter.addFragment(testFragment);

        final IndicatorView indicatorView = new IndicatorView(this)
                .setIndicatorRatio(1f)
                .setIndicatorRadius(4f)
                .setIndicatorSelectedRatio(3)
                .setIndicatorSelectedRadius(4f)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_CIRCLE_RECT)
                .setIndicatorColor(Color.GRAY)
                .setIndicatorSelectorColor(Color.WHITE);

        mRingViewPager2 = findViewById(R.id.ring_vp);
        mRingViewPager2.setIndicator(indicatorView);
        mRingViewPager2.setAdapter(testAdapter);
        mRingViewPager2.setAutoTurning(2000);
    }

    private void initFragment() {
        testFragment = (TestFragment) getSupportFragmentManager().findFragmentByTag("f1");
        if (testFragment == null) {
            testFragment = new TestFragment();
        }

        test2Fragment = (Test2Fragment) getSupportFragmentManager().findFragmentByTag("f2");
        if (test2Fragment == null) {
            test2Fragment = new Test2Fragment();
        }

        test3Fragment = (Test3Fragment) getSupportFragmentManager().findFragmentByTag("f3");
        if (test3Fragment == null) {
            test3Fragment = new Test3Fragment();
        }

        test4Fragment = (Test4Fragment) getSupportFragmentManager().findFragmentByTag("f0");
        if (test4Fragment == null) {
            test4Fragment = new Test4Fragment();
        }
    }
}