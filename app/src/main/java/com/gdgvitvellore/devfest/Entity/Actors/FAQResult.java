package com.gdgvitvellore.devfest.Entity.Actors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @see <a href="https://github.com/GDGVIT/devfest-portal/wiki">API Reference</a>
 */

public class FAQResult extends RealmObject {
    private int status;
    private String message;


    private RealmList<FAQ> faqs;


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


    public RealmList<FAQ> getFaqs() {
        return faqs;
    }

    public void setFaqs(RealmList<FAQ> faqs) {
        this.faqs = faqs;
    }
}
