package com.gdgvitvellore.devfest.Boundary.Handlers;

/**
 * Created by Prince Bansal Local on 10/8/2016.
 */


import android.content.Context;
import android.content.SharedPreferences;

import com.gdgvitvellore.devfest.Control.Contracts.PrivateContract;
import com.gdgvitvellore.devfest.Entity.Actors.APIAssigned;
import com.gdgvitvellore.devfest.Entity.Actors.FAQ;
import com.gdgvitvellore.devfest.Entity.Actors.LogoutResult;
import com.gdgvitvellore.devfest.Entity.Actors.Realm.RealmString;
import com.gdgvitvellore.devfest.Entity.Actors.Slots;
import com.gdgvitvellore.devfest.Entity.Actors.Speakers;
import com.gdgvitvellore.devfest.Entity.Actors.Team;
import com.gdgvitvellore.devfest.Entity.Actors.Timeline;
import com.gdgvitvellore.devfest.Entity.Actors.TimelineResult;
import com.gdgvitvellore.devfest.Entity.Actors.User;

import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * This singleton class will be used to fetch as well as store any data.
 * The sources of data can be both SharedPreferences or Database.
 */

public class DataHandler {

    //Singleton reference
    public static DataHandler myInstance;

    private Context mContext;
    private SharedPreferences mPreferences;
    private Realm mRealm;

    /**
     * Method to retrieve the singleton reference of this class
     *
     * @param context The context reference passed from the calling class
     * @return Returns static reference of {@link DataHandler} class
     */

    public static DataHandler getInstance(Context context) {
        if (myInstance == null) {
            myInstance = new DataHandler(context);
        }
        return myInstance;
    }

    /**
     * Private constructor. This class cannot be instantiated outside this class.
     * All class attrbutes should be initialised here
     *
     * @param context The context reference passed while instantiating
     */

    private DataHandler(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences(PrivateContract.PREFERENCES_FILE, Context.MODE_PRIVATE);
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * Use this method to save {@link String} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value String value to store
     */

    private void savePreference(String key, String value) {
        mPreferences.edit().putString(key, value).commit();
    }


    /**
     * Use this method to retrieve {@link String} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default String value to fetch
     * @return Returns String value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public String getPreference(String key, String def) {

        return mPreferences.getString(key, def);
    }

    /**
     * Use this method to save {@link boolean} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value boolean value to store
     */

    private void savePreference(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * Use this method to retrieve {@link boolean} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default boolean value to fetch
     * @return Returns boolean value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public boolean getPreference(String key, boolean def) {

        return mPreferences.getBoolean(key, def);
    }

    /**
     * Use this method to save {@link int} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value int value to store
     */
    private void savePreference(String key, int value) {
        mPreferences.edit().putInt(key, value).commit();
    }

    /**
     * Use this method to retrieve {@link int} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default int value to fetch
     * @return Returns int value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public int getPreference(String key, int def) {

        return mPreferences.getInt(key, def);

    }

    public String getUserMail(){

        User user = mRealm.where(User.class).findFirst();
        String mailid = user.getEmail();
        return mailid;
    }

    public String getAuthToken(){
        User user = mRealm.where(User.class).findFirst();
        String auth = user.getAuthToken();
        return auth;
    }

    public Timeline getTimeline(){
        Timeline timeline = mRealm.where(Timeline.class).findFirst();
            return timeline;
    }

    /**
     * Use this method to save {@link HashSet<String>} to SharedPreferences
     *
     * @param key   Key value of the pair to store
     * @param value {@link HashSet<String>} value to store
     */

    private void savePreference(String key, HashSet<String> value) {
        mPreferences.edit().putStringSet(key, value).commit();
    }


    /**
     * Use this method to retrieve {@link HashSet} from SharedPreferences using key
     *
     * @param key Key of the pair to fetch
     * @param def Default {@link HashSet<String>} value to fetch
     * @return Returns {@link HashSet<String>} value with associated key from SharedPreferences.
     * If value doesn't exist, returns default value passed in argument.
     */

    public HashSet<String> getPreference(String key, HashSet<String> def) {

        return (HashSet<String>) mPreferences.getStringSet(key, def);

    }

    /**
     * Use this method to change whether user opened the app for first time or not.
     *
     * @param isFirstTimeUser pass true to set first time user and false if he has already been t the app before
     */
    public void saveFirstTimeUser(boolean isFirstTimeUser) {
        savePreference("firstTimeUser", isFirstTimeUser);
    }

    /**
     * Use this method to know whether user is a first time user or not
     *
     * @return Returns true if yes else false
     * If value doesn't exist, returns true.
     */
    public boolean isFirstTimeUser() {
        return getPreference("firstTimeUser", true);
    }

    /**
     * Use this method to store user data returned after login in the form of {@link com.gdgvitvellore.devfest.Entity.Actors.User}
     *
     * @param user This is the user object which contains all info about user
     */
    public void saveUser(User user) {
        if (user != null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(user);
            mRealm.commitTransaction();
        }
    }

    /**
     * Use this method to store user's team data returned after login in the form of {@link com.gdgvitvellore.devfest.Entity.Actors.Team}
     *
     * @param team
     */

    public void saveTeam(Team team) {
        if (team != null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(team);
            mRealm.commitTransaction();
        }
    }

    /**
     * Use this method to store timeline {@link com.gdgvitvellore.devfest.Entity.Actors.Timeline}
     *
     * @param timeline
     */
    public void saveTimeline(RealmList<Timeline> timeline) {
        if (timeline != null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(timeline);
            mRealm.commitTransaction();
        }
    }

    public void saveSpeakers(RealmList<Speakers> speakers) {

        if (speakers != null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(speakers);
            mRealm.commitTransaction();
        }
    }

    public void saveFAQ(RealmList<FAQ> FAQs) {
        if (FAQs != null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(FAQs);
            mRealm.commitTransaction();
        }
    }

    public void saveApi(RealmList<APIAssigned> apis) {
        if (apis != null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(apis);
            mRealm.commitTransaction();
        }
    }

    public void saveSlots(RealmList<Slots> slots) {
        if (slots != null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(slots);
            mRealm.commitTransaction();

        }
    }

    public void saveLogout(LogoutResult logoutResults) {
        if(logoutResults!=null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(logoutResults);
            mRealm.commitTransaction();
        }
    }


    public boolean isLoggedIn() {
        return getPreference("loggedIn", false);
    }

    public void saveLoggedIn(boolean isloggedIn) {
        savePreference("loggedIn", isloggedIn);
    }

}
