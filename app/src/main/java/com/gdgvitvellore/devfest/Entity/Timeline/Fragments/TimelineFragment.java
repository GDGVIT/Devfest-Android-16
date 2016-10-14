package com.gdgvitvellore.devfest.Entity.Timeline.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.Utils.ViewUtils;
import com.gdgvitvellore.devfest.Entity.Actors.Phase;
import com.gdgvitvellore.devfest.Entity.Actors.TimelineResult;
import com.gdgvitvellore.devfest.Entity.Customs.EmptyFragment;
import com.gdgvitvellore.devfest.Entity.Customs.VerticalPageTransformer;
import com.gdgvitvellore.devfest.Entity.Customs.VerticalViewPager;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Prince Bansal Local on 10/10/2016.
 */

public class TimelineFragment extends Fragment implements ConnectAPI.ServerAuthenticateListener,ViewUtils {

    private static final int NUM_PAGES = 2;
    private static final String TAG = TimelineFragment.class.getSimpleName();

    private VerticalViewPager viewPager;
    private RecyclerView recyclerView;
    private TextView timer;
    private ProgressDialog progressDialog;
    private LinearLayout root;

    private PhasesAdapter mAdapter;
    private List<Phase> phaseList = new ArrayList<>();

    private Handler customHandler = new Handler();
    private int hoursToGo = 24;
    private int minutesToGo = 0;
    private int secondsToGo = 0;
    private ImageView indicator1;
    private ImageView indicator2;

    private int millisToGo = secondsToGo * 1000 + minutesToGo * 1000 * 60 + hoursToGo * 1000 * 60 * 60;

    private ConnectAPI connectAPI;

    private String email, auth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getCredentials();
        setInit();
        setData();
    }

    private void getCredentials() {
        email = DataHandler.getInstance(getActivity()).getUser().getEmail();
        Log.d("EMAIL", email);
        auth = DataHandler.getInstance(getActivity()).getUser().getAuthToken();
        Log.d("PASSWORD", auth);
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.phases_list);
        timer = (TextView) view.findViewById(R.id.time);
        viewPager = (VerticalViewPager) view.findViewById(R.id.pager);
        progressDialog = new ProgressDialog(getContext());
        root=(LinearLayout)view.findViewById(R.id.root);
        indicator1 = (ImageView)view.findViewById(R.id.indicator1);
        indicator2 = (ImageView)view.findViewById(R.id.indicator2);
        connectAPI = new ConnectAPI(getActivity());
    }

    private void setInit() {
        connectAPI.setServerAuthenticateListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        viewPager.setPageTransformer(false, new VerticalPageTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                    if(position==0)
                    {
                        indicator1.setImageResource(R.drawable.white_indicator_circle);
                        indicator2.setImageResource(R.drawable.white_alpha_indicator_circle);
                    }
                    else if(position==1)
                    {
                        indicator1.setImageResource(R.drawable.white_alpha_indicator_circle);
                        indicator2.setImageResource(R.drawable.white_indicator_circle);
                    }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        startTimer();
    }

    private void setData() {
        setupViewPager(viewPager);

        List<Phase> phases = DataHandler.getInstance(getContext()).getPhases();
        if (phases != null) {
            Log.i(TAG, "setData: ");
            phaseList = phases;
            mAdapter = new PhasesAdapter(phaseList);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            connectAPI.timeline(DataHandler.getInstance(getContext()).getUser().getEmail(),
                    DataHandler.getInstance(getContext()).getUser().getAuthToken());
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
      //  pagerIndicator.setViewPager(viewPager);
    }

    private void startTimer() {
        new CountDownTimer(millisToGo, 1000) {

            @Override
            public void onTick(long millis) {
                int seconds = (int) (millis / 1000) % 60;
                int minutes = (int) ((millis / (1000 * 60)) % 60);
                int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
                String text = String.format("%02d H, %02d M, %02d S", hours, minutes, seconds);
                timer.setText(text);
            }

            @Override
            public void onFinish() {

                //Start post Hackathon activity

            }
        };
    }

    @Override
    public void onRequestInitiated(int code) {
        if (code == ConnectAPI.TIMELINE_CODE) {
            progressDialog.setMessage("Loading timeline...");
            progressDialog.show();
        }
    }

    @Override
    public void onRequestCompleted(int code, Object result) {

        progressDialog.cancel();
        if (code == ConnectAPI.TIMELINE_CODE) {
            TimelineResult timelineResult = (TimelineResult) result;
            if (timelineResult != null) {
                if (timelineResult.getStatus() == ErrorDefinitions.CODE_SUCCESS) {
                    DataHandler.getInstance(getActivity()).saveTimeline(timelineResult.getTimeline());
                    Log.d("Realm result:", DataHandler.getInstance(getActivity()).getPhases().toString());
                    setData();
                }else{
                    showMessage(timelineResult.getMessage());
                }
            }
        }

    }

    @Override
    public void onRequestError(int code, String message) {
        progressDialog.cancel();
        if (code == ConnectAPI.TIMELINE_CODE) {
            showMessage(message);
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(root,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDialog() {

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TimelineDisplayFragment();
            } else if (position == 1) {
                return new TimelineAboutFragment();
            } else return new EmptyFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    public class PhasesAdapter extends RecyclerView.Adapter<PhasesAdapter.MyViewHolder> {

        private List<Phase> phasesList;
        private Phase nowPhase;

        public PhasesAdapter(List<Phase> phasesList) {
            this.phasesList = phasesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_timeline_card, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Phase phase = phasesList.get(position);
            holder.name.setText(phase.getTitle());
            holder.time.setText(phase.getStartTime() + "-" + phase.getEndTime());
            if (position == 0) {
                holder.time.setActivated(true);
            }
            /*if(phase.isRunning()){
                holder.time.setActivated(true);
                if(nowPhase!=null)
                nowPhase.setRunning(false);
                nowPhase=phase;
            }*/
        }


        @Override
        public int getItemCount() {
            return phasesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView name, time;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.phase_name);
                time = (TextView) view.findViewById(R.id.phase_time);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //phaseList.get(getAdapterPosition()).setRunning(true);
                //notifyDataSetChanged();
            }
        }


    }

}


