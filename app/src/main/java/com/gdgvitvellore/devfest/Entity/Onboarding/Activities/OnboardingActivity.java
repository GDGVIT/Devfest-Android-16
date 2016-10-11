package com.gdgvitvellore.devfest.Entity.Onboarding.Activities;

/**
 * Created by Shuvam Ghosh on 10/9/2016.
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gdgvitvellore.devfest.Entity.Authentication.Activities.AuthenticationActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {

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
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        init();
    }

    private void init() {
        cardview1 = (CardView) findViewById(R.id.cardView1);
        cardview2 = (CardView) findViewById(R.id.cardView2);
        cardview3 = (CardView) findViewById(R.id.cardView3);
        cardview4 = (CardView) findViewById(R.id.cardView4);
        iv  = (ImageView)findViewById(R.id.image);
        astronaut = (ImageView)findViewById(R.id.astronaut);
        frameLayout=(FrameLayout)findViewById(R.id.activity_main);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        animationcard1=ObjectAnimator.ofFloat(cardview1,"rotation",142,150);
        animationcard2=ObjectAnimator.ofFloat(cardview2,"rotation",165,170);
        animationcard3=ObjectAnimator.ofFloat(cardview3,"rotation",180,185);
        animationcard4=ObjectAnimator.ofFloat(cardview4,"rotation",180,210);
        animationtext=ObjectAnimator.ofFloat(iv,"alpha",0f,1f);
        astroanim = ObjectAnimator.ofFloat(astronaut,"rotation",0,-30);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setinit();
    }

    private void setinit() {

        fab.setOnClickListener(this);

        animationcard1.setDuration(600);
        animationcard1.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationcard1.start();

        animationcard2.setDuration(800);
        animationcard2.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationcard2.start();

        animationcard3.setDuration(800);
        animationcard3.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationcard3.start();

        animationcard4.setDuration(800);
        animationcard4.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationcard4.start();

        animationtext.setDuration(800);
        //animationtext.start();

        astroanim.setDuration(1500);
        //astroanim.start();

        final AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animationcard1,animationcard2,animationcard3,animationcard4,animationtext,astroanim);
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(this, AuthenticationActivity.class));
        }
    }
}
