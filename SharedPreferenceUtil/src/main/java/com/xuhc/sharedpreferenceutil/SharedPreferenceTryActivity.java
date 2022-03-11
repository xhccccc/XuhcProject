package com.xuhc.sharedpreferenceutil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPreferenceTryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvRead;
    private EditText etInput;
    private Button btSave, btRead, btChange;

    private SharedPreferenceUtil mSharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_try);

        initView();
        initData();
    }

    private void initView() {
        tvRead = findViewById(R.id.tv_read);
        etInput = findViewById(R.id.et_shared_preference_try);
        btSave = findViewById(R.id.bt_save);
        btRead = findViewById(R.id.bt_read);
        btChange = findViewById(R.id.bt_change);

        btSave.setOnClickListener(this);
        btRead.setOnClickListener(this);
        btChange.setOnClickListener(this);
    }

    private void initData(){
        mSharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        mSharedPreferenceUtil.addPreferenceChangeListener(mIPreferenceCallback);
    }

    private final IPreferenceCallback mIPreferenceCallback = new IPreferenceCallback() {
        @Override
        public void onPreferenceChange(String key) {
            //判断key值后对应调用方法读取value
            //此处已String为例子
            switch (key) {
                case SharedPreferenceUtil.SP_TRY:
                    String sp_string = mSharedPreferenceUtil.getStringKey(SharedPreferenceUtil.SP_TRY,"sp没有存储任何内容");
                    tvRead.setText(sp_string);
            }
        }
    };


    @Override
    public void onClick(View v) {
        int id = v.getId();

        //以存储字符串为例子
        if (id == R.id.bt_save){
            mSharedPreferenceUtil.setStringKey(SharedPreferenceUtil.SP_TRY,etInput.getText().toString());
        } else if (id == R.id.bt_read){
            String sp_string = mSharedPreferenceUtil.getStringKey(SharedPreferenceUtil.SP_TRY,"sp没有存储任何内容");
            tvRead.setText(sp_string);
        } else if (id == R.id.bt_change){
            mSharedPreferenceUtil.setStringKey(SharedPreferenceUtil.SP_TRY,"回调改变string值");
        }
    }
}