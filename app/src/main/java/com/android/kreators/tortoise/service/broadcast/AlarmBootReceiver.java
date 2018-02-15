package com.android.kreators.tortoise.service.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.nobug.util.log;

/**
 * Created by rrobbie on 2017. 11. 16..
 */

public class AlarmBootReceiver extends BroadcastReceiver {



    //  ===================================================================================

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if( intent != null ) {
                if( intent.getAction() != null ) {
                    if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                        // Set the alarm here.



                    }
                }
            }


            BaseStepItem item = PreferenceFactory.getCurrentStepItem(context);

            log.show( "alarm receive : " );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
