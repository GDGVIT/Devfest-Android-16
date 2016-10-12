package com.gdgvitvellore.devfest.Control.ViewHolder;


import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gdgvitvellore.devfest.Entity.Actors.FAQ;
import com.gdgvitvellore.devfest.gdgdevfest.R;

public class FAQQuestionViewHolder extends ParentViewHolder{

    private TextView tvQuestion ;

    public FAQQuestionViewHolder(View itemView) {
        super(itemView);
        tvQuestion = (TextView) itemView.findViewById(R.id.card_list_parent_question_title) ;
    }

    public void bind(FAQ faqEntity) {
        tvQuestion.setText(faqEntity.getQuestion());
    }


}
