package com.gdgvitvellore.devfest.Entity.Actors;

/**
 * Created by AravindRaj on 11-10-2016.
 */

public class Phase {

    private String name, time;

    public Phase(){

    }

    public Phase(String name, String time){
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
