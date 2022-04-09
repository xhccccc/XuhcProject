package com.xuhc.viewpager2.ringForFragment.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhc.viewpager2.R;

import androidx.fragment.app.Fragment;

public class Test3Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("xhccc","Test3Fragment_onCreateView : " + this);
        return inflater.inflate(R.layout.fragment_test3, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("xhccc","Test3Fragment_onResume: " + this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("xhccc","Test3Fragment_onDestroyView: " + this);
    }
}
