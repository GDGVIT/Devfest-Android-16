package com.gdgvitvellore.devfest.Entity.Actors;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by AravindRaj on 15-10-2016.
 */

public class CouponResult extends RealmObject {

    private int status;
    private String message;
    private RealmList<Coupon> coupons;


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

    public RealmList<Coupon> getCoupons() {
        return coupons;
    }

    public void setCouponList(RealmList<Coupon> coupons) {
        this.coupons = coupons;
    }
}
