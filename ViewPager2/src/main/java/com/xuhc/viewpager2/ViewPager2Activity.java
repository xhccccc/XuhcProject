package com.xuhc.viewpager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xuhc.viewpager2.horizontal.HorizontalActivity;
import com.xuhc.viewpager2.ringForFragment.RingActivity;
import com.xuhc.viewpager2.vertical.VerticalActivity;
import com.xuhc.viewpager2.withRadioGroup.RgActivity;
import com.xuhc.viewpager2.withTab.TabActivity;

import androidx.appcompat.app.AppCompatActivity;

public class ViewPager2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2);
        findViewById(R.id.btn_horizontal).setOnClickListener(this);
        findViewById(R.id.btn_vertical).setOnClickListener(this);
        findViewById(R.id.btn_rg).setOnClickListener(this);
        findViewById(R.id.btn_tab).setOnClickListener(this);
        findViewById(R.id.btn_ring).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_horizontal) {
            startActivity(new Intent(this, HorizontalActivity.class));
        } else if (id == R.id.btn_vertical) {
            startActivity(new Intent(this, VerticalActivity.class));
        } else if (id == R.id.btn_rg) {
            startActivity(new Intent(this, RgActivity.class));
        } else if (id == R.id.btn_tab) {
            startActivity(new Intent(this, TabActivity.class));
        } else if (id == R.id.btn_ring) {
            startActivity(new Intent(this, RingActivity.class));
        }

    }
}
