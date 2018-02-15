/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.android.kreators.tortoise.activity.setting.pattern;

import android.view.MenuItem;

import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.log;
import com.android.nobug.util.pattern.PatternLockUtils;
import com.android.nobug.view.pattern.BaseSetPatternActivity;
import com.android.nobug.view.pattern.PatternView;

import java.util.List;

public class SetPatternActivity extends BaseSetPatternActivity {


    //  ======================================================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPatternDetected(List<PatternView.Cell> newPattern) {
        super.onPatternDetected(newPattern);
        onRightButtonClicked();
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        PatternLockUtils.setPattern(pattern, this);
    }

    //  ======================================================================================



}
