package com.gdgvitvellore.devfest.Entity.Actors;

import io.realm.RealmObject;

public class Faq extends RealmObject{

    private String question;
    private String answer;

    public Faq() {
    }

    public Faq(String question) {
        this.question = question;
    }

    public Faq(String question, String answer) {
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
}
