package com.android.nobug.manager.analytics;

import android.content.Context;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class FacebookAnalyticsManager {

    private static AppEventsLogger logger;

    //  =======================================================================================

    public static void logEvent(Context context, String event, String key, String value) {
        Bundle parameters = new Bundle();
        parameters.putString(key, value);
        getEventLogger(context).logEvent(event, parameters);
    }

    public static void logEvent(Context context, String event, HashMap<String, String> args) {
        Bundle parameters = new Bundle();
        for (Map.Entry<String, String> element : args.entrySet()) {
            parameters.putString(element.getKey(), element.getValue());
        }
        getEventLogger(context).logEvent(event, parameters);
    }

    public static void logPurchase(Context context, BigDecimal purchaseAmount, Currency currency) {
        getEventLogger(context).logPurchase(purchaseAmount, Currency.getInstance(""));
    }

    //  ========================================================================================

    private static AppEventsLogger getEventLogger(Context context) {
        if (logger == null) {
            logger = AppEventsLogger.newLogger(context);
        }
        return logger;
    }

}
