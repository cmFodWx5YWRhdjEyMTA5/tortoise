package com.android.kreators.tortoise.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.android.kreators.tortoise.BuildConfig;
import com.android.kreators.tortoise.constants.KeyFix;
import com.android.nobug.manager.analytics.GoogleAnalyticsManager;
import com.crashlytics.android.Crashlytics;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.FirebaseApp;

import io.fabric.sdk.android.Fabric;

/**
 * Created by rrobbie on 2017. 4. 10..
 */

public class TortoiseApplication extends MultiDexApplication {

    //  ======================================================================================

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppEventsLogger.activateApp(this);
        GoogleAnalyticsManager.initialize(KeyFix.GOOGLE_ANALYTICS_KEY);
        checkBuildConfig();
    }

    //  =======================================================================================

    private void checkBuildConfig() {
        if(!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }




}
