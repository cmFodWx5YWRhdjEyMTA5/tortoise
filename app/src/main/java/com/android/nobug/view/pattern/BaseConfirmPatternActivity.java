/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.android.nobug.view.pattern;

import android.os.Bundle;

import com.android.kreators.tortoise.R;

import java.util.List;

public class BaseConfirmPatternActivity extends BasePatternActivity
        implements PatternView.OnPatternListener {

    private static final String KEY_NUM_FAILED_ATTEMPTS = "num_failed_attempts";

    public static final int RESULT_FORGOT_PASSWORD = RESULT_FIRST_USER;

    protected int mNumFailedAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessageText.setText(R.string.pl_draw_pattern_to_unlock);
        mPatternView.setInStealthMode(isStealthModeEnabled());
        mPatternView.setOnPatternListener(this);

//        onForgotPassword();

        ViewAccessibilityCompat.announceForAccessibility(mMessageText, mMessageText.getText());

        if (savedInstanceState == null) {
            mNumFailedAttempts = 0;
        } else {
            mNumFailedAttempts = savedInstanceState.getInt(KEY_NUM_FAILED_ATTEMPTS);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_NUM_FAILED_ATTEMPTS, mNumFailedAttempts);
    }

    @Override
    public void onPatternStart() {

        removeClearPatternRunnable();

        // Set display mode to correct to ensure that pattern can be in stealth mode.
        mPatternView.setDisplayMode(PatternView.DisplayMode.Correct);
    }

    @Override
    public void onPatternCellAdded(List<PatternView.Cell> pattern) {}

    @Override
    public void onPatternDetected(List<PatternView.Cell> pattern) {
        if (isPatternCorrect(pattern)) {
            onConfirmed();
        } else {
            mMessageText.setText(R.string.pl_wrong_pattern);
            mPatternView.setDisplayMode(PatternView.DisplayMode.Wrong);
            postClearPatternRunnable();
            ViewAccessibilityCompat.announceForAccessibility(mMessageText, mMessageText.getText());
            onWrongPattern();
        }
    }

    @Override
    public void onPatternCleared() {
        removeClearPatternRunnable();
    }

    protected boolean isStealthModeEnabled() {
        return false;
    }

    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        return true;
    }

    protected void onConfirmed() {
        setResult(RESULT_OK);
        finish();
    }

    protected void onWrongPattern() {
        ++mNumFailedAttempts;
    }

    protected void onCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    protected void onForgotPassword() {
        setResult(RESULT_FORGOT_PASSWORD);
        finish();
    }
}
