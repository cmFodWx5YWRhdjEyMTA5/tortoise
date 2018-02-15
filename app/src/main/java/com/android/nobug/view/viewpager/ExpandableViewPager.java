package com.android.nobug.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by seonjonghun on 2016. 11. 29..
 */
public class ExpandableViewPager extends ViewPager {

    private int mCurrentPagePosition = 0;

    //  =======================================================================================

    public ExpandableViewPager(Context context) {
        super(context);
    }

    public ExpandableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //  =======================================================================================

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            View child = getChildAt(mCurrentPagePosition);
            if (child != null) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void reMeasureCurrentPage(int position) {
        mCurrentPagePosition = position;
        requestLayout();
    }
}