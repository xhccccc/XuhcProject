package com.xuhc.animationexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xuhc.animationexample.clean.CleanActivity;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationExampleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "xhccc" + AnimationExampleActivity.class.getSimpleName();

    private Button btObjectAnimator,
            btCleanAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_example);

        initView();
        initListener();
    }

    private void initView() {
        btObjectAnimator = findViewById(R.id.bt_object_animator);
        btCleanAnimation = findViewById(R.id.bt_clean_animation);
    }

    private void initListener() {
        btObjectAnimator.setOnClickListener(this);
        btCleanAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_object_animator) {
            startActivity(new Intent(AnimationExampleActivity.this, ObjectAnimatorActivity.class));
        } else if (id == R.id.bt_clean_animation) {
            startActivity(new Intent(AnimationExampleActivity.this, CleanActivity.class));
        }
    }
}