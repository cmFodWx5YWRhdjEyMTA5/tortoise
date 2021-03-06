package com.android.nobug.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public abstract class BaseLinearLayout extends LinearLayout implements IBaseView {

    protected View mView;
    protected Context mContext;

    //  ======================================================================================

    public BaseLinearLayout(Context context) {
        super(context);
        mContext = context;
    }

    public BaseLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    //  ======================================================================================

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initialize();
    }

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return 0;
    }

    @Override
    public void setUp() {
    }

    @Override
    public void initialize() {
        createChildren();
        setProperties();
        configureListener();
    }

    @Override
    public void createChildren() {
        mView = inflate(getContext(), getLayoutContentView(), this);
    }

    @Override
    public void configureListener() {
    }

    @Override
    public void setProperties() {
    }

}
