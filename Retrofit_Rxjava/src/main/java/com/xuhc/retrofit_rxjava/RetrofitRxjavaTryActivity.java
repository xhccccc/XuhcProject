package com.xuhc.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xuhc.retrofit_rxjava.retrofit.RetrofitActivity;
import com.xuhc.retrofit_rxjava.rxjava.RxjavaActivity;
import com.xuhc.retrofit_rxjava.together.TogetherActivity;

public class RetrofitRxjavaTryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_rxjava_try);

        findViewById(R.id.bt_retrofit).setOnClickListener(this);
        findViewById(R.id.bt_rxjava).setOnClickListener(this);
        findViewById(R.id.bt_retrofit_and_rxjava).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_retrofit){
            startActivity(new Intent(this, RetrofitActivity.class));
        } else if (id == R.id.bt_rxjava){
            startActivity(new Intent(this, RxjavaActivity.class));
        } else if (id == R.id.bt_retrofit_and_rxjava){
            startActivity(new Intent(this, TogetherActivity.class));
        }
    }
}