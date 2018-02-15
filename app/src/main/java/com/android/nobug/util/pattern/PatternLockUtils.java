/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.android.nobug.util.pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.nobug.view.pattern.BaseConfirmPatternActivity;
import com.android.nobug.util.PreferenceUtil;
import com.android.nobug.view.pattern.BaseSetPatternActivity;
import com.android.nobug.view.pattern.PatternUtils;
import com.android.nobug.view.pattern.PatternView;

import java.util.List;


public class PatternLockUtils {

    public static final int REQUEST_CODE_CONFIRM_PATTERN = 1214;

    //  ====================================================================================

    private PatternLockUtils() {}

    public static void setPattern(List<PatternView.Cell> pattern, Context context) {
        PreferenceUtil.put(context, Contract.KEY_PATTERN_SHA1, PatternUtils.patternToSha1String(pattern));
    }

    private static String getPatternSha1(Context context) {
        return PreferenceUtil.getValue(context, Contract.KEY_PATTERN_SHA1, Contract.DEFAULT_PATTERN_SHA1);
    }

    public static boolean hasPattern(Context context) {
        return !TextUtils.isEmpty(getPatternSha1(context));
    }

    public static boolean isPatternCorrect(List<PatternView.Cell> pattern, Context context) {
        return TextUtils.equals(PatternUtils.patternToSha1String(pattern), getPatternSha1(context));
    }

    public static void clearPattern(Context context) {
        PreferenceUtil.remove(context, Contract.KEY_PATTERN_SHA1);
    }

    public static void setPatternByUser(Context context) {
        context.startActivity(new Intent(context, BaseSetPatternActivity.class));
    }

    // NOTE: Should only be called when there is a pattern for this account.
    public static void confirmPattern(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, BaseConfirmPatternActivity.class),
                requestCode);
    }

    public static void confirmPattern(Activity activity) {
        confirmPattern(activity, REQUEST_CODE_CONFIRM_PATTERN);
    }

    public static void confirmPatternIfHas(Activity activity) {
        if (hasPattern(activity)) {
            confirmPattern(activity);
        }
    }

    public static <ActivityType extends Activity & OnConfirmPatternResultListener> boolean
            checkConfirmPatternResult(ActivityType activity, int requestCode, int resultCode) {
        if (requestCode == REQUEST_CODE_CONFIRM_PATTERN) {
            activity.onConfirmPatternResult(resultCode == Activity.RESULT_OK);
            return true;
        } else {
            return false;
        }
    }

    public interface OnConfirmPatternResultListener {
        void onConfirmPatternResult(boolean successful);
    }
}
