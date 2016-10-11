package com.gdgvitvellore.devfest.Entity.Timeline.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgvitvellore.devfest.gdgdevfest.R;

/**
 * Created by AravindRaj on 10-10-2016.
 */

public class TimelineDisplayFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timeline_display,container, false);

        return view;
    }
}
