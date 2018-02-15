package com.android.nobug.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

public class ObjectUtil {

    public static String toProperties(Object item) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(item);
    }

    public static String toRawProperties(Object item) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(item);
    }

    public static boolean isEmpty(Object s) {
        if (s == null) return true;
        if ((s instanceof String) && (((String) s).trim().length() == 0)) return true;
        if (s instanceof Map) return ((Map<?, ?>) s).isEmpty();
        if (s instanceof List) return ((List<?>) s).isEmpty();
        if (s instanceof Object[]) return (((Object[]) s).length == 0);
        return false;
    }

    //  ==========================================================================================

}

