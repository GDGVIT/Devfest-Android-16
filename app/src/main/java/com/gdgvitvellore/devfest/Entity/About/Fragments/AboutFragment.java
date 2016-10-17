package com.gdgvitvellore.devfest.Entity.About.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.bumptech.glide.Glide;
import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.DataGenerator.AboutDataGenerator;
import com.gdgvitvellore.devfest.Control.Utils.ViewUtils;
import com.gdgvitvellore.devfest.Entity.Actors.API;
import com.gdgvitvellore.devfest.Entity.Actors.Child;
import com.gdgvitvellore.devfest.Entity.Actors.Member;
import com.gdgvitvellore.devfest.Entity.Actors.Speakers;
import com.gdgvitvellore.devfest.Entity.Actors.SpeakersResult;
import com.gdgvitvellore.devfest.Entity.Actors.User;
import com.gdgvitvellore.devfest.Entity.Authentication.Activities.AuthenticationActivity;
import com.gdgvitvellore.devfest.Entity.Main.Activities.MainActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Prince Bansal Local on 10/11/2016.
 */

public class AboutFragment extends Fragment implements ConnectAPI.ServerAuthenticateListener, ViewUtils {
    private RecyclerView mRecyclerView;
    private RecyclerView speakersRecyclerView;
    private ProgressDialog progressDialog;
    private CoordinatorLayout root;

    private List<Speakers> speakersList;
    private List<Group> groupList;
    private User user;


    private ConnectAPI connectAPI;
    private SpeakersAdapter speakersAdapter;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        init(rootView);
        setInit();
        return rootView;
    }

    private void init(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        speakersRecyclerView = (RecyclerView) rootView.findViewById(R.id.speakers_recycler_view);
        progressDialog = new ProgressDialog(getContext());
        root = (CoordinatorLayout) rootView.findViewById(R.id.root);

        connectAPI = new ConnectAPI(getContext());
        groupList = new ArrayList<>();
        user = DataHandler.getInstance(getContext()).getUser();
        if (!MainActivity.ISGUEST) {
            if (user == null) {
                startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                getActivity().finish();
            }
        }
    }


    private void setInit() {
        //Set data for ExpandableListView in About page
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog.setMessage("Loading...");
        connectAPI.setServerAuthenticateListener(this);
        speakersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setData() {
        setSpeakers();
        setAboutData();
    }

    private void setSpeakers() {
        speakersList = DataHandler.getInstance(getContext()).getSpeakers();
        if (speakersList != null) {
            speakersAdapter = new SpeakersAdapter(getContext());
            speakersRecyclerView.setAdapter(speakersAdapter);
        } else {
            if (!MainActivity.ISGUEST)
                connectAPI.speakers(user.getEmail(), user.getAuthToken(), false);
            else
                connectAPI.speakers(null, null, true);
        }
    }

    private void setAboutData() {

        groupList.add(AboutDataGenerator.getPatrons(this));
        groupList.add(AboutDataGenerator.getSponsors(this));
        groupList.add(AboutDataGenerator.getContacts(this));
        myAdapter = new MyAdapter(getContext(), groupList);
        mRecyclerView.setAdapter(myAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        setData();
    }

    @Override
    public void onRequestInitiated(int code) {
        if (code == ConnectAPI.SPEAKERS_CODE) {
            progressDialog.show();
        }
    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        progressDialog.cancel();
        if (code == ConnectAPI.SPEAKERS_CODE) {
            SpeakersResult speakersResult = (SpeakersResult) result;
            if (speakersResult != null && speakersResult.getStatus() == ErrorDefinitions.CODE_SUCCESS) {
                DataHandler.getInstance(getContext()).saveSpeakers(speakersResult.getSpeakers());
                setSpeakers();
            } else {
                showMessage(speakersResult.getMessage());
            }
        }
    }


    @Override
    public void onRequestError(int code, String message) {
        progressDialog.cancel();
        showMessage(message);
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

    public class Group implements ParentListItem {

        // a recipe contains several ingredients
        private List mItems;
        private String name;

        public Group(List items) {
            mItems = items;
        }


        @Override
        public List<?> getChildItemList() {
            return mItems;
        }

        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setChildItemList(List members) {
            mItems = members;
        }
    }


    public class MyAdapter extends ExpandableRecyclerAdapter<MyAdapter.GroupViewHolder, MyAdapter.ItemViewHolder> {

        private LayoutInflater mInflator;

        public MyAdapter(Context context, @NonNull List<Group> parentItemList) {
            super(parentItemList);
            mInflator = LayoutInflater.from(context);
        }

        @Override
        public MyAdapter.GroupViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View groupView = mInflator.inflate(R.layout.fragment_about_group, parentViewGroup, false);
            return new MyAdapter.GroupViewHolder(groupView);
        }

        @Override
        public MyAdapter.ItemViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View itemView = mInflator.inflate(R.layout.fragment_about_group_item, childViewGroup, false);
            return new MyAdapter.ItemViewHolder(itemView);
        }


        @Override
        public void onBindParentViewHolder(MyAdapter.GroupViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
            Group group = (Group) parentListItem;
            parentViewHolder.bind(group);
        }

        @Override
        public void onBindChildViewHolder(MyAdapter.ItemViewHolder childViewHolder, int position, Object childListItem) {
            childViewHolder.bind(childListItem);
        }

        public class GroupViewHolder extends ParentViewHolder {

            private TextView mGroupTextView;

            public GroupViewHolder(View itemView) {
                super(itemView);
                mGroupTextView = (TextView) itemView.findViewById(R.id.about_list_item_content);
            }

            public void bind(Group group) {
                mGroupTextView.setText(group.getName());
            }
        }

        public class ItemViewHolder extends ChildViewHolder {

            private TextView mItemTextView, desgination;
            private ImageView info;
            private CircleImageView mImageView;
            private View holder;

            public ItemViewHolder(View itemView) {
                super(itemView);
                holder = itemView;
                mImageView = (CircleImageView) itemView.findViewById(R.id.photo);
                info = (ImageView) itemView.findViewById(R.id.info);
                mItemTextView = (TextView) itemView.findViewById(R.id.name);
                desgination = (TextView) itemView.findViewById(R.id.designation);
            }

            public void bind(Object item) {
                final Child child = (Child) item;
                if (child.getImageType() == Child.IMAGE_URL) {
                    desgination.setVisibility(View.VISIBLE);
                    mItemTextView.setText(child.getName());
                    desgination.setText(child.getDesignation());
                    Glide.with(getContext()).load(child.getImageUrl()).asBitmap().into(mImageView);
                    info.setVisibility(View.GONE);
                } else {
                    mItemTextView.setText(child.getName());
                    mImageView.setImageResource(child.getImageResource());
                    desgination.setVisibility(View.GONE);
                    info.setVisibility(View.GONE);
                    holder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (getAdapterPosition() == 4) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(child.getDesignation()));
                                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            } else if(getAdapterPosition()==3){
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(child.getDesignation()));
                                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            } else{
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(child.getDesignation()));
                                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                                    startActivity(intent);
                                }

                            }
                        }
                    });
                }
            }
        }

    }

    public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.SpeakersViewHolder> {


        private LayoutInflater mInflator;

        public SpeakersAdapter(Context context) {
            mInflator = LayoutInflater.from(context);
        }

        @Override
        public SpeakersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflator.inflate(R.layout.fragment_about_speakers_recycler_item, parent, false);
            return new SpeakersViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SpeakersViewHolder holder, int position) {
            Speakers speakers = speakersList.get(position);
            holder.name.setText(speakers.getName());
            holder.designation.setText(speakers.getDesignation());
            Glide.with(getContext()).load(speakersList.get(position).getImageUrl()).asBitmap().into(holder.photo);
        }

        @Override
        public int getItemCount() {
            return speakersList.size();
        }

        public class SpeakersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private CircleImageView photo;
            private TextView name, designation;
            private ImageView info;

            public SpeakersViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                designation = (TextView) itemView.findViewById(R.id.designation);
                info = (ImageView) itemView.findViewById(R.id.info);
                photo = (CircleImageView) itemView.findViewById(R.id.photo);
                info.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }
    }


}
