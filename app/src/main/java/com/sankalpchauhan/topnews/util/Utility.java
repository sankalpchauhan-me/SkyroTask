package com.sankalpchauhan.topnews.util;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utility {

    public static Map<String, String> prepareHashmap(){
        Map<String, String> hashMap = new HashMap<>();
        //International
        hashMap.put("ABC News", "abc-news");
        hashMap.put("BBC News", "bbc-news");
        //Entertainment
        hashMap.put("Mashable", "mashable");
        hashMap.put("IGN", "ign");
        //Science & Tech
        hashMap.put("National Geographic", "national-geographic");
        hashMap.put("New Scientist", "new-scientist");
        hashMap.put("Next Big Future", "next-big-future");
        //India
        hashMap.put("The Hindu", "the-hindu");
        hashMap.put("The Times of India", "the-times-of-india");
        hashMap.put("Google News (India)", "google-news-in");
        return hashMap;
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setSnackBar(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_LONG);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }
}
