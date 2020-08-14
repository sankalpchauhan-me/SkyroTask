package com.sankalpchauhan.topnews.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

public class DefaultPrefSettings {
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private static DefaultPrefSettings ourInstance = new DefaultPrefSettings();
    private final Object object = new Object();
    private SharedPreferences defaultPref;

    public static DefaultPrefSettings getInstance() {
        return ourInstance;
    }

    public static void init(Context context) {
        ourInstance.defaultPref = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
