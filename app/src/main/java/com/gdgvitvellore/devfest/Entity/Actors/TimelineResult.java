package com.gdgvitvellore.devfest.Entity.Actors;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @see <a href="https://github.com/GDGVIT/devfest-portal/wiki">API Reference</a>
 */

public class TimelineResult extends RealmObject {
    private int status;
    private String message;
    private RealmList<Phase> timeline;


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


    public RealmList<Phase> getTimeline() {
        return timeline;
    }

    public void setTimeline(RealmList<Phase> timeline) {
        this.timeline = timeline;
    }


}
