package com.gdgvitvellore.devfest.Entity.Actors;

/**
 * Created by Prince Bansal Local on 10/14/2016.
 */

public class Child{

    public final static int IMAGE_URL=0;
    public final static int IMAGE_RESOURCE=1;

    private String imageUrl;
    private String name;
    private String designation;
    private int imageType=IMAGE_URL;
    private int imageResource;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }
}

