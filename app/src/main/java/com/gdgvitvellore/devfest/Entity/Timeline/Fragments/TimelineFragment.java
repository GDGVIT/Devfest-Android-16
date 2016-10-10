package com.gdgvitvellore.devfest.Entity.Timeline.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gdgvitvellore.devfest.Entity.About.Activities.AboutActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prince Bansal Local on 10/10/2016.
 */

public class TimelineFragment extends Fragment {
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private RecyclerView mRecyclerView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.test, container, false);
        init(rootView);
        setInit();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //setData();
        setTestData();
    }



    private void init(View rootView) {
        expListView = (ExpandableListView) rootView.findViewById(R.id.expanded_about_menu);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    private void setInit() {
        //Set data for ExpandableListView in About page
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
    }

    private void setData() {

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

        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private void setTestData() {
        Ingredient beef = new Ingredient("beef");
        Ingredient cheese = new Ingredient("cheese");
        Ingredient salsa = new Ingredient("salsa");
        Ingredient tortilla = new Ingredient("tortilla");

        Recipe taco = new Recipe(Arrays.asList(beef, cheese, salsa, tortilla));
        taco.setName("Taco");
        Recipe quesadilla = new Recipe(Arrays.asList(cheese, tortilla));
        quesadilla.setName("quesadilla");
        Recipe quesadilla1 = new Recipe(Arrays.asList(cheese, tortilla));
        quesadilla1.setName("quesadilla1");
        Recipe quesadilla2 = new Recipe(Arrays.asList(cheese, tortilla));
        quesadilla2.setName("quesadilla2");
        Recipe quesadilla3 = new Recipe(Arrays.asList(cheese, tortilla));
        quesadilla3.setName("quesadilla3");
        Recipe quesadilla4 = new Recipe(Arrays.asList(cheese, tortilla));
        quesadilla4.setName("quesadilla4");
        Recipe quesadilla5 = new Recipe(Arrays.asList(cheese, tortilla));
        quesadilla5.setName("quesadilla5");
        List<Recipe> recipes = Arrays.asList(taco, quesadilla,quesadilla1,quesadilla2,quesadilla3,quesadilla4,quesadilla5);
        MyAdapter adapter = new MyAdapter(getContext(), recipes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
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
    public class Recipe implements ParentListItem {

        // a recipe contains several ingredients
        private List mIngredients;
        private String name;

        public Recipe(List<Ingredient> ingredients) {
            mIngredients = ingredients;
        }


        @Override
        public List<?> getChildItemList() {
            return mIngredients;
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
    public class Ingredient{
        public String name;

        public Ingredient(String beef) {
            name=beef;
        }

        public String getName(){
            return name;
        }
    }
    public class RecipeViewHolder extends ParentViewHolder {

        private TextView mRecipeTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mRecipeTextView = (TextView)itemView.findViewById(R.id.about_list_item_content);
        }

        public void bind(Recipe recipe) {
            mRecipeTextView.setText(recipe.getName());
        }
    }
    public class IngredientViewHolder extends ChildViewHolder {

        private TextView mIngredientTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            mIngredientTextView = (TextView)itemView.findViewById(R.id.about_list_item_header);
        }

        public void bind(Ingredient ingredient) {
            mIngredientTextView.setText(ingredient.getName());
        }
    }
    public class MyAdapter extends ExpandableRecyclerAdapter<RecipeViewHolder, IngredientViewHolder> {

        private LayoutInflater mInflator;

        public MyAdapter(Context context, @NonNull List<Recipe> parentItemList) {
            super(parentItemList);
            mInflator = LayoutInflater.from(context);
        }

        // onCreate ...


        @Override
        public RecipeViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View recipeView = mInflator.inflate(R.layout.activity_about_row_content, parentViewGroup, false);
            return new RecipeViewHolder(recipeView);
        }

        @Override
        public IngredientViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View ingredientView = mInflator.inflate(R.layout.activity_about_list_row_title, childViewGroup, false);
            return new IngredientViewHolder(ingredientView);
        }

        // onBind ...


        @Override
        public void onBindParentViewHolder(RecipeViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
            Recipe recipe = (Recipe) parentListItem;
            parentViewHolder.bind(recipe);
        }

        @Override
        public void onBindChildViewHolder(IngredientViewHolder childViewHolder, int position, Object childListItem) {
            Ingredient ingredient = (Ingredient) childListItem;
            childViewHolder.bind(ingredient);
        }



    }
}
