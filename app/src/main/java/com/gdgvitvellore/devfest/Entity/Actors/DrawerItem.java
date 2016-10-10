package com.gdgvitvellore.devfest.Entity.Actors;

/**
 * Created by Prince Bansal Local on 10/10/2016.
 */

public class DrawerItem {
    private String title;
    private int iconId;

    public DrawerItem(String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
