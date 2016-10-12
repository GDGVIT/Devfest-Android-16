package com.gdgvitvellore.devfest.Entity.Actors;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;


public class FAQQuestion implements ParentListItem {

    private List<String> answerList = new ArrayList<>();

    public FAQQuestion(String FAQAnswer) {
        answerList.add(FAQAnswer) ;
    }

    @Override
    public List<?> getChildItemList() {
        return answerList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

}
