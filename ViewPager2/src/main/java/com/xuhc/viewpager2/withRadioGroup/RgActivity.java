package com.xuhc.viewpager2.withRadioGroup;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xuhc.viewpager2.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class RgActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ViewPager2 vpRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rg);
        RgAdapter adapter = new RgAdapter(this);

        RadioGroup rgVp = findViewById(R.id.rg_vp);
        vpRg = findViewById(R.id.vp_rg);
        rgVp.setOnCheckedChangeListener(this);

        vpRg.setAdapter(adapter);
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new MessageFragment());
        adapter.addFragment(new MyFragment());
        vpRg.setCurrentItem(0);


        vpRg.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        ((RadioButton) findViewById(R.id.rb_home)).setChecked(true);
                        break;
                    case 1:
                        ((RadioButton) findViewById(R.id.rb_msg)).setChecked(true);
                        break;
                    case 2:
                        ((RadioButton) findViewById(R.id.rg_my)).setChecked(true);
                        break;
                }
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


        if (checkedId == R.id.rb_home) {
            vpRg.setCurrentItem(0);
        } else if (checkedId == R.id.rb_msg) {
            vpRg.setCurrentItem(1);
        } else if (checkedId == R.id.rg_my) {
            vpRg.setCurrentItem(2);
        }
    }
}
