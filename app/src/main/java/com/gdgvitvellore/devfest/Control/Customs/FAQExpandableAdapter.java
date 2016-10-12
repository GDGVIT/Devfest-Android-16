package com.gdgvitvellore.devfest.Control.Customs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.gdgvitvellore.devfest.Control.ViewHolder.FAQAnswerViewHolder;
import com.gdgvitvellore.devfest.Control.ViewHolder.FAQQuestionViewHolder;
import com.gdgvitvellore.devfest.Entity.Actors.FAQ;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.List;


public class FAQExpandableAdapter extends ExpandableRecyclerAdapter<FAQQuestionViewHolder, FAQAnswerViewHolder> {

    private LayoutInflater layoutInflater ;

    public FAQExpandableAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        layoutInflater = LayoutInflater.from(context) ;
    }

    @Override
    public FAQQuestionViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View questionView = layoutInflater.inflate(R.layout.list_item_faq_general_parent, parentViewGroup, false);
        return new FAQQuestionViewHolder(questionView);
    }

    @Override
    public FAQAnswerViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View answerView = layoutInflater.inflate(R.layout.list_item_faq_general_child, childViewGroup, false);
        return new FAQAnswerViewHolder(answerView);
    }

    @Override
    public void onBindParentViewHolder(FAQQuestionViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        FAQ faq = (FAQ) parentListItem;
        parentViewHolder.bind(faq);
    }

    @Override
    public void onBindChildViewHolder(FAQAnswerViewHolder childViewHolder, int position, Object childListItem) {
        String child = (String) childListItem;
        childViewHolder.bind(child);
    }
}
