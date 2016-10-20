package com.gdgvitvellore.devfest.Entity.Timeline.Fragments;


import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Algorithms.TimelineAlgos;
import com.gdgvitvellore.devfest.Control.Animations.Timeline.ObjectAnimations;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.Utils.ViewUtils;
import com.gdgvitvellore.devfest.Entity.Actors.Phase;
import com.gdgvitvellore.devfest.Entity.Actors.TimelineResult;
import com.gdgvitvellore.devfest.Entity.Customs.EmptyFragment;
import com.gdgvitvellore.devfest.Entity.Customs.VerticalPageTransformer;
import com.gdgvitvellore.devfest.Entity.Customs.VerticalViewPager;
import com.gdgvitvellore.devfest.Entity.Main.Activities.MainActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Prince Bansal Local on 10/10/2016.
 */

public class TimelineFragment extends Fragment implements ConnectAPI.ServerAuthenticateListener, ViewUtils, ViewPager.OnPageChangeListener {

    private static final int NUM_PAGES = 2;
    private static final String TAG = TimelineFragment.class.getSimpleName();

    private VerticalViewPager phaseDisplayPager;
    private RecyclerView recyclerView;
    private TextView timer, title;
    private ProgressDialog progressDialog;
    private ImageView indicator1;
    private ImageView indicator2;
    private LinearLayout root;

    private TimelineDisplayFragment timelineDisplayFragment;
    private TimelineAboutFragment timelineAboutFragment;

    private PhasesAdapter mAdapter;
    private ViewPagerAdapter phasePagerAdapter;

    private List<Phase> phaseList = new ArrayList<>();
    private Handler customHandler = new Handler();
    private int hoursToGo = 24;
    private int minutesToGo = 0;
    private int secondsToGo = 0;

    private int millisToGo = secondsToGo * 1000 + minutesToGo * 1000 * 60 + hoursToGo * 1000 * 60 * 60;

    private ConnectAPI connectAPI;

    private String email, auth;
    private Phase currentPhase;
    private int currentPhasePosition = -1;


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
        setInit();
        setData();
    }


    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.phases_list);
        timer = (TextView) view.findViewById(R.id.time);
        title = (TextView) view.findViewById(R.id.title);
        progressDialog = new ProgressDialog(getContext());
        root = (LinearLayout) view.findViewById(R.id.root);
        phaseDisplayPager = (VerticalViewPager) view.findViewById(R.id.pager);

        timelineDisplayFragment = TimelineDisplayFragment.getNewInstance();
        timelineAboutFragment = TimelineAboutFragment.getNewInstance();

        indicator1 = (ImageView) view.findViewById(R.id.indicator1);
        indicator2 = (ImageView) view.findViewById(R.id.indicator2);
        connectAPI = new ConnectAPI(getActivity());

        phasePagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        if (!MainActivity.ISGUEST) {
            email = DataHandler.getInstance(getActivity()).getUser().getEmail();
            Log.d("EMAIL", email);
            auth = DataHandler.getInstance(getActivity()).getUser().getAuthToken();
            Log.d("AUTH", auth);
        }
    }

    private void setInit() {
        connectAPI.setServerAuthenticateListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.getItemAnimator().setChangeDuration(0);

        phaseDisplayPager.setPageTransformer(false, new VerticalPageTransformer());
        phaseDisplayPager.addOnPageChangeListener(this);
    }

    private void setData() {

        phaseList = DataHandler.getInstance(getContext()).getPhases();

        if (phaseList != null) {
            refreshPhases();
            mAdapter = new PhasesAdapter(phaseList);
            recyclerView.setAdapter(mAdapter);
            if (currentPhase != null) {
                recyclerView.scrollToPosition(currentPhasePosition);
            } else {
                currentPhase = phaseList.get(0);
            }
            phaseDisplayPager.setAdapter(phasePagerAdapter);
            refreshCurrentEventTitles();
        } else {
            connectAPI.timeline(email, auth, MainActivity.ISGUEST);
        }
    }

    private void refreshPhases() {
        for (int i = 0; i < phaseList.size(); i++) {
            Phase p = phaseList.get(i);
            if (TimelineAlgos.isRunningNow(p.getStartTime(), p.getEndTime())) {
                p.setRunning(true);
                currentPhase = p;
                currentPhasePosition = i;
            } else {
                p.setRunning(false);
            }
        }
    }

    private void refreshPages() {
        timelineDisplayFragment.setImage(currentPhase.getImageUrl());
        timelineAboutFragment.setAboutText(currentPhase.getDescription());
    }


    private void refreshCurrentEventTitles() {
        title.setText(currentPhase.getTitle());
        timer.setText(TimelineAlgos.getTime(currentPhase.getStartTime()) + " - " + TimelineAlgos.getTime(currentPhase.getEndTime()));
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
                } else {
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
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message, boolean showAction) {

    }

    @Override
    public void showErrorDialog() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.i(TAG, "onPageSelected: " + position);
        if (position == 0) {
            indicator1.setImageResource(R.drawable.white_indicator_circle);
            indicator2.setImageResource(R.drawable.white_alpha_indicator_circle);
        } else if (position == 1) {
            indicator1.setImageResource(R.drawable.white_alpha_indicator_circle);
            indicator2.setImageResource(R.drawable.white_indicator_circle);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (currentPhase != null) {
                    timelineDisplayFragment.setImageUrl(currentPhase.getImageUrl());
                }
                return timelineDisplayFragment;
            } else if (position == 1) {
                if (currentPhase != null) {
                    timelineAboutFragment.setAboutText(currentPhase.getDescription());
                }
                return timelineAboutFragment;
            } else return new EmptyFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    public class PhasesAdapter extends RecyclerView.Adapter<PhasesAdapter.MyViewHolder> {
        private List<Phase> phasesList;
        private SparseBooleanArray selectedItems;
        private View lastViewClicked = null;
        private int lastSelected=0;
        private int lastDeSelected=-1;

        public PhasesAdapter(List<Phase> phasesList) {
            this.phasesList = phasesList;
            selectedItems=new SparseBooleanArray();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_timeline_recycler_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Phase phase = phasesList.get(position);
            holder.name.setText(phase.getTitle());
            holder.time.setText(TimelineAlgos.getTime(phase.getStartTime()) + "-" + TimelineAlgos.getTime(phase.getEndTime()));

            if (phase.isRunning()) {
                Log.i(TAG, "onBindViewHolder: " + phase.getTitle());
                holder.time.setActivated(true);
                holder.time.setText("Now");
                if(lastSelected==-1){
                    lastSelected=position;
                }
            } else {
                holder.time.setActivated(false);
            }
            ObjectAnimations.reset(holder.holder).start();
            if(position==lastSelected){
                ObjectAnimations.scaleUp(holder.holder).start();
                Log.i(TAG, "onBindViewHolder: scaleUP:pos:"+position+":sel:"+lastSelected+":des:"+lastDeSelected);
            }else if(position==lastDeSelected){
                Log.i(TAG, "onBindViewHolder: scaleDown:pos:"+position+":sel:"+lastSelected+":des:"+lastDeSelected);
                ObjectAnimations.scaleDown(holder.holder).start();
            }

        }

        @Override
        public int getItemCount() {
            return phasesList.size();
        }



        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView name, time;
            public CardView holder;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.phase_name);
                time = (TextView) view.findViewById(R.id.phase_time);
                holder =(CardView)view.findViewById(R.id.holder);
                holder.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                phaseDisplayPager.setCurrentItem(0);
                currentPhase = phaseList.get(getAdapterPosition());
                refreshCurrentEventTitles();
                refreshPages();
                if(lastSelected==getAdapterPosition()&&lastDeSelected==-2){
                    lastDeSelected=getAdapterPosition();
                    lastSelected=-2;
                    notifyItemChanged(lastDeSelected);
                }else if(lastDeSelected==getAdapterPosition()&&lastSelected==-2){
                    lastSelected=getAdapterPosition();
                    lastDeSelected=-2;
                    notifyItemChanged(lastSelected);
                }else{
                    lastDeSelected=lastSelected;
                    lastSelected=getAdapterPosition();
                    notifyItemChanged(lastDeSelected);
                    notifyItemChanged(lastSelected);
                }
            }

        }


    }

}


