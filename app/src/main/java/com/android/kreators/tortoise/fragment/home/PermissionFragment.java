package com.android.kreators.tortoise.fragment.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.log;

/**
 * Created by rrobbie on 2017. 10. 16..
 */

public class PermissionFragment extends BaseFragment {


    protected final static int APP_PERMISSION = 0;

    //  =====================================================================================

    @TargetApi(Build.VERSION_CODES.M)
    protected boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        int findLocation = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (isGranted(findLocation) ) {
            getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, APP_PERMISSION);
            return false;
        } else {
            return true;
        }
    }

    protected boolean isGranted(int permission) {
        return (permission != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case APP_PERMISSION:
                int len = grantResults.length;

                if( len > 0 ) {
                    if ( !isGranted(grantResults[0]) ) {
                        granted();
                    } else {
                        denied();
                    }
                }
                break;
        }
    }

    //  =====================================================================================

    protected void granted() {
        //  TODO
    }

    protected void denied() {
        //  TODO

    }

}
