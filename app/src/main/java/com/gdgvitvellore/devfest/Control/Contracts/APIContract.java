package com.gdgvitvellore.devfest.Control.Contracts;

/**
 * Created by Prince Bansal Local on 10/8/2016.
 */

/**
 * Use this class to define all API static references that include
 * urls and others.
 */

public class APIContract {

    //TODO Edit the base url when it is ready
    public static String BASE_URL="";

    public static String getLoginUrl(){
        return BASE_URL+"/login";
    }
}
