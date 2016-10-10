package com.gdgvitvellore.devfest.Control.Contracts;

/**
 * Created by Prince Bansal Local on 10/8/2016.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Use this class to define all API static references that include
 * urls and others.
 */

public class APIContract {

    //TODO Edit the base url when it is ready
    public static String BASE_URL="http://devfest.gdgvitvellore.com/api";

    public static String getLoginUrl(){
        return BASE_URL+"/login";
    }

    public static String getTimelineUrl(){
        return BASE_URL+"/timeline";
    }
    public static String getSpeakersUrl(){
        return BASE_URL+"/speakers";
    }

    public static String getFAQUrl()
    {
        return BASE_URL+"/faq";
    }

    public static String getAPIAssignedUrl(){
        return BASE_URL+"/apis";
    }
    public static String getSlotUrl(){
        return BASE_URL+"/slot";
    }
    public static String getLogoutUrl(){
        return BASE_URL+"/logout";
    }

    public static Map<String,String> getLoginParams(String email,String password){
        Map<String,String> map=new HashMap<>();
        map.put("email",email);
        map.put("password",password);
        return map;
    }
    public static Map<String,String> getTimelineParams(String email,String auth_token){
        Map<String,String> map=new HashMap<>();
        map.put("email",email);
        map.put("auth_token",auth_token);
        return map;
    }
    public static Map<String,String> getSpeakersParams(String email,String auth_token){
        Map<String,String> map=new HashMap<>();
        map.put("email",email);
        map.put("auth_token",auth_token);
        return map;
    }
    public static Map<String,String> getFAQParams(String email,String auth_token){
        Map<String,String> map=new HashMap<>();
        map.put("email",email);
        map.put("auth_token",auth_token);
        return map;
    }
    public static Map<String,String> getAPIParams(String email,String auth_token){
        Map<String,String> map=new HashMap<>();
        map.put("email",email);
        map.put("auth_token",auth_token);
        return map;
    }
    public static Map<String,String> getSlotParams(String email,String auth_token,String number_of_tries,String slots){
        Map<String,String> map=new HashMap<>();
        map.put("email",email);
        map.put("auth_token",auth_token);
        map.put("number_of_tries",number_of_tries);
        map.put("slots",slots);
        return map;
    }
    public static Map<String,String> getLogoutParams(String email,String auth_token){
        Map<String,String> map=new HashMap<>();
        map.put("email",email);
        map.put("auth_token",auth_token);
        return map;
    }
}
