package com.xuhc.viewpager2.withRadioGroup;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhc.viewpager2.R;

import androidx.fragment.app.Fragment;


public class MyFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

}
