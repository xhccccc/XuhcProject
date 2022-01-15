package com.xuhc.mvvm.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhc.mvvm.base.BaseFragment;
import com.xuhc.mvvm.databinding.FragmentHomeBinding;
import com.xuhc.mvvm.viewmodel.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "xhccc" + HomeFragment.class.getSimpleName();

    private FragmentHomeBinding mFragmentHomeBinding;
    private HomeViewModel mHomeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        initData();
        addListener();
        return mFragmentHomeBinding.getRoot();
    }

    @Override
    public void resume() {
        Log.d(TAG,"resume");
    }

    @Override
    public void initData() {
        Log.d(TAG,"initData");
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mHomeViewModel.startCountDown();
    }

    @Override
    public void addListener() {
        Log.d(TAG,"addListener");
        mHomeViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mFragmentHomeBinding.tvLiveDataTest.setText(String.valueOf(aLong/1000));
            }
        });
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
