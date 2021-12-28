package com.xuhc.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ThreadsTryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_try);

        findViewById(R.id.bt_thread).setOnClickListener(this);
        findViewById(R.id.bt_runnable).setOnClickListener(this);
        findViewById(R.id.bt_handle).setOnClickListener(this);
        findViewById(R.id.bt_handle_thread).setOnClickListener(this);
        findViewById(R.id.bt_my_intent_service).setOnClickListener(this);
        findViewById(R.id.bt_thread_pool).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_thread){
            startActivity(new Intent(this,ThreadActivity.class));
        } else if (id == R.id.bt_runnable){
            startActivity(new Intent(this,RunnableActivity.class));
        } else if (id == R.id.bt_handle){
            startActivity(new Intent(this,HandleActivity.class));
        } else if (id == R.id.bt_my_intent_service){
            startActivity(new Intent(this,MyIntentServiceActivity.class));
        } else if (id == R.id.bt_thread_pool){
            startActivity(new Intent(this,ThreadPoolTryActivity.class));
        }
    }
}