package com.gdgvitvellore.devfest.Control.Algorithms;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AravindRaj on 14-10-2016.
 */

public class TimelineAlgos {


    public static boolean calculateTime(String startTime, String endTime) {

        try {
            startTime = startTime.replaceAll("T", "");
            startTime = startTime.replaceAll("/", "");
            endTime = endTime.replaceAll("T", "");
            endTime = endTime.replaceAll("/", "");
            endTime = endTime.replaceAll(":", "");
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");
            long startDate = sdf.parse(startTime).getTime();
            long endDate = sdf.parse(endTime).getTime();
            long cur=System.currentTimeMillis();
            if(startDate<=cur&&endDate>=cur){
                return true;
            }else{
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String getTime(String time) {
        String[] timeSplitted = time.split("T");
        String sec = timeSplitted[1];
        sec=sec.replaceAll(":","");
        String result = sec.substring(0, 2) + ":" + sec.substring(2, 4);

        return result;
    }

}
