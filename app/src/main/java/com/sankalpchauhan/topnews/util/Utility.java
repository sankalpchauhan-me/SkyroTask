package com.sankalpchauhan.topnews.util;

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
}
