package com.xuhc.mvvm.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhc.mvvm.base.BaseFragment;
import com.xuhc.mvvm.databinding.FragmentExpand2Binding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ExpandFragment2 extends BaseFragment {

    private static final String TAG = "xhccc" + ExpandFragment2.class.getSimpleName();

    private FragmentExpand2Binding mFragmentExpand2Binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentExpand2Binding = FragmentExpand2Binding.inflate(inflater, container, false);
        initData();
        addListener();
        return mFragmentExpand2Binding.getRoot();
    }

    @Override
    public void resume() {
        Log.d(TAG,"resume");
    }

    @Override
    public void initData() {
        Log.d(TAG,"initData");
    }

    @Override
    public void addListener() {
        Log.d(TAG,"addListener");
    }

    @Override
    public void detach() {
        Log.d(TAG,"detach");
    }

    @Override
    public void pause() {
        Log.d(TAG,"pause");
    }

    @Override
    public void stop() {
        Log.d(TAG,"stop");
    }

    @Override
    public void pageSelect() {
        Log.d(TAG,"pageSelect");
    }
}
