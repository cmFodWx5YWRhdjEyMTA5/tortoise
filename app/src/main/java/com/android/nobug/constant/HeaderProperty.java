package com.android.nobug.constant;

import android.content.Context;

import com.android.kreators.tortoise.R;
import com.android.nobug.util.DeviceInfoUtil;

public class HeaderProperty {

    //  =====================================================================================

    public final static String FACEBOOK_AUTH_TOKEN = "facebook-auth-token";
    public final static String AUTH_TOKEN = "tortoise_api_key";
    public final static String USER_AGENT = "User-Agent";

    //  =====================================================================================

    public static String getUserAgent(Context context) {
        try {
            String agentVersion = "1.0.0";
            String android = "android";
            String value = agentVersion + "|" + DeviceInfoUtil.getAppVersion(context) + "|" + android + " " + DeviceInfoUtil.getOsVersion() + "|" + DeviceInfoUtil.getDeviceName();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
