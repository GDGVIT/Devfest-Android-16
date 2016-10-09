package com.gdgvitvellore.devfest.Entity.Customs;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RevealActivity extends AppCompatActivity {

    private View revealView;

    public static final String REVEAL_X = "REVEAL_X";
    public static final String REVEAL_Y = "REVEAL_Y";

    public void showRevealEffect(Bundle savedInstanceState, final View rootView) {

        revealView = rootView;

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootView.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        circularRevealActivity(rootView);

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                    }
                });
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity(View rootView) {

        int cx = getIntent().getIntExtra(REVEAL_X, 0);
        int cy = getIntent().getIntExtra(REVEAL_Y, 0);

        float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0, finalRadius);
        circularReveal.setDuration(700);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());

        // make the view visible and start the animation
        rootView.setVisibility(View.VISIBLE);
        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setActivityRevealed();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        circularReveal.start();

    }

    public void setActivityRevealed() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void destroyActivity(View rootView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            destroyCircularRevealActivity(rootView);
        else
            finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void destroyCircularRevealActivity(final View rootView) {
        int cx = getIntent().getIntExtra(REVEAL_X, 0);
        int cy = getIntent().getIntExtra(REVEAL_Y, 0);

        float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, finalRadius, 0);
        circularReveal.setDuration(700);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());

        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rootView.setVisibility(View.INVISIBLE);
                destroyAnimationFinished();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        // make the view visible and start the animation
        rootView.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    public void destroyAnimationFinished() {

    }
}