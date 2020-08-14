package com.sankalpchauhan.topnews.config;

public class Constants {
    public final static String BASE_URL = "https://newsapi.org/v2/";
    public final static String SOURCES_ENDPOINT = "sources";
    public final static String TOP_HEADLINES = "top-headlines";
    public static final String ARTICLE_LIST = "articleList";
    public static final String TAB_POSITION = "position";

    //max number of retries before fail
    public static final int MAX_LIMIT = 3;
    // wait for 5 second before retrying network request
    public static final int WAIT_THRESHOLD = 5000;
}
