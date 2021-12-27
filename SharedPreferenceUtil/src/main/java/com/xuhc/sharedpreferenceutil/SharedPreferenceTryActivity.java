package com.xuhc.sharedpreferenceutil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPreferenceTryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvRead;
    private EditText etInput;
    private Button btSave, btRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_try);

        initView();
    }

    private void initView() {
        tvRead = findViewById(R.id.tv_read);
        etInput = findViewById(R.id.et_shared_preference_try);
        btSave = findViewById(R.id.bt_save);
        btRead = findViewById(R.id.bt_read);

        btSave.setOnClickListener(this);
        btRead.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        //以存储字符串为例子
        if (id == R.id.bt_save){
            SharedPreferenceUtil.setStringKey(this,SharedPreferenceUtil.SP_TRY,etInput.getText().toString());
        } else if (id == R.id.bt_read){
            String sp = SharedPreferenceUtil.getStringKey(this,SharedPreferenceUtil.SP_TRY,"sp没有存储任何内容");
            tvRead.setText(sp);
        }
    }
}