package com.gdgvitvellore.devfest.Entity.SlotMachine.Fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bydavy.morpher.DigitalClockView;
import com.bydavy.morpher.font.DFont;
import com.gdgvitvellore.devfest.Control.Animations.SlotMachine.ObjectAnimations;
import com.gdgvitvellore.devfest.Control.Contracts.PrivateContract;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.Random;

/**
 * Created by Shuvam Ghosh on 10/11/2016.
 */

public class SlotMachineFragment extends Fragment implements View.OnTouchListener {

    private static final String TAG = SlotMachineFragment.class.getSimpleName();
    private ImageView trigger;
    private ImageView slot1, slot2, slot3;
    private LinearLayout triggerHolder, arrowLayout;
    private DigitalClockView digitalClockView;
    private int min = 0;
    private int sec = 0;

    PointF downPT = new PointF();
    PointF startPT = new PointF();

    private int imgResources[] = {
            R.drawable.api1,
            R.drawable.api2,
            R.drawable.api3,
            R.drawable.api4,
            R.drawable.api5,
            R.drawable.api6,
            R.drawable.api7,
            R.drawable.api8,
            R.drawable.api9,
            R.drawable.api10,
            R.drawable.api11,
            R.drawable.api12,
            R.drawable.api13,
            R.drawable.api14,
    };
    private CountDownTimer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_slot_machine, container, false);
        init(rootView);
        setInit();
        return rootView;
    }

    private void init(View rootView) {
        trigger = (ImageView) rootView.findViewById(R.id.imageView);
        slot1 = (ImageView) rootView.findViewById(R.id.slot1);
        slot2 = (ImageView) rootView.findViewById(R.id.slot2);
        slot3 = (ImageView) rootView.findViewById(R.id.slot3);
        arrowLayout = (LinearLayout) rootView.findViewById(R.id.arrows_layout);
        triggerHolder = (LinearLayout) rootView.findViewById(R.id.trigger_holder);

        digitalClockView = (DigitalClockView) rootView.findViewById(R.id.digitalClock);

    }


    private void setInit() {
        digitalClockView.setFont(new DFont(70, 2));
        digitalClockView.setMorphingDuration(100);

        ObjectAnimations.triggerArrowAnimator(triggerHolder, 60).start();

        digitalClockView.setVisibility(View.INVISIBLE);

        trigger.setOnTouchListener(this);
    }

    private void animateSlots() {


        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimations.slotTranslateAnimation(slot1, 60),
                ObjectAnimations.slotTranslateAnimation(slot2, 80),
                ObjectAnimations.slotTranslateAnimation(slot3, 100),
                ObjectAnimations.slotTranslateAnimation(slot1, 60),
                ObjectAnimations.slotTranslateAnimation(slot2, 80),
                ObjectAnimations.slotTranslateAnimation(slot3, 100));
        set.start();

        final int p = new Random().nextInt(imgResources.length-1);
        final int q = new Random().nextInt(imgResources.length-1);
        final int r = new Random().nextInt(imgResources.length-1);
        Log.i(TAG, "animateSlots: I:j:k:"+p+":"+q+":"+r);
        CountDownTimer swapTimer = new CountDownTimer(10000, 100) {

            int i=p;
            int j=q;
            int k=r;


            @Override
            public void onTick(long l) {

                if(l>6000&&l<=10000){
                    slot1.setImageResource(imgResources[i]);
                    slot2.setImageResource(imgResources[j]);
                    slot3.setImageResource(imgResources[k]);
                    i=i>=imgResources.length-1?0:i+1;
                    j=j>=imgResources.length-1?0:j+1;
                    k=k>=imgResources.length-1?0:k+1;

                }else if(l<=6000&&l>2000){
                    j=j>=imgResources.length-1?0:j+1;
                    k=k>=imgResources.length-1?0:k+1;
                    slot2.setImageResource(imgResources[j]);
                    slot3.setImageResource(imgResources[k]);

                }else{
                    k=k>=imgResources.length-1?0:k+1;
                    slot3.setImageResource(imgResources[k]);
                }
            }

            @Override
            public void onFinish() {
                startWaitTimer();
                Log.i(TAG, "onFinish: "+i+":"+j+":"+k);
            }
        }.start();
    }

    private void startWaitTimer() {
        trigger.setEnabled(false);
        if (timer != null) {
            timer.cancel();
        }
        min = 0;
        sec = 0;

        ObjectAnimations.fadeInAnimation(digitalClockView).start();

        digitalClockView.setVisibility(View.VISIBLE);

        timer = new CountDownTimer(PrivateContract.SLOT_WAIT_TIME, 1000) {
            @Override
            public void onTick(long l) {

                min = (int) l / 1000 / 60;
                sec = ((int) l / 1000) % 60;
                digitalClockView.setTime((min / 10 != 0 ? min : "0" + min) + ":" + (sec / 10 != 0 ? sec : "0" + sec));
            }

            @Override
            public void onFinish() {
                digitalClockView.setTime("00:00");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimations.fadeOutAnimation(digitalClockView).start();
                    }
                }, 1000);
                trigger.setEnabled(true);
            }
        }.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int eid = event.getAction();
        switch (eid) {
            case MotionEvent.ACTION_MOVE:
                PointF mv = new PointF(event.getX() - downPT.x, event.getY() - downPT.y);
                Rect rec = new Rect();
                Rect rec2 = new Rect();
                triggerHolder.getLocalVisibleRect(rec2);
                trigger.getLocalVisibleRect(rec);
                if (startPT.y + mv.y >= rec2.top && startPT.y + mv.y <= rec2.height() - rec.height()) {
                    trigger.setY((int) (startPT.y + mv.y));
                    startPT = new PointF(trigger.getX(), trigger.getY());
                }
                break;
            case MotionEvent.ACTION_DOWN:

                ObjectAnimations.fadeOutAnimation(arrowLayout).start();
                downPT.x = event.getX();
                downPT.y = event.getY();
                startPT = new PointF(trigger.getX(), trigger.getY());
                break;
            case MotionEvent.ACTION_UP:


                float in = trigger.getY();
                ObjectAnimator animator = ObjectAnimator.ofFloat(trigger, "y", in, 35, 20, 30, 20, 25, 20);
                animator.setDuration(500);
                animator.start();
                animateSlots();
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(arrowLayout, "alpha", 0f, 1f);
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
            default:
                break;
        }

        return true;
    }
}
