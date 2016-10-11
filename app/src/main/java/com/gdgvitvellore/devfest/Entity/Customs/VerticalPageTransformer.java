package com.gdgvitvellore.devfest.Entity.Customs;

import android.support.v4.view.ViewPager;
import android.view.View;

public class VerticalPageTransformer implements ViewPager.PageTransformer {

    private static final String TAG = "VerticalPageTransformer";

    public VerticalPageTransformer() {

    }

    public void transformPage(View view, float position) {

        //Log.d(TAG, "transformPage() called with: " + "view = [" + view.getId() + "], position = [" + position + "]");
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();


        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            view.setAlpha(1);
            //checkBox.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
            if (position <= 0) {
                view.setTranslationX(-position * pageWidth);
                view.setTranslationY(position * pageHeight);
            } else {
                view.setTranslationX(-position * pageWidth);
                view.setTranslationY(position * pageHeight);
            }

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }

    }
}