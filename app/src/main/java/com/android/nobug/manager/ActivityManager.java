package com.android.nobug.manager;

import android.app.Activity;
import java.util.ArrayList;

public class ActivityManager {

    private static ActivityManager uniqueInstance = new ActivityManager();
    private ArrayList<Activity> list = new ArrayList<Activity>();

//  ===============================================================================================

    public static ActivityManager getInstance() {
        return uniqueInstance;
    }

    //  =========================================================================================

    public int getSize() {
        return list.size();
    }

    public void add(Activity activity) {
        list.add(activity);
    }

    public void remove(Activity activity) {
        list.remove(activity);
    }

    public void pop(Activity item) {
        int len = list.size();

        for (int i=0; i<len; i++) {
            Activity activity = list.get(i);
            if (activity != null && item != null ) {
                if( activity.getClass().getSimpleName().toString().equals(item.getClass().getSimpleName().toString()) ) {
                    activity.finish();
                    list.remove(i);
                }
            }
        }
    }

    public  void reset(Activity item) {
        int len = list.size();

        for (int i=0; i<len; i++) {
            Activity activity = list.get(i);
            if (activity != null) {
                if( !activity.equals(item) ) {
                    activity.finish();
                }
            }
        }
        list = new ArrayList<>();
    }


}
