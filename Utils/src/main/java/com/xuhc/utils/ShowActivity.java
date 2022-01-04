package com.xuhc.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;

import com.xuhc.basemoudle.BaseActivity;
import com.xuhc.basemoudle.BaseFragment;
import com.xuhc.basemoudle.TestFragment;

public class ShowActivity extends BaseActivity {

    @Override
    protected BaseFragment getFragment() {
        String action = getIntent().getAction();
        if (action.equals("com.xuhc.xuhcproject.DATE_TIME_UTIL")) {
            return new DateTimeFragment();
        }

        return new TestFragment();
    }
}