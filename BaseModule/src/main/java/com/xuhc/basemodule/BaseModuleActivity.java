package com.xuhc.basemodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BaseModuleActivity extends BaseActivity implements View.OnClickListener, OnActivityResultManager.OnActivityResultCallBack {

    private static final String TAG = "xhccc" + BaseModuleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_moudle);

        Button btActivityResult = findViewById(R.id.bt_activity_result);
        btActivityResult.setOnClickListener(this);
    }

    @Override
    protected BaseFragment getFragment() {
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_activity_result){
            Intent intent = new Intent(this, TestActivity.class);
            mOnActivityResultManager.startActivityForResult(intent,100,this);
        }
    }

    @Override
    public void onActivityResultCallBack(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"requestCode: " + requestCode);
        Log.d(TAG,"resultCode: " + resultCode);
    }
}