package com.android.nobug.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    public static String PREFERENCE_NAME = "preference_";

    //  =========================================================================================

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME + context.getPackageName() , Activity.MODE_PRIVATE);
    }

    //  =========================================================================================

    public static void put(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void put(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void put(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void put(Context context, String key, long value) {
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    //  ======================================================================================

    public static String getValue(Context context, String key, String dftValue) {
        try {
            return getPreference(context).getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public static int getValue(Context context, String key, int dftValue) {
        try {
            return getPreference(context).getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public static long getValue(Context context, String key, long dftValue) {
        try {
            return getPreference(context).getLong(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public static boolean getValue(Context context, String key, boolean dftValue) {
        try {
            return getPreference(context).getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    //  ======================================================================================

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreference(context).edit();
    }
    public static void remove(Context context, String key) {
        getEditor(context).remove(key).apply();
    }

    public static void clear(Context context) {
        getEditor(context).clear().apply();
    }


}
