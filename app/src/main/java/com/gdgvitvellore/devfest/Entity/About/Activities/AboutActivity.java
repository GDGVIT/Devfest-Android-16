package com.gdgvitvellore.devfest.Entity.About.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

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

        init();
        setInit();
        setData();

    }

    private void init() {
        expListView = (ExpandableListView) findViewById(R.id.expanded_about_menu);
    }

    private void setInit() {
        //Set data for ExpandableListView in About page
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private void setData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Title data for all section
        listDataHeader.add("Guidelines");
        listDataHeader.add("Contact");
        listDataHeader.add("About us");

        // Adding child data for Guidelines
        List<String> guidelines = new ArrayList<String>();
        guidelines.add("Guidelines 1");
        guidelines.add("Guidelines 2");
        guidelines.add("Guidelines 3");

        // Adding child data for Contact
        List<String> contact = new ArrayList<String>();
        contact.add("Contact 1");
        contact.add("Contact 2");
        contact.add("Contact 3");

        // Adding child data for About
        List<String> about = new ArrayList<String>();
        about.add("About 1");
        about.add("About 1");
        about.add("About 1");

        //Pass title and content as Parameters
        listDataChild.put(listDataHeader.get(0), guidelines);
        listDataChild.put(listDataHeader.get(1), contact);
        listDataChild.put(listDataHeader.get(2), about);
    }

}

class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_about_row_content, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.about_list_item_content);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_about_list_row_title, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.about_list_item_header);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

