package com.android.nobug.view.listview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.android.kreators.tortoise.R;

/**
 * Created by rrobbie on 2017. 4. 21..
 */

public class DividerGridView extends BaseGridView {

    private Context mContext = null;

    //  =======================================================================================

    public DividerGridView(Context context) {
        super(context);
        mContext = context;
    }

    public DividerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public DividerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    //  =======================================================================================

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final int count = getChildCount();
        for(int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int top = child.getTop();
            int bottom = child.getBottom();
            int left = child.getLeft();
            int right = child.getRight();

            Paint paint = new Paint();
            paint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));

            int density = 10;
            int offset = 0;

            paint.setStrokeWidth(Math.round(0.5 * density));
            canvas.drawLine(left + offset, bottom, right - offset, bottom, paint);
            canvas.drawLine(right + offset, top, right - offset, bottom, paint);
        }

        super.dispatchDraw(canvas);
    }


}
