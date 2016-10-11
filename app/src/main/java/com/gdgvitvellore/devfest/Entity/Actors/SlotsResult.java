package com.gdgvitvellore.devfest.Entity.Actors;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @see <a href="https://github.com/GDGVIT/devfest-portal/wiki">API Reference</a>
 */

public class SlotsResult extends RealmObject {
    private int status;
    private String message;
    private RealmList<Slots> slots;


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


    public RealmList<Slots> getSlots() {
        return slots;
    }

    public void setSlots(RealmList<Slots> slots) {
        this.slots = slots;
    }


}
