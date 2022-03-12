package com.xuhc.basemodule;

public class TestActivity extends BaseActivity {

    @Override
    protected BaseFragment getFragment() {
        return new TestFragment();
    }

}