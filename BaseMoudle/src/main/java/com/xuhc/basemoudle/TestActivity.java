package com.xuhc.basemoudle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TestActivity extends BaseActivity {

    @Override
    protected BaseFragment getFragment() {
        return new TestFragment();
    }

}