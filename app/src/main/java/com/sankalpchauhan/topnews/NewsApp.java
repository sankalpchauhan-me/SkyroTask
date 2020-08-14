package com.sankalpchauhan.topnews;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.sankalpchauhan.topnews.config.DefaultPrefSettings;

import timber.log.Timber;

public class NewsApp extends Application {
    private static NewsApp instance;

    public static Context getAppContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
        DefaultPrefSettings.init(this);
    }


}
