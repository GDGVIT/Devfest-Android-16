package com.gdgvitvellore.devfest.Entity.Actors;


import android.os.SystemClock;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Phase extends RealmObject {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @Ignore
    private boolean isRunning;

    private int hour, min, sec;


    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public boolean isRunning()
    {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}