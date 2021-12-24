package com.xuhc.viewpager.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected Activity mContext;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visibleToUser(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(setLayoutId(), container, false);
        initView(view);
        initFocus();
        initData();
        addListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public abstract int setLayoutId();

    public abstract void initView(View view);

    public abstract void initFocus();

    public abstract void initData();

    public abstract void addListener();

    public abstract void detach();

    public abstract void resume();

    public abstract void pause();

    public abstract void visibleToUser(boolean isVisibleToUser);

    public abstract void pageSelect();

}
