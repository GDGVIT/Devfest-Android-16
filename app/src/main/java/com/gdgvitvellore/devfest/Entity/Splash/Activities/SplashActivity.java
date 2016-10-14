package com.gdgvitvellore.devfest.Entity.Splash.Activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Contracts.Status;
import com.gdgvitvellore.devfest.Entity.Customs.RevealActivity;
import com.gdgvitvellore.devfest.Entity.Main.Activities.MainActivity;
import com.gdgvitvellore.devfest.Entity.Onboarding.Activities.OnboardingActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

/**
 * Created by Prince Bansal Local on 10/9/2016.
 */

public class SplashActivity extends RevealActivity {


    private ImageView im1, im2;
    private Bundle mSavedInstanceState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init(savedInstanceState);
        setInit();

    }

    private void init(Bundle savedInstanceState) {
        im1 = (ImageView) findViewById(R.id.im1);
        im2 = (ImageView) findViewById(R.id.im2);
        this.mSavedInstanceState = savedInstanceState;
    }

    private void setInit() {
        if (!DataHandler.getInstance(getApplicationContext()).isLoggedIn()) {
            showOnboarding();
        } else {
            showRevealEffect(mSavedInstanceState, findViewById(R.id.root));
        }
    }

    private void showOnboarding() {
        startActivity(new Intent(this, OnboardingActivity.class));
        finish();
    }

    @Override
    public void setActivityRevealed() {
        super.setActivityRevealed();
        ObjectAnimator animator = ObjectAnimator.ofFloat(im1, "translationX", 0, -20, 0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(im2, "translationX", 0, 20, 0);
        animator.setDuration(700);
        animator2.setDuration(700);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator, animator2);
        set.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                destroyActivity(findViewById(R.id.root));
            }
        }, 3000);
    }

    @Override
    public void destroyAnimationFinished() {
        super.destroyAnimationFinished();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
