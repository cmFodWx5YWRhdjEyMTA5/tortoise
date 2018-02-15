package com.android.nobug.view.pattern;

import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.nobug.core.BaseActivity;

public class BasePatternActivity extends BaseActivity {

    private static final int CLEAR_PATTERN_DELAY_MILLI = 2000;

    protected TextView mMessageText;
    protected PatternView mPatternView;

    //  =====================================================================================


    @Override
    public int getLayoutContentView() {
        return R.layout.activity_base_pattern;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        mMessageText = (TextView)findViewById(R.id.pl_message_text);
        mPatternView = (PatternView)findViewById(R.id.pl_pattern);
    }

    @Override
    public void setProperties() {
        super.setProperties();


    }

    //  =====================================================================================

    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            mPatternView.clearPattern();
        }
    };

    protected void removeClearPatternRunnable() {
        mPatternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        mPatternView.postDelayed(clearPatternRunnable, CLEAR_PATTERN_DELAY_MILLI);
    }
}
