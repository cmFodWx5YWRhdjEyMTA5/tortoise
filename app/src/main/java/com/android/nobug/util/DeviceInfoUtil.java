package com.android.nobug.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeviceInfoUtil {

    public static String getAppVersion(Context context) {
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return URLEncoder.encode(i.versionName, "utf-8");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return i.versionCode;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getAppPackageName(Context context) {
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return URLEncoder.encode(i.packageName, "utf-8");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getOperator(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }

    public static String getDeviceName() throws UnsupportedEncodingException {
        return URLEncoder.encode(Build.MODEL, "utf-8");
    }

    public static String getDeviceId(Context context) throws UnsupportedEncodingException {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return URLEncoder.encode(android_id, "utf-8");
    }

    @SuppressLint("NewApi")
    public static String getDeviceSerialNumber() {
        try {
            return (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception ignored) {
            return Settings.Secure.ANDROID_ID;
        }
    }

    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    public static int getOsSdkInt() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isKitkat() {
        return (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH);
    }

    public static String getKeyHash(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            String packageName = context.getApplicationContext().getPackageName();
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                log.show("Key Hash=" + key);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }
}
