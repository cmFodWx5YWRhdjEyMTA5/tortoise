package com.android.nobug.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class BaseGridView extends GridView {

    //  ========================================================================================

    public BaseGridView(Context context) {
        super(context);
    }

    public BaseGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //  ========================================================================================

    @Override
    protected void layoutChildren() {
        try {
            super.layoutChildren();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
