package com.gdgvitvellore.devfest.Control.Animations.SlotMachine;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
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

}
