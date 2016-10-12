package com.gdgvitvellore.devfest.Entity.MyTeam.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.gdgvitvellore.devfest.Entity.Actors.APIAssigned;
import com.gdgvitvellore.devfest.Entity.Actors.Member;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shuvam Ghosh on 10/11/2016.
 */

public class MyTeamFragment extends Fragment {


    private RecyclerView recyclerView;
    private ArrayList<APIAssigned> apisList;
    private ArrayList<Member> memberList;
    private LinearLayoutManager layoutManager;

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

        layoutManager = new LinearLayoutManager(rootView.getContext());
    }

    private void setInit() {
        recyclerView.setLayoutManager(layoutManager);
        apisList = new ArrayList<>();
        memberList = new ArrayList<>();
    }

    private void setData() {
        //This is just the sample data
        //TODO API integration needs to be done

        Member member1 = new Member();
        member1.setName("Prince");
        Member member2 = new Member();
        member2.setName("Bansal");
        memberList.add(member1);
        memberList.add(member2);

        APIAssigned apiAssigned=new APIAssigned();
        apiAssigned.setName("Github");
        APIAssigned apiAssigned2=new APIAssigned();
        apiAssigned.setName("Mongo");
        apisList.add(apiAssigned);
        apisList.add(apiAssigned2);

        Group taco = new Group(memberList);
        taco.setName("My Team");
        Group quesadilla = new Group(apisList);
        quesadilla.setName("Assigned APIs");
        List<Group> groups = Arrays.asList(taco, quesadilla);
        MyAdapter adapter = new MyAdapter(getContext(), groups);
        recyclerView.setAdapter(adapter);
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

        // onBind ...


        @Override
        public void onBindParentViewHolder(GroupViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
            Group group = (Group) parentListItem;
            parentViewHolder.bind(group);
        }

        @Override
        public void onBindChildViewHolder(ItemViewHolder childViewHolder, int position, Object childListItem) {
            Group group = (Group) childListItem;
            childViewHolder.bind(group);
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
                if (item instanceof APIAssigned)
                    mItemTextView.setText(((APIAssigned)item).getName());
                else if(item instanceof Member){
                    mItemTextView.setText(((Member)item).getName());
                }
            }
        }

    }
}

