package com.android.nobug.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.nobug.util.DeviceInfoUtil;

public class ActionHelper {

    //  =======================================================================================

    public static void showOutsideBrowser(Context context, String url) {
        if (url == null)
            return;

        String value = url.replace(" ", "");
        if ("".equals(value))
            return;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void showMarket(Context context) {
        try {
            String marketUrl = "market://details?id=" + DeviceInfoUtil.getAppPackageName(context);
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)));
        } catch (Exception e) {
            e.printStackTrace();
            String url = "https://play.google.com/store/apps/details?id=" + DeviceInfoUtil.getAppPackageName(context);
            showOutsideBrowser(context, url);
        }
    }

    public static void showMarketToPackage(Context context, String value) {
        try {
            String marketUrl = "market://details?id=" + value;
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)));
        } catch (Exception e) {
            e.printStackTrace();
            String url = "https://play.google.com/store/apps/details?id=" + value;
            showOutsideBrowser(context, url);
        }
    }

    public static void showMarketReview(Context context) {
        try {
            String marketUrl = "market://details?id=" + DeviceInfoUtil.getAppPackageName(context) + "&reviewId=0";
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)));
        } catch (Exception e) {
            e.printStackTrace();
            String url = "https://play.google.com/store/apps/details?id=" + DeviceInfoUtil.getAppPackageName(context) + "&reviewId=0";
            showOutsideBrowser(context, url);
        }
    }

    //  ========================================================================================




}
