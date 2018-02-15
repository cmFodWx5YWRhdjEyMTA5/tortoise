package com.android.kreators.tortoise.view.hexagon;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.callback.IFloatPointCallback;
import com.android.nobug.core.BaseRelativeLayout;
import com.android.nobug.util.DeprecationUtil;
import com.android.nobug.util.log;

public class Hexagon extends BaseRelativeLayout {


    public static int INTERVAL = 3000;
    public static int MIN_INTERVAL = 2000;
    public static int DURATION = 1000;

    //  ======================================================================================

    private HexagonSurfaceView hexagonSurfaceView;
    private IFloatPointCallback iFloatPointCallback;

    //  ======================================================================================

    public Hexagon(Context context) {
        super(context);
        mContext = context;
    }

    public Hexagon(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public Hexagon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.component_hexagon;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        hexagonSurfaceView = (HexagonSurfaceView) mView.findViewById(R.id.hexagonSurfaceView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        hexagonSurfaceView.setDuration(INTERVAL);
        hexagonSurfaceView.setBaseColor(ContextCompat.getColor(mContext, R.color.color_dddddd));
        hexagonSurfaceView.setProgressColor(ContextCompat.getColor(mContext, R.color.colorAccent));
    }

    @Override
    public void configureListener() {
        super.configureListener();

        hexagonSurfaceView.setIFloatPointCallback(floatPointCallback);

        hexagonSurfaceView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                DeprecationUtil.removeOnGlobalLayoutListener(hexagonSurfaceView, this);
                hexagonSurfaceView.ready();
            }
        });
    }

    //  ======================================================================================

    private IFloatPointCallback floatPointCallback = new IFloatPointCallback() {
        @Override
        public void update(float x, float y) {
            if( iFloatPointCallback != null ) {
                iFloatPointCallback.update(x, y);
            }
        }
    };

    //  ======================================================================================

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        float radius = Math.min(width / 2f, height / 2f) - 10f;

        setMeasuredDimension(width, height);
        hexagonSurfaceView.setVertexProperties( radius );
    }

    //  ======================================================================================

    public void setProgressColor(int value) {
        hexagonSurfaceView.setProgressColor(value);
    }

    public void setBaseColor(int value) {
        hexagonSurfaceView.setBaseColor(value);
    }

    public void setBorderThickness(int value) {
        hexagonSurfaceView.setBorderThickness(value);
    }

    public void setDuration(int value) {
        hexagonSurfaceView.setDuration(value);
    }

    public void setIFloatPointCallback(IFloatPointCallback callback) {
        this.iFloatPointCallback = callback;
    }

    public void setProgress(int value, int max) {
        hexagonSurfaceView.setBaseColor( ContextCompat.getColor(mContext, R.color.color_dddddd) );
        hexagonSurfaceView.setProgress(value, max);
    }

    public void setGoal() {
        hexagonSurfaceView.setBaseColor( ContextCompat.getColor(mContext, R.color.colorPrimary) );
    }

    public void setBalanceEmpty() {
        hexagonSurfaceView.setBaseColor( ContextCompat.getColor(mContext, R.color.color_ef4b4c) );
    }


}



