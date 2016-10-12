package com.gdgvitvellore.devfest.Entity.Actors;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

public class FAQ extends RealmObject implements ParentListItem{

    private String question;
    private String answer;

    public FAQ() {
    }

    public FAQ(String question) {
        this.question = question;
    }

    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public List<?> getChildItemList() {
        List<String> list = new ArrayList<>();
        list.add(getAnswer()) ;
        return list ;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
