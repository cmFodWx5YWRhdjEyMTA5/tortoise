package com.android.kreators.tortoise.view.hexagon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Region;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.android.kreators.tortoise.callback.IFloatPointCallback;
import com.android.kreators.tortoise.manager.BalanceManager;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.nobug.util.DisplayUtil;
import com.android.nobug.util.log;

import java.util.ArrayList;

/**
 * Created by rrobbie on 2017. 8. 17..
 */

public class HexagonSurfaceView extends RelativeLayout {

    private Context mContext;

    //  ======================================================================================

    private int max = 0;
    private int progress = 0;

    private final int STEP = 6;
    private final int HALF_STEP = 3;

    private int bound = 0;
    private int goalIndex = 0;
    private int currentIndex = 0;

    private boolean created = false;

    private ArrayList<FloatPoint> vertexeis;

    private FloatPoint topCenter;
    private FloatPoint topRight;
    private FloatPoint topLeft;
    private FloatPoint bottomCenter;
    private FloatPoint bottomRight;
    private FloatPoint bottomLeft;

    private FloatPoint currentPoint;

    //  ======================================================================================

    private Path basePath;
    private Path secondaryPath;
    private Path animationPath;
    private Path progressPath;
    private Path replacePath;

    private Paint baseSolidPaint;
    private Paint basePaint;
    private Paint secondaryPaint;
    private Paint animationPaint;

    private boolean isDraw = false;

    private Handler mHandler = new Handler();

    //  =====================================================================================

    private int baseColor = Color.LTGRAY;
    private int progressColor = Color.YELLOW;
    private int borderThickness = 10;
    private int duration = 0;

    private IFloatPointCallback iFloatPointCallback;

    //  =====================================================================================

    public HexagonSurfaceView(Context context) {
        super(context);
        mContext = context;
        initialize();
    }

    public HexagonSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize();
    }

    public HexagonSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initialize();
    }

    //  =====================================================================================

    private void initialize() {
        createChildren();
        serProperties();
        configureListener();
    }

    private void createChildren() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        borderThickness = DisplayUtil.dip2px(mContext, 10);
        createPaths();
        createBasePaint();
        secondaryPaint = createPaint(baseColor, borderThickness, Paint.Cap.ROUND, Paint.Style.STROKE);
        animationPaint = createPaint(progressColor, borderThickness, Paint.Cap.ROUND, Paint.Style.STROKE);
    }

    private void serProperties() {

    }

    private void configureListener() {

    }

    //  =====================================================================================

    private void createPaths() {
        basePath = new Path();
        secondaryPath = new Path();
        replacePath = new Path();
        animationPath = new Path();
        progressPath = new Path();
    }

    private void createBasePaint() {
        baseSolidPaint = createPaint(Color.WHITE, 0, Paint.Cap.SQUARE, Paint.Style.FILL);

        basePaint = new Paint();
        basePaint.setColor(baseColor);
        basePaint.setStrokeCap(Paint.Cap.SQUARE);
        basePaint.setStrokeWidth( borderThickness );
        basePaint.setAntiAlias(true);
        basePaint.setStyle(Paint.Style.STROKE);
    }

    private Paint createPaint(int color, float width, Paint.Cap cap, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor( color );
        paint.setStrokeWidth( width );
        paint.setStrokeCap(cap);
        paint.setAntiAlias(true);
        paint.setStyle(style);
        return paint;
    }

    private void setupPath(int remain) {
        reset();

        for (int i = 1; i<=goalIndex; i++) {
            FloatPoint point = currentPoint = vertexeis.get(i);
            replacePath.lineTo( point.x, point.y );
            progressPath.lineTo( point.x, point.y );
            currentIndex = i;
        }

        if( remain > 0 && goalIndex != STEP ) {
            int targetIndex = goalIndex == STEP ? goalIndex : goalIndex + 1;
            int index = targetIndex - 1;

            FloatPoint current = vertexeis.get(index);
            FloatPoint target = vertexeis.get(targetIndex);

            float moveX = Math.abs( ( (current.x - target.x) / bound * remain ) );
            float moveY = Math.abs( ( (current.y - target.y) / bound * remain ) );
            float xp = current.x < target.x ? current.x + moveX : current.x - moveX;
            float yp = targetIndex > HALF_STEP ? current.y - moveY: current.y + moveY;

            replacePath.lineTo( xp, yp );
            progressPath.lineTo( xp, yp );
            currentPoint = new FloatPoint(xp, yp);
            currentIndex = targetIndex - 1;
        }
    }

    private void addPath(int remain) {
        animationPath.reset();
        animationPath.moveTo(currentPoint.x, currentPoint.y);

        progressPath.reset();
        progressPath.moveTo(currentPoint.x, currentPoint.y);

        replacePath.reset();
        replacePath.moveTo(topCenter.x, topCenter.y);

        int current = currentIndex;

        for (int i = 1; i<=goalIndex; i++) {
            FloatPoint point = vertexeis.get(i);
            replacePath.lineTo( point.x, point.y );
        }

        for (int i = current+1; i<=goalIndex; i++) {
            FloatPoint point = currentPoint = vertexeis.get(i);
            progressPath.lineTo( point.x, point.y );
            currentIndex = i;
        }

        if( remain > 0 && goalIndex != STEP ) {
            int targetIndex = goalIndex == STEP ? goalIndex : goalIndex + 1;
            int index = targetIndex - 1;

            FloatPoint cPoint = vertexeis.get(index);
            FloatPoint target = vertexeis.get(targetIndex);

            float moveX = Math.abs( ( (cPoint.x - target.x) / bound * remain ) );
            float moveY = Math.abs( ( (cPoint.y - target.y) / bound * remain ) );
            float xp = cPoint.x < target.x ? cPoint.x + moveX : cPoint.x - moveX;
            float yp = targetIndex > HALF_STEP ? cPoint.y - moveY: cPoint.y + moveY;

            replacePath.lineTo( xp, yp );
            progressPath.lineTo(xp, yp);
            currentPoint = new FloatPoint(xp, yp);
            currentIndex = targetIndex - 1;
        }
    }

    private Runnable displayTask = new Runnable() {
        @Override public void run() {
            isDraw = true;

            invalidate();

            secondaryPath.reset();
            secondaryPath.addPath(replacePath);

            isDraw = false;
        }
    };

    private void update(final Path path) {
        Animation animation = new Animation() {
            final PathMeasure pathMeasure = new PathMeasure(path, false);
            final float[] position = new float[2];

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                pathMeasure.getPosTan(pathMeasure.getLength() * interpolatedTime, position, null);
                setCoordinatesF(position[0], position[1]);
            }
        };

        int accelerate = BalanceManager.getInstance().getAccelerationTime(mContext);

        animation.setDuration(accelerate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.postDelayed(displayTask, 100);
            }
        });
        startAnimation(animation);
    }

    private void setCoordinatesF(float x, float y) {
        animationPath.lineTo(x, y);
        secondaryPath.lineTo(x, y);

        currentPoint.x = x;
        currentPoint.y = y;

        iFloatPointCallback.update(x, y);
        invalidate();
    }

    private void drawBase() {
        basePath.reset();
        basePath.moveTo(topCenter.x, topCenter.y);
        basePath.lineTo(topRight.x, topRight.y);
        basePath.lineTo(bottomRight.x, bottomRight.y);
        basePath.lineTo(bottomCenter.x, bottomCenter.y);
        basePath.lineTo(bottomLeft.x, bottomLeft.y);
        basePath.lineTo(topLeft.x, topLeft.y);
        basePath.close();
    }

    //  =======================================================================================

    public void reset() {
        currentPoint = topCenter;

        if( currentPoint != null ) {
            animationPath.reset();
            animationPath.moveTo(currentPoint.x, currentPoint.y);

            progressPath.reset();
            progressPath.moveTo(currentPoint.x, currentPoint.y);

            secondaryPath.reset();
            secondaryPath.moveTo(currentPoint.x, currentPoint.y);

            replacePath.reset();
            replacePath.moveTo(currentPoint.x, currentPoint.y);

            iFloatPointCallback.update(currentPoint.x, currentPoint.y);
        }
    }

    public void setProgressColor(int value) {
        progressColor = value;
        secondaryPaint.setColor(progressColor);
        animationPaint.setColor(progressColor);
        invalidate();
    }

    public void setBaseColor(int value) {
        baseColor = value;
        basePaint.setColor(baseColor);
        invalidate();
    }

    public void setBorderThickness(int value) {
        borderThickness = value;
        secondaryPaint.setStrokeWidth(borderThickness);
        animationPaint.setStrokeWidth(borderThickness);
        basePaint.setStrokeWidth(borderThickness);
        invalidate();
    }

    public void setDuration(int value) {
        duration = value;
    }

    public void setIFloatPointCallback(IFloatPointCallback callback) {
        this.iFloatPointCallback = callback;
    }

    public void ready() {
        created = true;
        reset();
    }

    //  =======================================================================================

    public void setVertexProperties(float radius) {
        if( !created ) {
            float centerX = getMeasuredWidth() / 2f;
            float centerY = getMeasuredHeight() / 2f;
            float radiusBorder = radius - 5f;
            float halfRadiusBorder = radiusBorder / 2f;
            float triangleBorderHeight = (float) (Math.sqrt(3.0) * halfRadiusBorder);

            float topCenterY = centerY - radiusBorder;
            float topRightY = centerY - halfRadiusBorder;
            float topLeftY = centerY - halfRadiusBorder;

            float bottomCenterY = centerY + radiusBorder;
            float bottomLeftY = centerY + halfRadiusBorder;
            float bottomRightY = centerY + halfRadiusBorder;

            float leftX = centerX - triangleBorderHeight;
            float rightX = centerX + triangleBorderHeight;

            topCenter = new FloatPoint(centerX, topCenterY);
            topRight = new FloatPoint(rightX, topRightY);
            bottomRight = new FloatPoint(rightX, bottomRightY);
            bottomCenter = new FloatPoint(centerX, bottomCenterY);
            bottomLeft = new FloatPoint(leftX, bottomLeftY);
            topLeft = new FloatPoint(leftX, topLeftY);

            vertexeis = new ArrayList<FloatPoint>();
            vertexeis.add( topCenter );
            vertexeis.add( topRight );
            vertexeis.add( bottomRight );
            vertexeis.add( bottomCenter );
            vertexeis.add( bottomLeft );
            vertexeis.add( topLeft );
            vertexeis.add( topCenter );

            currentPoint = topCenter;

            drawBase();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(basePath, baseSolidPaint);
        canvas.drawPath(basePath, basePaint);
        canvas.clipPath(basePath, Region.Op.UNION);
        canvas.drawPath(secondaryPath, secondaryPaint);
        canvas.drawPath(animationPath, animationPaint);

        if( isDraw ) {
            canvas.drawPath(replacePath, secondaryPaint);
        }

        super.onDraw(canvas);
    }

    public void setProgress(int value, int max) {
        value = value * STEP;

        if( value == progress ) {
            return;
        } else {
            this.max = max * STEP;
            boolean flag = value < progress;
            int remain = 0;
            bound = this.max / STEP;
            progress = value;

            if( progress > this.max ) {
                goalIndex = STEP;
                flag = true;
            } else {
                goalIndex = (progress / bound);
                remain = progress % bound;
            }

            if( value == 0 ) {
                reset();
                invalidate();
                return;
            } else {
                if( flag ) {
                    setupPath(remain);
                } else {
                    addPath(remain);
                }
            }
            update(progressPath);
        }
    }


    //  =====================================================================================

}
