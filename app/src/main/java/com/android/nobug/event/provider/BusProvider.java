package com.android.nobug.event.provider;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public final class BusProvider extends Bus {

    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    //  =========================================================================================

    public static Bus getInstance() {
        return BUS;
    }

    public static Bus createInstance() {
        return new Bus(ThreadEnforcer.ANY);
    }

    //  =========================================================================================

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    BusProvider.super.post(event);
                }
            });
        }
    }

}
