package com.gdgvitvellore.devfest.Entity.Actors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by AravindRaj on 15-10-2016.
 */

public class Coupon extends RealmObject {

    @SerializedName("couponType")
    @Expose
    private String couponName;
    @SerializedName("code")
    @Expose
    private String couponCode;
    @SerializedName("used")
    @Expose
    private boolean isUsed;


    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
