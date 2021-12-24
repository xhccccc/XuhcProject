package com.xuhc.viewpager.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xuhc.viewpager.util.LogUtil;
import com.xuhc.viewpager.R;


public class MainFragment extends BaseFragment{

    private TextView tvTest;
    private String testShowText;

    public MainFragment() {

    }

    public MainFragment(Context context) {

    }

    public MainFragment(String testShowText) {
        this.testShowText = testShowText;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void visibleToUser(boolean isVisibleToUser) {
        LogUtil.i("xhccc","main_isVisibleToUser: " + isVisibleToUser);

    }

    @Override
    public void pageSelect() {
        LogUtil.i("xhccc","main_pageSelect");
    }

    @Override
    public void resume() {
        LogUtil.i("xhccc","main_resume");
    }

    @Override
    public void pause() {
        LogUtil.i("xhccc","main_pause");
    }

    @Override
    public void detach() {
        LogUtil.i("xhccc","main_detach");

    }


    @Override
    public void initView(View view) {
        tvTest = view.findViewById(R.id.tv_fragment);
    }

    @Override
    public void initFocus() {

    }

    @Override
    public void initData() {
        tvTest.setText(testShowText);
    }

    @Override
    public void addListener() {

    }

}
