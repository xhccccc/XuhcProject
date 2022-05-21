package com.xuhc.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UtilsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils);

        findViewById(R.id.bt_date_utils_activity).setOnClickListener(this);
        findViewById(R.id.bt_catch_crash).setOnClickListener(this);
        findViewById(R.id.bt_network).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        if (id == R.id.bt_date_utils_activity){
            intent.setAction("com.xuhc.xuhcproject.DATE_TIME_UTIL");
        } else if (id == R.id.bt_catch_crash){
            intent.setAction("com.xuhc.xuhcproject.CATCH_CRASH");
        } else if (id == R.id.bt_network){
            intent.setAction("com.xuhc.xuhcproject.NET_WORK_UTIL");
        }

        startActivity(intent);
    }
}