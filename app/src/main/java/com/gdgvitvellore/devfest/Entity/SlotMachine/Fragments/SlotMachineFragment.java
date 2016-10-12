package com.gdgvitvellore.devfest.Entity.SlotMachine.Fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdgvitvellore.devfest.Control.Animations.SlotMachine.ObjectAnimations;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.Random;

/**
 * Created by Shuvam Ghosh on 10/11/2016.
 */

public class SlotMachineFragment extends Fragment {

    private ImageView trigger;
    private ImageView slot1,slot2,slot3;
    private LinearLayout triggerHolder,arrowLayout;
    private TextView timeRemaining;
    private int min=0;
    private int sec=0;


    private int imgResources[]={
            R.drawable.github_api_150,
            R.drawable.uber_api_150,
            R.drawable.github_api_150,
            R.drawable.uber_api_150
    };
    private CountDownTimer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_slot_machine,container,false);
        init(rootView);
        setInit();
        return rootView;
    }

    private void init(View rootView) {
        trigger = (ImageView)rootView.findViewById(R.id.imageView);
        slot1 = (ImageView) rootView.findViewById(R.id.slot1);
        slot2 = (ImageView) rootView.findViewById(R.id.slot2);
        slot3 = (ImageView)rootView.findViewById(R.id.slot3);
        arrowLayout = (LinearLayout)rootView.findViewById(R.id.arrows_layout);
        triggerHolder=(LinearLayout)rootView.findViewById(R.id.trigger_holder);
        timeRemaining = (TextView)rootView.findViewById(R.id.timeRemaining);

    }


    private void setInit() {

        ObjectAnimations.triggerArrowAnimator(triggerHolder,60).start();
        timeRemaining.setVisibility(View.INVISIBLE);
        trigger.setOnTouchListener(new View.OnTouchListener() {

            PointF DownPT = new PointF();
            PointF StartPT = new PointF();

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int eid = motionEvent.getAction();
                switch(eid)
                {
                    case MotionEvent.ACTION_MOVE:
                        PointF mv = new PointF(motionEvent.getX()-DownPT.x,motionEvent.getY()- DownPT.y);
                        Log.i("st+mv", "onTouch: "+StartPT.y+"*"+mv.y+"*"+(StartPT.y+mv.y));
                        Rect rec=new Rect();
                        Rect rec2=new Rect();
                        triggerHolder.getLocalVisibleRect(rec2);
                        trigger.getLocalVisibleRect(rec);
                        if(StartPT.y+mv.y>=rec2.top&&StartPT.y+mv.y<=rec2.height()-rec.height()){
                            trigger.setY((int) (StartPT.y+mv.y));
                            StartPT = new PointF(trigger.getX(), trigger.getY());
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:

                        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(arrowLayout,"alpha",1f,0f);
                        fadeOut.setDuration(400);
                        fadeOut.start();
                        DownPT.x = motionEvent.getX();
                        DownPT.y = motionEvent.getY();
                        StartPT = new PointF( trigger.getX(), trigger.getY());
                        break;
                    case MotionEvent.ACTION_UP :


                        float in= trigger.getY();
                        ObjectAnimator animator = ObjectAnimator.ofFloat(trigger,"y",in,35,20,30,20,25,20);
                        animator.setDuration(500);
                        animator.start();
                        animateSlots();
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(arrowLayout,"alpha",0f,1f);
                                fadeIn.setDuration(500);
                                fadeIn.start();
                                arrowLayout.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });

                        break;
                    default :
                        break;
                }

                return true;
            }
        });
    }

    private void animateSlots() {


        ObjectAnimator animator1 = ObjectAnimator.ofFloat(slot1,"translationY",0,-80,80,0);
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(slot1,"alpha",1,0,0,1);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(slot2,"translationY",0,-80,80,0);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(slot2,"alpha",1,0,0,1);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(slot3,"translationY",0,-80,80,0);
        ObjectAnimator alpha3 = ObjectAnimator.ofFloat(slot3,"alpha",1,0,0,1);


        animator1.setDuration(100);
        alpha1.setDuration(100);

        animator2.setDuration(100);
        alpha2.setDuration(100);

        animator3.setDuration(100);
        alpha3.setDuration(100);

        animator1.setInterpolator(new LinearInterpolator());
        alpha1.setInterpolator(new LinearInterpolator());
        animator1.setRepeatCount(60);
        animator2.setRepeatCount(80);
        animator3.setRepeatCount(100);


        animator1.setRepeatMode(ValueAnimator.RESTART);
        animator2.setRepeatMode(ValueAnimator.RESTART);
        animator3.setRepeatMode(ValueAnimator.RESTART);



        alpha1.setRepeatCount(60);
        alpha2.setRepeatCount(80);
        alpha3.setRepeatCount(100);


        alpha1.setRepeatMode(ValueAnimator.RESTART);
        alpha2.setRepeatMode(ValueAnimator.RESTART);
        alpha3.setRepeatMode(ValueAnimator.RESTART);


        AnimatorSet set=new AnimatorSet();
        set.playTogether(animator1,alpha1,animator2,alpha2,animator3,alpha3);
        set.start();
        CountDownTimer timer=new CountDownTimer(6000,100) {
            int i=1;
            @Override
            public void onTick(long l) {
                slot1.setImageResource(imgResources[i]);
                if(i>=3){
                    i=0;
                }else{
                    i++;
                }
            }
            @Override
            public void onFinish() {

            }
        }.start();

        CountDownTimer timer1 = new CountDownTimer(8000,100) {
            int j=2;
            @Override
            public void onTick(long l) {
                int i = new Random().nextInt(4);
                int j = new Random().nextInt(4);
                int k = new Random().nextInt(4);
                slot2.setImageResource(imgResources[j]);
                if(j>=3){
                    j=0;
                }else{
                    j++;
                }
            }
            @Override
            public void onFinish() {
            }
        }.start();

        CountDownTimer timer2=new CountDownTimer(10000,100) {
            int k=3;
            @Override
            public void onTick(long l) {

                slot3.setImageResource(imgResources[k]);
                if(k>=3){
                    k=0;
                }else{
                    k++;
                }
            }
            @Override
            public void onFinish() {
                startTimer();
            }
        }.start();
    }
    private void startTimer() {
        trigger.setEnabled(false);
        if(timer!=null){
            timer.cancel();
        }
        min=0;
        sec=0;
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(timeRemaining,"alpha",0f,1f);
        fadeIn.setDuration(500);
        fadeIn.start();
        timeRemaining.setVisibility(View.VISIBLE);
        timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {

                min=(int)l/1000/60;
                sec=((int)l/1000)%60;
                timeRemaining.setText((min/10!=0?min:"0"+min)+" : "+(sec/10!=0?sec:"0"+sec));
            }
            @Override
            public void onFinish() {
                timeRemaining.setText("00 : 00");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timeRemaining.setVisibility(View.GONE);
                    }
                },1000);
                trigger.setEnabled(true);
            }
        }.start();
    }
}
