package com.xuhc.viewpager2.withTab;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabAdapter extends FragmentStateAdapter {

    private List<Integer> colors;

    TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        if (colors == null) {
            colors = new ArrayList<>();
        }
    }

    void addColor(int color) {
        if (colors != null) {
            colors.add(color);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ShowFragment.newInstance(colors, position);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }
}
