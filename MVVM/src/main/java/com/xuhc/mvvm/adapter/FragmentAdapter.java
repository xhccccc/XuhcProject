package com.xuhc.mvvm.adapter;

import com.xuhc.mvvm.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    private List<BaseFragment> fragments;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
    }

    public void addFragment(BaseFragment fragment) {
        if (fragments != null) {
            fragments.add(fragment);
        }
    }

    @NonNull
    @Override
    public BaseFragment createFragment(int position) {
        return (BaseFragment) fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public BaseFragment getBaseItem(int i){
        return fragments.get(i);
    }
}
