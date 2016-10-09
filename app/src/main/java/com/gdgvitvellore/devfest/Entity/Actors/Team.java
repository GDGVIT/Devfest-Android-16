package com.gdgvitvellore.devfest.Entity.Actors;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Prince Bansal Local on 10/8/2016.
 */

/**
 * @see <a href="https://github.com/GDGVIT/devfest-portal/wiki">API Reference</a>
 */

public class Team extends RealmObject {

    private String name;
    private RealmList<Member> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Member> getMembers() {
        return members;
    }

    public void setMembers(RealmList<Member> members) {
        this.members = members;
    }



}
