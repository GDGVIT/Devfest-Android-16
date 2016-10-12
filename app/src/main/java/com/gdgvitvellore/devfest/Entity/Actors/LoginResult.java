package com.gdgvitvellore.devfest.Entity.Actors;

/**
 * Created by Prince Bansal Local on 10/8/2016.
 */

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * @see <a href="https://github.com/GDGVIT/devfest-portal/wiki">API Reference</a>
 */

public class LoginResult extends RealmObject {
    private int status;
    //TODO Ask backend developer to correct the typo
    @SerializedName("messasge")
    private String message;
    private User user;
    private Team team;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
