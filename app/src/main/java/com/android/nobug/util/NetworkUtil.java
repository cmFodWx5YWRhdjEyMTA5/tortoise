package com.android.nobug.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.kreators.tortoise.R;

public class NetworkUtil {

    //  ======================================================================================

    public static boolean isConnected(final Context context) {
        if (context == null) {
            return false;
        }

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (activeNetwork != null) {
            return true;
        }

        Toast.makeText(context, context.getString(R.string.network_error_message), Toast.LENGTH_SHORT).show();
        return false;
    }

    //  ======================================================================================

    public static String getNetworkType(final Context context) {
        if (context == null) {
            return null;
        }

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return activeNetwork.getTypeName();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                String type = activeNetwork.getTypeName() + "_" + activeNetwork.getSubtypeName();
                return type;
            }
        }

        return null;
    }

}

