package com.gdgvitvellore.devfest.Control.Algorithms;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AravindRaj on 14-10-2016.
 */

public class TimelineAlgos {


    public static boolean calculateTime(String startTime, String endTime){


        String[] times = startTime.split(":");
        int hour  = Integer.parseInt(times[0]);
        int min = Integer.parseInt(times[1]);

        Log.d("TIME SERVER: ", ""+hour+min);

        String[] end = startTime.split(":");
        int hour1  = Integer.parseInt(times[0]);
        int min1 = Integer.parseInt(times[1]);
        long s=hour*60*60*60;
        long m=min1*60*60;
        long s1=hour1*60*60*60;
        long m1=min1*60*60;
        long cur=System.currentTimeMillis();
        if((s+m)<=cur&&(s1+m1)>=cur){
            return true;
        }else{
            return false;
        }

    }

//    public static String timeLeft(String startTime, String endTime){
//
//        String[] times = startTime.split(":");
//        int hour  = Integer.parseInt(times[0]);
//        int min = Integer.parseInt(times[1]);
//
//        String[] end = startTime.split(":");
//        int hour1  = Integer.parseInt(times[0]);
//        int min1 = Integer.parseInt(times[1]);
//
//        String duration = hour1 - hou
//
//        return
//    }

}
