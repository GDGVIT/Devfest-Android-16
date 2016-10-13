package com.gdgvitvellore.devfest.Control.Animations.SlotMachine;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Prince Bansal Local on 10/10/2016.
 */

public class ObjectAnimations {



    public static enum Position{UP,DOWN};

    public static Animator triggerArrowAnimator(View v, int offset){
        ObjectAnimator animator=ObjectAnimator.ofFloat(v,"translationY",0,offset,0);
        animator.setDuration(600);
        animator.setStartDelay(400);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }

    public static Animator slotTranslateAnimation(View v, int repeatCount) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", 0, -80, 80, 0);
        animator.setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(repeatCount);
        animator.setRepeatMode(ValueAnimator.RESTART);
        return animator;
    }

    public static Animator slotAplhaAnimation(View v, int repeatCount) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 1, 0, 0, 1);
        animator.setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(repeatCount);
        animator.setRepeatMode(ValueAnimator.RESTART);
        return animator;
    }

    public static Animator fadeInAnimation(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        animator.setDuration(500);
        return animator;
    }
    public static Animator fadeOutAnimation(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 1f, 0f);
        animator.setDuration(500);
        return animator;
    }

}
