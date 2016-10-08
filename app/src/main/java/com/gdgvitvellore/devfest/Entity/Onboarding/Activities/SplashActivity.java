package com.gdgvitvellore.devfest.Entity.Onboarding.Activities;

/**
 * Created by Shuvam Ghosh on 10/9/2016.
 */

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gdgvitvellore.devfest.gdgdevfest.R;

public class SplashActivity extends AppCompatActivity {

    private CardView cardview1;
    private CardView cardview2;
    private CardView cardview3;
    private CardView cardview4;
    private FrameLayout frameLayout;
    private ObjectAnimator animationcard1;
    private ObjectAnimator animationcard2;
    private ObjectAnimator animationcard3;
    private ObjectAnimator animationcard4;
    private ObjectAnimator animationtext;
    private ObjectAnimator astroanim;
    private ImageView iv;
    private ImageView astronaut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        setinit();
    }

    private void setinit() {

        animationcard1.setDuration(600);
        animationcard1.setInterpolator(new AccelerateDecelerateInterpolator());
        animationcard1.start();

        animationcard2.setDuration(800);
        animationcard2.setInterpolator(new AccelerateDecelerateInterpolator());
        animationcard2.start();

        animationcard3.setDuration(800);
        animationcard3.setInterpolator(new AccelerateDecelerateInterpolator());
        animationcard3.start();

        animationcard4.setDuration(800);
        animationcard4.setInterpolator(new AccelerateDecelerateInterpolator());
        animationcard4.start();

        animationtext.setDuration(800);
        animationtext.start();

        astroanim.setDuration(1500);
        astroanim.start();
    }

    private void init() {
        cardview1 = (CardView) findViewById(R.id.cardView1);
        cardview2 = (CardView) findViewById(R.id.cardView2);
        cardview3 = (CardView) findViewById(R.id.cardView3);
        cardview4 = (CardView) findViewById(R.id.cardView4);
        iv  = (ImageView)findViewById(R.id.image);
        astronaut = (ImageView)findViewById(R.id.astronaut);
        frameLayout=(FrameLayout)findViewById(R.id.activity_main);


        animationcard1=ObjectAnimator.ofFloat(cardview1,"rotation",142,150);
        animationcard2=ObjectAnimator.ofFloat(cardview2,"rotation",165,170);
        animationcard3=ObjectAnimator.ofFloat(cardview3,"rotation",180,185);
        animationcard4=ObjectAnimator.ofFloat(cardview4,"rotation",180,210);
        animationtext=ObjectAnimator.ofFloat(iv,"alpha",0f,1f);
        astroanim = ObjectAnimator.ofFloat(astronaut,"rotation",0,-30);

    }

}
