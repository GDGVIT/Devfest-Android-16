package com.gdgvitvellore.devfest.Entity.MyTeam.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdgvitvellore.devfest.Entity.Actors.APIAssigned;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;

/**
 * Created by Shuvam Ghosh on 10/11/2016.
 */

public class MyTeam extends Fragment{


    private RecyclerView apiRecView;
    private RecyclerViewAdapter adapter;
    private ArrayList<APIAssigned> apis;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_myteam,container,false);
        initRootView(rootView);
        return rootView;
    }

    private void initRootView(View rootView) {

        apiRecView=(RecyclerView)rootView.findViewById(R.id.apisRecView);
        LinearLayoutManager manager = new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.HORIZONTAL,false);
        apiRecView.setLayoutManager(manager);

        apis = new ArrayList<APIAssigned>();


        //These data comes from the slot machine

        APIAssigned api1 = new APIAssigned();
        api1.setName("Facebook");
        apis.add(api1);

        APIAssigned api2 = new APIAssigned();
        api2.setName("Google");
        apis.add(api2);

        APIAssigned api3 = new APIAssigned();
        api3.setName("Geethub");
        apis.add(api3);

        adapter = new RecyclerViewAdapter(apis);
        adapter = new RecyclerViewAdapter(apis);
        apiRecView.setAdapter(adapter);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<MyView> {

        ArrayList<APIAssigned> apis;

        RecyclerViewAdapter(ArrayList<APIAssigned> apis)
        {
            this.apis = apis;
        }


        @Override
        public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
            View root;
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_assigned_api_item,parent,false);
            MyView myView = new MyView(root);
            return myView;
        }

        @Override
        public void onBindViewHolder(MyView holder, int position) {

            holder.apiName.setText(apis.get(position).getName());
        }


        @Override
        public int getItemCount() {
            return 3;
        }
    }

    private class MyView extends RecyclerView.ViewHolder{

        private TextView apiName;
        public MyView(View itemView) {
            super(itemView);
            apiName = (TextView)itemView.findViewById(R.id.apiName);
        }
    }
}
