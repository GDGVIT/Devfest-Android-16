package com.gdgvitvellore.devfest.Control.Animations.Timeline;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

/**
 * Created by Prince Bansal Local on 10/20/2016.
 */

public class ObjectAnimations {

    public static Animator scaleUp(View v){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1f, 1.1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1f, 1.1f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(scaleX,scaleY);
        return animatorSet;
    }
    public static Animator reset(View v){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1f, 1f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(scaleX,scaleY);
        return animatorSet;
    }
    public static Animator scaleDown(View v){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1.1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1.1f, 1f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(scaleX,scaleY);
        return animatorSet;
    }
}
