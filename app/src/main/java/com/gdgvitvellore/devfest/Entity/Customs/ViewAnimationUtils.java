package com.gdgvitvellore.devfest.Entity.Customs;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewAnimationUtils {

    private static final String TAG = ViewAnimationUtils.class.getSimpleName();

    public static void expand(final LinearLayout drawerRecycler, final View v) {
        //final int inheight=v.getMeasuredHeight();
        //Log.i(TAG, "expand:inheight: "+inheight);
        drawerRecycler.setVisibility(View.VISIBLE);

        drawerRecycler.measure(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        final int targtetHeight = drawerRecycler.getMeasuredHeight();
        Log.i(TAG, "expand: "+targtetHeight);
        //Log.i(TAG, "expand: tarheigt"+targtetHeight);
        drawerRecycler.getLayoutParams().height = 0;
        final float initx=0;
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                drawerRecycler.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targtetHeight * interpolatedTime);
                //Log.i(TAG, "applyTransformation: height"+v.getLayoutParams().height);
                drawerRecycler.setAlpha(interpolatedTime);
                drawerRecycler.setTranslationX(initx-40*(1-interpolatedTime));
                //titleIcon.setAlpha(interpolatedTime);
                //titleIcon.setTranslationY(-100+interpolatedTime*100);
                //title.setTranslationY(interpolatedTime*100);

                //title.setAlpha(1-interpolatedTime);
                drawerRecycler.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //titleIcon.setVisibility(View.VISIBLE);
                //title.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                drawerRecycler.setVisibility(View.VISIBLE);
                //title.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final LinearLayout drawerRecycler, final View v) {
        final int initialHeight = drawerRecycler.getMeasuredHeight();
        final int finalheight=0;

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    drawerRecycler.setVisibility(View.GONE);
                }else{
                    drawerRecycler.getLayoutParams().height = (initialHeight - (int)(initialHeight * interpolatedTime));
                    drawerRecycler.setAlpha(1-interpolatedTime);
                    //titleIcon.setAlpha(1-interpolatedTime);
                    //titleIcon.setTranslationY(-interpolatedTime*100);
                    //title.setTranslationY(100-interpolatedTime*100);

                    //title.setAlpha(interpolatedTime);
                    drawerRecycler.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //titleIcon.setVisibility(View.VISIBLE);
                //title.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //titleIcon.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    /*public static void expandOrCollapse(final RecyclerView drawerRecycler, final View v, String exp_or_colpse) {
        ScaleAnimation anim = null;
        if(exp_or_colpse.equals("expand"))
        {
            anim = new TranslateAnimation(0,0,v.getHeight(), v.getHeight()+drawerRecycler.getHeight());
            drawerRecycler.setVisibility(View.VISIBLE);
        }
        else{
            anim = new ScaleAnimation(v.getWidth(),v.getWidth(), v.getHeight()+drawerRecycler.getHeight(), v.getHeight());
            Animation.AnimationListener collapselistener= new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    drawerRecycler.setVisibility(View.GONE);
                }
            };

            anim.setAnimationListener(collapselistener);
        }

        // To Collapse
        //

        anim.setDuration(300);
        v.startAnimation(anim);
    }
    */
}