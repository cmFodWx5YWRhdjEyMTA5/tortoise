package com.android.kreators.tortoise.service;

import android.content.Context;
import android.os.Binder;

public class WalkSensorServiceBinder extends Binder {

    private WalkSensorService service;
    private Context mApplication;

    //  =====================================================================================

    public WalkSensorServiceBinder(WalkSensorService service, Context application) {
        this.service = service;
        mApplication = application;
    }

    public WalkSensorService getService() {
        return service;
    }

}

