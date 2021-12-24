package com.xhc.xhcnote.tool;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import com.xhc.xhcnote.R;


public class AnimUtils {

    /**
     * Addanimation.画面扩大
     *
     * @param view1 the view 1
     * @param view2 the view 2
     */
    public static void addanimation(View view1, final View view2){
        Animator animator;
        int xc=(view1.getLeft()+view1.getRight())/2;
        int yc=(view1.getTop()+view1.getBottom())/2;
        animator = ViewAnimationUtils.createCircularReveal(view2,xc,yc,0,3333);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
//                startActivityForResult(new Intent(MainActivity.class, AddActivity.class),1);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view2.getBackground().setAlpha(0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
        view2.getBackground().setAlpha(255);
    }

    //缩放
    public static void setScaleAnimation(final View views, int duration, Context context){
        ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(context, R.anim.scale);
        scaleAnimation.setDuration(duration);
//        scaleAnimation.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnimation.setRepeatMode(ValueAnimator.REVERSE);
//        scaleAnimation.setRepeatMode(ValueAnimator.RESTART);
        views.startAnimation(scaleAnimation);

    }

    public static void clean(View view){
        view.clearAnimation();
    }

}
