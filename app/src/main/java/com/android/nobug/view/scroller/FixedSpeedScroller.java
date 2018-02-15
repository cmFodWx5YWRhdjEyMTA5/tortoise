package com.android.nobug.view.scroller;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class FixedSpeedScroller extends Scroller {

    private double mScrollFactor = 1;

    //  ========================================================================================

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    //  =======================================================================================

    public void setScrollDurationFactor(double scrollFactor) {
        mScrollFactor = scrollFactor;
    }

    //  =======================================================================================

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
    }
}