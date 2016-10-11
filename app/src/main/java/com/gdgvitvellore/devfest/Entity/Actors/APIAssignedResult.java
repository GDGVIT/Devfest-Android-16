package com.gdgvitvellore.devfest.Entity.Actors;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @see <a href="https://github.com/GDGVIT/devfest-portal/wiki">API Reference</a>
 */

public class APIAssignedResult extends RealmObject {
    private int status;
    private String message;
    private RealmList<APIAssigned> apis;


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


    public RealmList<APIAssigned> getApis() {
        return apis;
    }


    public void setApis(RealmList<APIAssigned> apis) {
        this.apis = apis;
    }


}