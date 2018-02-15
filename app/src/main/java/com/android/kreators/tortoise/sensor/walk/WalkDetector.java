package com.android.kreators.tortoise.sensor.walk;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * Created by rrobbie on 2017. 9. 19..
 */

public class WalkDetector implements SensorEventListener {

    private float[] oriValues = new float[3];
    private final int ValueNum = 4;
    private float[] tempValue = new float[ValueNum];
    private int tempCount = 0;
    private boolean isDirectionUp = false;
    private int continueUpCount = 0;
    private int continueUpFormerCount = 0;
    private boolean lastStatus = false;
    private float peakOfWave = 0;
    private float valleyOfWave = 0;
    private long timeOfThisPeak = 0;
    private long timeOfLastPeak = 0;
    private long timeOfNow = 0;
    private float gravityNew = 0;
    private float gravityOld = 0;
    private final float InitialValue = (float) 1.3;
    private float ThreadValue = (float) 2.0;
    private int TimeInterval = 250;
    private IWalkCallback iWalkCallback;

    //  =======================================================================================

    @Override
    public void onSensorChanged(SensorEvent event) {
        for (int i = 0; i < 3; i++) {
            oriValues[i] = event.values[i];
        }
        gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
                + oriValues[1] * oriValues[1] + oriValues[2] * oriValues[2]);
        detectorNewStep(gravityNew);

        Log.e("rrobbie", "on sensor changed : " + gravityNew );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }

    public void initListener(IWalkCallback iWalkCallback) {
        this.iWalkCallback = iWalkCallback;
    }

    public void detectorNewStep(float values) {
        if (gravityOld == 0) {
            gravityOld = values;
        } else {
            if (detectorPeak(values, gravityOld)) {
                timeOfLastPeak = timeOfThisPeak;
                timeOfNow = System.currentTimeMillis();
                if (timeOfNow - timeOfLastPeak >= TimeInterval && (peakOfWave - valleyOfWave >= ThreadValue)) {
                    timeOfThisPeak = timeOfNow;

                    //  iWalkCallback.update();
                }
                if (timeOfNow - timeOfLastPeak >= TimeInterval
                        && (peakOfWave - valleyOfWave >= InitialValue)) {
                    timeOfThisPeak = timeOfNow;
                    ThreadValue = peakValleyThread(peakOfWave - valleyOfWave);
                }
            }
        }
        gravityOld = values;
    }

    public boolean detectorPeak(float newValue, float oldValue) {
        lastStatus = isDirectionUp;
        if (newValue >= oldValue) {
            isDirectionUp = true;
            continueUpCount++;
        } else {
            continueUpFormerCount = continueUpCount;
            continueUpCount = 0;
            isDirectionUp = false;
        }

        if (!isDirectionUp && lastStatus
                && (continueUpFormerCount >= 2 || oldValue >= 20)) {
            peakOfWave = oldValue;
            return true;
        } else if (!lastStatus && isDirectionUp) {
            valleyOfWave = oldValue;
            return false;
        } else {
            return false;
        }
    }

    public float peakValleyThread(float value) {
        float tempThread = ThreadValue;
        if (tempCount < ValueNum) {
            tempValue[tempCount] = value;
            tempCount++;
        } else {
            tempThread = averageValue(tempValue, ValueNum);
            for (int i = 1; i < ValueNum; i++) {
                tempValue[i - 1] = tempValue[i];
            }
            tempValue[ValueNum - 1] = value;
        }
        return tempThread;

    }

    public float averageValue(float value[], int n) {
        float ave = 0;
        for (int i = 0; i < n; i++) {
            ave += value[i];
        }
        ave = ave / ValueNum;
        if (ave >= 8)
            ave = (float) 4.3;
        else if (ave >= 7 && ave < 8)
            ave = (float) 3.3;
        else if (ave >= 4 && ave < 7)
            ave = (float) 2.3;
        else if (ave >= 3 && ave < 4)
            ave = (float) 2.0;
        else {
            ave = (float) 1.3;
        }
        return ave;
    }

}