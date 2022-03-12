package com.xuhc.basemodule;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BaseModuleActivity extends BaseActivity implements View.OnClickListener, OnActivityResultManager.OnActivityResultCallBack {

    private static final String TAG = "xhccc" + BaseModuleActivity.class.getSimpleName();
    
    ActivityResultLauncher<Intent> intentActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_moudle);

        Button btActivityResult = findViewById(R.id.bt_activity_result);
        btActivityResult.setOnClickListener(this);
        Button btActivityResultNew = findViewById(R.id.bt_activity_result_new);
        btActivityResultNew.setOnClickListener(this);

        initActivityResult();
    }

    private void initActivityResult(){
        ActivityResultContracts.StartActivityForResult mStartActivityForResult  = new ActivityResultContracts.StartActivityForResult();
        intentActivityResultLauncher = registerForActivityResult(mStartActivityForResult, new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d(TAG,"result: " + result.toString());
            }
        });
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
        } else if (id == R.id.bt_activity_result_new){
            Intent intent = new Intent(this, TestActivity.class);
            intentActivityResultLauncher.launch(intent);
        }
    }

    @Override
    public void onActivityResultCallBack(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"requestCode: " + requestCode);
        Log.d(TAG,"resultCode: " + resultCode);
        if (data != null){
            Log.d(TAG,"data: " + data.toString());
        }
    }
}