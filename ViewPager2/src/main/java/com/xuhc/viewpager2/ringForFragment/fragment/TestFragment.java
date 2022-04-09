package com.xuhc.viewpager2.ringForFragment.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhc.viewpager2.R;

import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("xhccc","TestFragment_onCreateView: " + this);
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("xhccc","TestFragment_onResume: " + this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("xhccc","TestFragment_onDestroyView: " + this);
    }
}
