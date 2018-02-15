package com.android.kreators.tortoise.sensor.walk;

import com.android.nobug.util.log;

/**
 * Created by rrobbie on 2017. 9. 19..
 */

public class WalkCounter implements IWalkCallback {

    private int count = 0;
    private int mCount = 0;
    private IWalkValueCallback iWalkValueCallback;
    private long timeOfLastPeak = 0;
    private long timeOfThisPeak = 0;
    private WalkDetector walkDetector;

    //  ======================================================================================

    public WalkCounter() {
        walkDetector = new WalkDetector();
        walkDetector.initListener(this);
    }

    public WalkDetector getWalkDetector(){
        return walkDetector;
    }

    //  ======================================================================================

    @Override
    public void update(int value) {
        this.timeOfLastPeak = this.timeOfThisPeak;
        this.timeOfThisPeak = System.currentTimeMillis();

        if (this.timeOfThisPeak - this.timeOfLastPeak <= 3000L) {
            if (this.count < 9) {
                this.count++;
            } else if (this.count == 9) {
                this.count++;
                this.mCount += this.count;
                notifyListener();
            } else {
                this.mCount++;
                notifyListener();
            }
        } else {
            this.count = 1;
        }
    }

    //  ======================================================================================

    public void initListener(IWalkValueCallback iWalkValueCallback) {
        this.iWalkValueCallback = iWalkValueCallback;
    }

    public void notifyListener() {
        if (this.iWalkValueCallback != null)
            this.iWalkValueCallback.changed(this.mCount);
    }

    public void setSteps(int initValue) {
        this.mCount = initValue;
        this.count = 0;
        timeOfLastPeak = 0;
        timeOfThisPeak = 0;
        notifyListener();
    }

    //  ======================================================================================


}