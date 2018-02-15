package com.android.nobug.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.android.nobug.view.scroller.FixedSpeedScroller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

public class NonViewPager extends ViewPager {

    private FixedSpeedScroller mScroller = null;

    private boolean touchEnabled = false;
    private boolean animationEnabled = true;

    //  ========================================================================================

    public NonViewPager(Context context) {
        super(context);
        postInitViewPager();
    }

    public NonViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }

    //  ========================================================================================

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (this.touchEnabled) {
                return super.onTouchEvent(event);
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.touchEnabled && super.onInterceptTouchEvent(event);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, animationEnabled);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, animationEnabled);
    }

    //  ========================================================================================

    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);
            mScroller = new FixedSpeedScroller(getContext(), (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }

    //  ========================================================================================

    public void setInterceptTouchEvent(boolean enabled) {
        this.touchEnabled = enabled;
    }

    public void setAnimationEnabled(boolean enabled) {
        this.animationEnabled = enabled;
    }

    //  ========================================================================================

}
