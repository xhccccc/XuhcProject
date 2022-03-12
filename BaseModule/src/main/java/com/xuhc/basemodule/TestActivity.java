package com.xuhc.basemodule;

import android.content.Intent;

public class TestActivity extends BaseActivity {

    @Override
    protected BaseFragment getFragment() {
        return new TestFragment();
    }

    @Override
    public void onBackPressed() {
        setResult(450, new Intent().putExtra("my-data", "data"));
        super.onBackPressed();
    }

}