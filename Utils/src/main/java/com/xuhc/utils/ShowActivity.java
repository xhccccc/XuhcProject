package com.xuhc.utils;


import com.xuhc.basemodule.BaseActivity;
import com.xuhc.basemodule.BaseFragment;
import com.xuhc.basemodule.TestFragment;
import com.xuhc.utils.crash.CrashFragment;
import com.xuhc.utils.date.DateTimeFragment;

public class ShowActivity extends BaseActivity {

    @Override
    protected BaseFragment getFragment() {
        String action = getIntent().getAction();
        if (action.equals("com.xuhc.xuhcproject.DATE_TIME_UTIL")) {
            return new DateTimeFragment();
        } else if (action.equals("com.xuhc.xuhcproject.CATCH_CRASH")) {
            return new CrashFragment();
        }

        return new TestFragment();
    }
}