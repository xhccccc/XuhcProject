package com.xuhc.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class CustomBroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomReceiver mCustomReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_broadcast);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.XHC");
        mCustomReceiver = new CustomReceiver();
        registerReceiver(mCustomReceiver, intentFilter);

        findViewById(R.id.bt_custom_broadcast).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCustomReceiver);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_custom_broadcast){
            Intent intent = new Intent("android.intent.action.XHC");
            sendBroadcast(intent);
        }
    }
}