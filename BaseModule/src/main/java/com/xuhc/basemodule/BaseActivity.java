package com.xuhc.basemodule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseActivity extends FragmentActivity {

    private static final String TAG = "xhccc" + BaseActivity.class.getSimpleName();

    private FrameLayout contentFrame;
    public BaseFragment currentFragment;
    public OnActivityResultManager mOnActivityResultManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        contentFrame = (FrameLayout) findViewById(R.id.content_frame);

        mOnActivityResultManager = new OnActivityResultManager(this);

        BaseFragment fragment = getFragment();
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.commitAllowingStateLoss();
            currentFragment = fragment;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void newFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
        currentFragment = fragment;
    }

    public void newFragmentWithoutStack(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commitAllowingStateLoss();
        currentFragment = fragment;
    }

    public void newRootFragment(BaseFragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commitAllowingStateLoss();
        currentFragment = fragment;
    }

    protected abstract BaseFragment getFragment();

    public void back(){
        onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mOnActivityResultManager.onActivityResult(requestCode,resultCode,data);
    }
}
