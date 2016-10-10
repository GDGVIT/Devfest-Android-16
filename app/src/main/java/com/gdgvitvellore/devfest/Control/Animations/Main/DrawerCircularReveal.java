package com.gdgvitvellore.devfest.Control.Animations.Main;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Prince Bansal Local on 10/10/2016.
 */

public class DrawerCircularReveal {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator circularRevealDrawer(View rootView,int cx, int cy) {

        float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());

        Animator circularReveal = android.view.ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0, finalRadius);
        circularReveal.setDuration(400);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        return circularReveal;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator destroyCircularRevealDrawer(final View rootView, int cx, int cy) {

        float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());

        Animator circularReveal = android.view.ViewAnimationUtils.createCircularReveal(rootView, cx, cy, finalRadius, 0);
        circularReveal.setDuration(400);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        return circularReveal;
    }
}
