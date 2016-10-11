package com.gdgvitvellore.devfest.Entity.Customs;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shalini on 07-09-2015.
 */
public class VerticalViewPager extends ViewPager {

    private boolean pagingEnabled = false;

    public VerticalViewPager(Context context) {
        super(context);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }


    private MotionEvent swapXY(MotionEvent ev) {

        ev.setLocation(ev.getY() / getHeight() * getWidth(), ev.getX() / getWidth() * getHeight());
        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(swapXY(ev));

    }


}
