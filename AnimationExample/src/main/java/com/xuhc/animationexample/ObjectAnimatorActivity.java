package com.xuhc.animationexample;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ObjectAnimatorActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + ObjectAnimatorActivity.class.getSimpleName();

    private Button translate;
    private Button scale;
    private Button rotate;
    private Button alpha;
    private Button set;
    private Button animator;
    private Button animator_group;
    private Button test;
    private ImageView animationTest;
    private ImageView animationTest2;
    private ImageView animationTest3;
    private ImageView animationTest4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);

        initView();
        initListener();
        test();
    }

    private void initView(){
        translate = (Button) findViewById(R.id.translate);
        scale = (Button) findViewById(R.id.scale);
        rotate = (Button) findViewById(R.id.rotate);
        alpha = (Button) findViewById(R.id.alpha);
        set = (Button) findViewById(R.id.set);
        animator = (Button) findViewById(R.id.animator);
        animator_group = (Button) findViewById(R.id.animator_group);
        test = (Button) findViewById(R.id.test);

        animationTest = (ImageView) findViewById(R.id.animation_test);
        animationTest2 = (ImageView) findViewById(R.id.animation_test2);
        animationTest3 = (ImageView) findViewById(R.id.animation_test3);
        animationTest4 = (ImageView) findViewById(R.id.animation_test4);

    }

    private void initListener(){
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ObjectAnimatorActivity.this, R.anim.translate_animation);
                animationTest.startAnimation(animation);
            }
        });

        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ObjectAnimatorActivity.this, R.anim.scale_animation);
                animationTest.startAnimation(animation);
            }
        });

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ObjectAnimatorActivity.this, R.anim.rotate_animation);
                animationTest.startAnimation(animation);
            }
        });

        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ObjectAnimatorActivity.this, R.anim.alpha_animation);
                animationTest.startAnimation(animation);
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ObjectAnimatorActivity.this, R.anim.set_animation);
                animationTest.startAnimation(animation);
            }
        });

        animator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation();
            }
        });

        animator_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupAnimation();
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });

        animationTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ObjectAnimatorActivity.this,"animationTest",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void test(){
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(animationTest, "scaleX", 2, 1);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(animationTest, "scaleY", 2, 1);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(animationTest, "translationX", -150, 0);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(animationTest, "translationY", -100, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleXAnim, scaleYAnim,transXAnim, transYAnim);
//        set.playSequentially(scaleXAnim, scaleYAnim, transXAnim, transYAnim);
        set.setDuration(650);
        set.start();

        ObjectAnimator scaleXAnim2 = ObjectAnimator.ofFloat(animationTest2, "scaleX", 2.1f, 1);
        ObjectAnimator scaleYAnim2 = ObjectAnimator.ofFloat(animationTest2, "scaleY", 2.1f, 1);
        ObjectAnimator transXAnim2 = ObjectAnimator.ofFloat(animationTest2, "translationX", -40, 0);
        ObjectAnimator transYAnim2 = ObjectAnimator.ofFloat(animationTest2, "translationY", -100, 0);
        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(scaleXAnim2, scaleYAnim2,transXAnim2, transYAnim2);
//        set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
        set2.setDuration(700);
        set2.start();

        ObjectAnimator scaleXAnim3 = ObjectAnimator.ofFloat(animationTest3, "scaleX", 2.2f, 1);
        ObjectAnimator scaleYAnim3 = ObjectAnimator.ofFloat(animationTest3, "scaleY", 2.2f, 1);
        ObjectAnimator transXAnim3 = ObjectAnimator.ofFloat(animationTest3, "translationX", 40, 0);
        ObjectAnimator transYAnim3 = ObjectAnimator.ofFloat(animationTest3, "translationY", -100, 0);
        AnimatorSet set3 = new AnimatorSet();
        set3.playTogether(scaleXAnim3, scaleYAnim3,transXAnim3, transYAnim3);
//        set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
        set3.setDuration(750);
        set3.start();

        ObjectAnimator scaleXAnim4 = ObjectAnimator.ofFloat(animationTest4, "scaleX", 2.3f, 1);
        ObjectAnimator scaleYAnim4 = ObjectAnimator.ofFloat(animationTest4, "scaleY", 2.3f, 1);
        ObjectAnimator transXAnim4 = ObjectAnimator.ofFloat(animationTest4, "translationX", 150, 0);
        ObjectAnimator transYAnim4 = ObjectAnimator.ofFloat(animationTest4, "translationY", -100, 0);
        AnimatorSet set4 = new AnimatorSet();
        set4.playTogether(scaleXAnim4, scaleYAnim4,transXAnim4, transYAnim4);
//        set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
        set4.setDuration(800);
        set4.start();
    }

    private void RotateAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(animationTest, "rotation", 0f, 360f);
        anim.setDuration(1000);
        anim.start();
    }

    private void AlphaAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(animationTest, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f);
        anim.setRepeatCount(-1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setDuration(2000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG,"animation.getAnimatedValue(): " + animation.getAnimatedValue());
            }
        });
        anim.start();
    }

    private void GroupAnimation() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(animationTest, "alpha", 1.0f, 0.5f, 0.8f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(animationTest, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(animationTest, "scaleY", 0.0f, 2.0f);
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(animationTest, "rotation", 0, 360);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(animationTest, "translationX", 0, 100);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(animationTest, "translationY", 0, 100);
        AnimatorSet set = new AnimatorSet();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                Log.d(TAG,"onAnimationCancel: " + animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d(TAG,"onAnimationEnd: " + animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Log.d(TAG,"onAnimationRepeat: " + animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.d(TAG,"onAnimationStart: " + animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
                Log.d(TAG,"onAnimationPause: " + animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
                Log.d(TAG,"onAnimationResume: " + animation);
            }
        });
        set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
//        set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
        set.setDuration(3000);
        set.start();
    }
}