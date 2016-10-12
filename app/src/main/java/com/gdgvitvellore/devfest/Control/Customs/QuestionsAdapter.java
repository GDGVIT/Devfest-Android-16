package com.gdgvitvellore.devfest.Control.Customs;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gdgvitvellore.devfest.Entity.Actors.FAQ;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;


public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.parentViewHolder>{

    private static final String TAG = "TAG";
    private ArrayList<FAQ> questionArrayList ;

    public QuestionsAdapter(ArrayList<FAQ> questionArrayList) {
        this.questionArrayList = questionArrayList;
    }

    class parentViewHolder extends RecyclerView.ViewHolder{

        private TextView tvQuestionTitle ;
        private ImageButton ibQuestionAnswer ;

        parentViewHolder(View itemView) {
            super(itemView);
            tvQuestionTitle = (TextView) itemView.findViewById(R.id.card_list_parent_question_title) ;
            ibQuestionAnswer = (ImageButton) itemView.findViewById(R.id.card_list_parent_question_answer) ;
        }
    }

    @Override
    public parentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_faq_general_parent, parent, false);

        return new parentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(parentViewHolder holder, final int position) {
        final FAQ question = questionArrayList.get(position);
        holder.tvQuestionTitle.setText(question.getQuestion());
        holder.ibQuestionAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: " + position);
                Log.i(TAG, "onClick: " + question.getAnswer());
            }
        });

    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }
}
