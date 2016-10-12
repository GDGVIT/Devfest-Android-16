package com.gdgvitvellore.devfest.Control.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.gdgvitvellore.devfest.gdgdevfest.R;


public class FAQAnswerViewHolder extends ChildViewHolder {

    private TextView tvAnswer ;

    public FAQAnswerViewHolder(View itemView) {
        super(itemView);
        tvAnswer = (TextView) itemView.findViewById(R.id.list_item_faq_general_child_tv_answer) ;

    }

    public void bind(String faqEntity) {
        tvAnswer.setText(faqEntity);
    }
}
