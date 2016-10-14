package com.gdgvitvellore.devfest.Entity.Actors;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Slot extends RealmObject{

    @SerializedName("winner")
    @Expose
    private String winner;


    /**
     *
     * @return
     * The question
     */
    public String getWinner() {
        return winner;
    }

    /**
     *
     * @param question
     * The question
     */
    public void setWinner(String question) {
        this.winner = winner;
    }


}
