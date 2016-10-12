package com.gdgvitvellore.devfest.Entity.About.Fragments;

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
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prince Bansal Local on 10/11/2016.
 */

public class AboutFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        init(rootView);
        setInit();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
    }



    private void init(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    private void setInit() {
        //Set data for ExpandableListView in About page
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setData() {
        //This is just the sample data
        //TODO API integration needs to be done
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
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * These classes are just sample classes
     * TODO Please make new models in Actors or either as inner calss and inflate the data
     */
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
            View recipeView = mInflator.inflate(R.layout.fragment_about_group, parentViewGroup, false);
            return new RecipeViewHolder(recipeView);
        }

        @Override
        public IngredientViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View ingredientView = mInflator.inflate(R.layout.fragment_about_group_item, childViewGroup, false);
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
