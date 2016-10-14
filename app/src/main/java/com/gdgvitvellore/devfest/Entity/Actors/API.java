package com.gdgvitvellore.devfest.Entity.Actors;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class API extends RealmObject {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("logo")
    @Expose
    private String logo;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     *
     * @param logo
     * The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

}