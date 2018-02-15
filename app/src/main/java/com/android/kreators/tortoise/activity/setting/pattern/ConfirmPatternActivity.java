/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.android.kreators.tortoise.activity.setting.pattern;

import com.android.nobug.core.BaseActivity;
import com.android.nobug.util.PreferenceUtil;
import com.android.nobug.util.pattern.Contract;
import com.android.nobug.util.pattern.PatternLockUtils;
import com.android.nobug.view.pattern.BaseConfirmPatternActivity;
import com.android.nobug.view.pattern.PatternView;

import java.util.List;

public class ConfirmPatternActivity extends BaseConfirmPatternActivity {


    //  =====================================================================================

    @Override
    protected boolean isStealthModeEnabled() {
        return !PreferenceUtil.getValue(this, Contract.KEY_PATTERN_VISIBLE, Contract.DEFAULT_PATTERN_VISIBLE);
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        return PatternLockUtils.isPatternCorrect(pattern, this);
    }

    @Override
    protected void onForgotPassword() {
        //  startActivity(new Intent(this, ResetPatternActivity.class));
        super.onForgotPassword();
    }
}
