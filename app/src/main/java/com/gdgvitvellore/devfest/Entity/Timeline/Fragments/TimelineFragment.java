package com.gdgvitvellore.devfest.Entity.Timeline.Fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdgvitvellore.devfest.Control.Customs.PhasesAdapter;
import com.gdgvitvellore.devfest.Entity.Actors.Phase;
import com.gdgvitvellore.devfest.Entity.Customs.VerticalViewPager;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Prince Bansal Local on 10/10/2016.
 */

public class TimelineFragment extends Fragment {

    ViewPager viewPager;
    private List<Phase> phaseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhasesAdapter mAdapter;

    TextView timer;

    private Handler customHandler = new Handler();
    int hoursToGo = 24;
    int minutesToGo = 0;
    int secondsToGo = 0;

    int millisToGo = secondsToGo*1000+minutesToGo*1000*60+hoursToGo*1000*60*60;
    private static final String FORMAT = "%02d:%02d:%02d";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline,container,false);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        setupViewPager(viewPager);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initRecycler();
        setData();
        startTimer();
    }

    private void startTimer() {
        new CountDownTimer(86400000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timer.setText("Closed!");
            }
        }.start();
 }


    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.phases_list);
        timer = (TextView) view.findViewById(R.id.time);
    }

    private void setData() {
        Phase phase = new Phase("Hackathon Phase 1", "10:00 - 12:30");
        phaseList.add(phase);

        phase = new Phase("Hackathon Phase 2", "12:30 - 15:00");
        phaseList.add(phase);

        phase = new Phase("Hackathon Phase 3", "15:00 - 17:00");
        phaseList.add(phase);

        phase = new Phase("Hackathon Phase 1", "10:00 - 12:30");
        phaseList.add(phase);

        phase = new Phase("Hackathon Phase 2", "12:30 - 15:00");
        phaseList.add(phase);

        phase = new Phase("Hackathon Phase 3", "15:00 - 17:00");
        phaseList.add(phase);

        mAdapter.notifyDataSetChanged();
    }

    private void initRecycler() {
        mAdapter = new PhasesAdapter(phaseList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TimelineDisplayFragment(), "IMAGE");
        adapter.addFragment(new TimelineAboutFragment(), "ABOUT");
        viewPager.setAdapter(adapter);
    }

}

class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
    }
}