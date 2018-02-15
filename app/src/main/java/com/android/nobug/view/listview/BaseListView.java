package com.android.nobug.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.android.nobug.util.log;

/**
 * Created by seonjonghun on 2016. 8. 3..
 */
public class BaseListView extends ListView {

    //  =======================================================================================

    public BaseListView(Context context) {
        super(context);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //  =======================================================================================

    @Override
    protected void layoutChildren() {
        try {
            super.layoutChildren();
        } catch (IllegalStateException e) {
            log.show("layoutChildren exception : " + e.getLocalizedMessage());
        }
    }
}
