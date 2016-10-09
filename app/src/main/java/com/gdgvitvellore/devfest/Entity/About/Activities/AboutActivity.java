package com.gdgvitvellore.devfest.Entity.About.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.gdgvitvellore.devfest.Boundary.Customs.ExpandableListAdapter;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AravindRaj on 09-10-2016.
 */

public class AboutActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        expListView = (ExpandableListView) findViewById(R.id.expanded_about_menu);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Guidelines");
        listDataHeader.add("Contact");
        listDataHeader.add("About us");

        // Adding child data
        List<String> guidelines = new ArrayList<String>();
        guidelines.add("Guidelines 1");
        guidelines.add("Guidelines 2");
        guidelines.add("Guidelines 3");

        List<String> contact = new ArrayList<String>();
        contact.add("Contact 1");
        contact.add("Contact 2");
        contact.add("Contact 3");

        List<String> about = new ArrayList<String>();
        about.add("About 1");
        about.add("About 1");
        about.add("About 1");

        listDataChild.put(listDataHeader.get(0), guidelines);
        listDataChild.put(listDataHeader.get(1), contact);
        listDataChild.put(listDataHeader.get(2), about);
    }

}
