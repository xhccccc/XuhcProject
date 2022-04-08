package com.xuhc.viewpager2.ringForFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.xuhc.viewpager2.R;
import com.xuhc.viewpager2.ringForFragment.adapter.TestAdapter;
import com.xuhc.viewpager2.ringForFragment.custom.RingViewPager2;
import com.xuhc.viewpager2.ringForFragment.fragment.Test2Fragment;
import com.xuhc.viewpager2.ringForFragment.fragment.Test3Fragment;
import com.xuhc.viewpager2.ringForFragment.fragment.Test4Fragment;
import com.xuhc.viewpager2.ringForFragment.fragment.TestFragment;

public class RingActivity extends AppCompatActivity {

    private ViewPager2 vpTest;
    TestAdapter testAdapter;

    private RingViewPager2 mRingViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        testAdapter = new TestAdapter(this);
        testAdapter.addFragment(new Test4Fragment());
        testAdapter.addFragment(new TestFragment());
        testAdapter.addFragment(new Test2Fragment());
        testAdapter.addFragment(new Test3Fragment());
        testAdapter.addFragment(new Test4Fragment());
        testAdapter.addFragment(new TestFragment());

        mRingViewPager2 = findViewById(R.id.ring_vp);
        mRingViewPager2.setAdapter(testAdapter);
        mRingViewPager2.setAutoTurning(2000);
    }
}