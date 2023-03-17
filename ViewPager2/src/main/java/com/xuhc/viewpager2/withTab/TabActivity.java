package com.xuhc.viewpager2.withTab;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xuhc.viewpager2.R;
import com.xuhc.viewpager2.databinding.LayoutTabBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

public class TabActivity extends AppCompatActivity {

    private ViewPager2 vpTab;
    private TabLayout tabVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        TabAdapter adapter = new TabAdapter(this);
        vpTab = findViewById(R.id.vp_tb);
        tabVp = findViewById(R.id.tb_vp);
        tabVp.setTabTextColors(Color.parseColor("#111111"), Color.parseColor("#0371DD"));

        adapter.addColor(android.R.color.darker_gray);
        adapter.addColor(android.R.color.holo_red_dark);
        adapter.addColor(android.R.color.holo_green_dark);
        adapter.addColor(android.R.color.holo_blue_dark);
        adapter.addColor(android.R.color.holo_purple);
        adapter.addColor(android.R.color.holo_orange_dark);

        vpTab.setAdapter(adapter);


        for (int i = 0; i < adapter.getItemCount(); i++) {
            LayoutTabBinding mTabBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_tab, null, false);
            mTabBinding.itemTxt.setText("测试");
            mTabBinding.itemTxt.setTag("1");
//            mTabBinding.itemTxt.setTextColor(getResources().getColor(R.color.tabTxtDefault));
            TabLayout.Tab tab = tabVp.newTab();
            tab.setText("测试" + i);
            tab.setCustomView(mTabBinding.getRoot());
            tab.view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    tab.select();
                    Log.d("xhccc","onFocusChange: " + v);
                }
            });
            tabVp.addTab(tab);
        }

        tabVp.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab != null && tabVp.isFocused()){
                    TextView mTabView = (TextView) tab.getCustomView().findViewById(R.id.item_txt);
                    if(mTabView != null){
                        mTabView.setSelected(true);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab != null && tabVp.isFocused()){
                    TextView mTabView = (TextView) tab.getCustomView().findViewById(R.id.item_txt);
                    if(mTabView != null){
                        mTabView.setSelected(false);
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new TabLayoutMediator(tabVp, vpTab, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                LayoutTabBinding mTabBinding = DataBindingUtil.inflate(LayoutInflater.from(TabActivity.this), R.layout.layout_tab, null, false);
                mTabBinding.itemTxt.setText("测试");
                mTabBinding.itemTxt.setTag("1");
//            mTabBinding.itemTxt.setTextColor(getResources().getColor(R.color.tabTxtDefault));
                tab.setText("测试" + position);
                tab.setCustomView(mTabBinding.getRoot());
                tab.view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        tab.select();
                        Log.d("xhccc","onFocusChange: " + v);
                    }
                });
//                tab.setText("position_" + position);
            }
        }).attach();
//
        vpTab.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                tabVp.getTabAt(position).select();
//                tabVp.setScrollPosition(position, 0, false);
            }
        });

    }
}
