package com.gdgvitvellore.devfest.Entity.Timeline.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdgvitvellore.devfest.gdgdevfest.R;

/**
 * Created by AravindRaj on 11-10-2016.
 */

public class TimelineAboutFragment extends Fragment {


    private TextView timelineAbout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timeline_about, container, false);
        timelineAbout = (TextView)view.findViewById(R.id.timeline_about);
        return view;
    }

    public void setTimelineAbout(String text){
        timelineAbout.setText(text);
    }
}
