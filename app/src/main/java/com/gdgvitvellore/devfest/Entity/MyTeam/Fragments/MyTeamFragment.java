package com.gdgvitvellore.devfest.Entity.MyTeam.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.Utils.ViewUtils;
import com.gdgvitvellore.devfest.Entity.Actors.API;
import com.gdgvitvellore.devfest.Entity.Actors.APIAssignedResult;
import com.gdgvitvellore.devfest.Entity.Actors.Member;
import com.gdgvitvellore.devfest.Entity.Actors.Team;
import com.gdgvitvellore.devfest.Entity.Actors.User;
import com.gdgvitvellore.devfest.Entity.Authentication.Activities.AuthenticationActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuvam Ghosh on 10/11/2016.
 */

public class MyTeamFragment extends Fragment implements ConnectAPI.ServerAuthenticateListener,ViewUtils {

    private RecyclerView recyclerView;
    private TextView userName,regNo,teamName;
    private CoordinatorLayout root;

    private ArrayList<API> apisList;
    private ArrayList<Member> memberList;
    private LinearLayoutManager layoutManager;
    private List<Group> groups;
    private MyAdapter adapter;

    private ConnectAPI connectAPI;
    private Team team;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_myteam,container,false);
        init(rootView);
        setInit();
        setData();
        return rootView;
    }

    private void init(View rootView) {
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        userName=(TextView)rootView.findViewById(R.id.name);
        regNo=(TextView)rootView.findViewById(R.id.regno);
        teamName=(TextView)rootView.findViewById(R.id.team_name);
        root=(CoordinatorLayout)rootView.findViewById(R.id.root);

        layoutManager = new LinearLayoutManager(rootView.getContext());
        groups=new ArrayList<>();

        connectAPI=new ConnectAPI(getContext());
    }

    private void setInit() {
        recyclerView.setLayoutManager(layoutManager);
        apisList = new ArrayList<>();
        memberList = new ArrayList<>();

        Group myTeam=new Group(memberList);
        myTeam.setName("My Team");
        Group apis=new Group(apisList);
        apis.setName("Assigned APIs");
        groups.add(apis);
        groups.add(myTeam);

        adapter=new MyAdapter(getContext(),groups);
        recyclerView.setAdapter(adapter);

        connectAPI.setServerAuthenticateListener(this);
    }

    private void setData() {

        user=DataHandler.getInstance(getContext()).getUser();
        team=DataHandler.getInstance(getContext()).getTeam();
        if(user!=null&&team!=null){
            userName.setText(user.getName());
            regNo.setText(user.getRegistrationNumber());
            teamName.setText(team.getName());
            groups.get(1).setChildItemList(team.getMembers());
            adapter.notifyDataSetChanged();
            setAPIData(user.getEmail(),user.getAuthToken());
        }else{
            startActivity(new Intent(getActivity(), AuthenticationActivity.class));
            getActivity().finish();
        }
    }

    private void setAPIData(String email, String authToken) {
        List<API> apiAssignedList=DataHandler.getInstance(getContext()).getAssignedApis();
        if(apiAssignedList!=null&&apiAssignedList.size()>0){
            groups.get(0).setChildItemList(apiAssignedList);
            adapter.notifyDataSetChanged();
        }else{
            connectAPI.apis(email,authToken);
        }
    }

    @Override
    public void onRequestInitiated(int code) {
        if(code==ConnectAPI.APIS_CODE){
            showMessage("Updating APIs");
        }
    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        if(code==ConnectAPI.APIS_CODE){
            APIAssignedResult apiAssignedResult=(APIAssignedResult)result;

            if(apiAssignedResult!=null){
                if(apiAssignedResult.getStatus()== ErrorDefinitions.CODE_SUCCESS){
                    DataHandler.getInstance(getContext()).saveApi(apiAssignedResult.getApis());
                    setAPIData(user.getEmail(), user.getAuthToken());
                }else if(apiAssignedResult.getStatus()== ErrorDefinitions.API_NOT_ASSIGNED){
                    showMessage(ErrorDefinitions.getMessage(ErrorDefinitions.API_NOT_ASSIGNED));
                }
            }
        }
    }

    @Override
    public void onRequestError(int code, String message) {
        showMessage(message);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(root,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message, boolean showAction) {

    }

    @Override
    public void showErrorDialog() {

    }


    /**
     * These classes are just sample classes
     * TODO Please make new models in Actors or either as inner calss and inflate the data
     */
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
            mItems=members;
        }
    }


    public class MyAdapter extends ExpandableRecyclerAdapter<MyAdapter.GroupViewHolder,MyAdapter.ItemViewHolder> {

        private LayoutInflater mInflator;

        public MyAdapter(Context context, @NonNull List<Group> parentItemList) {
            super(parentItemList);
            mInflator = LayoutInflater.from(context);
        }

        // onCreate ...


        @Override
        public GroupViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View groupView = mInflator.inflate(R.layout.fragment_myteam_group, parentViewGroup, false);
            return new GroupViewHolder(groupView);
        }

        @Override
        public ItemViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View itemView = mInflator.inflate(R.layout.fragment_myteam_group_item, childViewGroup, false);
            return new ItemViewHolder(itemView);
        }


        @Override
        public void onBindParentViewHolder(GroupViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
            Group group = (Group) parentListItem;
            parentViewHolder.bind(group);
        }

        @Override
        public void onBindChildViewHolder(ItemViewHolder childViewHolder, int position, Object childListItem) {
            childViewHolder.bind(childListItem);
        }

        public class GroupViewHolder extends ParentViewHolder {

            private TextView mGroupTextView;

            public GroupViewHolder(View itemView) {
                super(itemView);
                mGroupTextView = (TextView)itemView.findViewById(R.id.about_list_item_content);
            }

            public void bind(Group group) {
                mGroupTextView.setText(group.getName());
            }
        }

        public class ItemViewHolder extends ChildViewHolder {

            private TextView mItemTextView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mItemTextView = (TextView)itemView.findViewById(R.id.about_list_item_header);
            }

            public void bind(Object item) {
                if (item instanceof API)
                    mItemTextView.setText(((API)item).getName());
                else if(item instanceof Member){
                    mItemTextView.setText(((Member)item).getName());
                }
            }
        }

    }
}

