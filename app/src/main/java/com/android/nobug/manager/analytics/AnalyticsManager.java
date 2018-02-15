package com.android.nobug.manager.analytics;

import android.app.Application;
import android.content.Context;

public class AnalyticsManager extends Application {

    private static AnalyticsManager instance;

    //  =========================================================================================

    public static AnalyticsManager getInstance() {
        if (instance == null) {
            instance = new AnalyticsManager();
        }
        return instance;
    }

    //  =========================================================================================

    public void sendEvent(Context context, String category, String action, String label) {
        try {
            //  FacebookAnalyticsManager.logEvent(context, category, action, label);
            //  GoogleAnalyticsManager.sendEvent(context, category, action, label);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendScreen(Context context, String name) {
        try {
            //  GoogleAnalyticsManager.sendScreen(context, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  =========================================================================================

}
