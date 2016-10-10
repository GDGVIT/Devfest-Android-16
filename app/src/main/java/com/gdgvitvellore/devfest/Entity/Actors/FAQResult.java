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

    /*
     *TODO Giving build error. To be corrected
     * Already opened an issue at
     * @see <a href="https://github.com/realm/realm-java/issues/3607">Issue</a>
     */
    //private RealmList<Faq> faqs;


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

    /*
     *TODO Giving build error. To be corrected
     * Already opened an issue at
     * @see <a href="https://github.com/realm/realm-java/issues/3607">Issue</a>
     */

   /* public RealmList<Faq> getFaqs() {
        return faqs;
    }

    public void setFaqs(RealmList<Faq> faqs) {
        this.faqs = faqs;
    }*/
}
