package com.xuhc.viewpager2.horizontal;

import android.os.Bundle;

import com.xuhc.viewpager2.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class HorizontalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal2);
        ViewPager2 viewPager2 = findViewById(R.id.vp_h);
        HorizontalVpAdapter adapter = new HorizontalVpAdapter(this);
        viewPager2.setAdapter(adapter);
    }
}
