package com.gdgvitvellore.devfest.Entity.Onboarding.Activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.gdgvitvellore.devfest.Entity.Customs.RevealActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

/**
 * Created by Prince Bansal Local on 10/9/2016.
 */

public class TestSplashActivity extends RevealActivity {


    private ImageView im1,im2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_test);
        showRevealEffect(savedInstanceState,findViewById(R.id.root));
        im1=(ImageView)findViewById(R.id.im1);
        im2=(ImageView)findViewById(R.id.im2);


    }

    @Override
    public void setActivityRevealed() {
        super.setActivityRevealed();
        ObjectAnimator animator=ObjectAnimator.ofFloat(im1,"translationX",0,-20,0);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(im2,"translationX",0,20,0);
        animator.setDuration(700);
        animator2.setDuration(700);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet set=new AnimatorSet();
        set.playTogether(animator,animator2);
        set.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                destroyActivity(findViewById(R.id.root));
            }
        },3000);
    }

    @Override
    public void destroyAnimationFinished() {
        super.destroyAnimationFinished();
        Intent intent=new Intent(TestSplashActivity.this,SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
