package com.xuhc.viewpager2.ringForFragment.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhc.viewpager2.R;

import androidx.fragment.app.Fragment;

public class Test4Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("xhccc","Test4Fragment_onCreateView");
        return inflater.inflate(R.layout.fragment_test4, container, false);
    }

}
