package com.android.kreators.tortoise.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.IBinder;

import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.sensor.walk.IWalkCallback;
import com.android.kreators.tortoise.sensor.walk.IWalkValueCallback;
import com.android.kreators.tortoise.sensor.walk.WalkCounter;
import com.android.nobug.util.DeviceInfoUtil;
import com.android.nobug.util.log;

public class WalkSensorService extends Service implements SensorEventListener {

    private Context mContext;

    private IWalkCallback iWalkCallback;
    private WalkSensorServiceBinder walkSensorServiceBinder;
    private SensorManager sensorManager;
    private WalkCounter walkCounter;

    private boolean hasRecord = false;
    private int hasStepCount = 0;
    private int previousStepCount = 0;
    private int steps = 0;

    private int DURATION = 0;

    private TimeCount time;

    private static int stepSensorType = -1;

    //  =====================================================================================


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public IBinder onBind(Intent intent) {
        walkSensorServiceBinder = new WalkSensorServiceBinder(this, this);
        return walkSensorServiceBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        try {
            sensorManager.unregisterListener(this);
            walkSensorServiceBinder = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //  =======================================================================================

    private void startDetector() {
        new Thread(new Runnable() {
            public void run() {
                if (sensorManager != null) {
                    sensorManager = null;
                }
                sensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);

                if (DeviceInfoUtil.getOsSdkInt() >= 19) {
                    addCountStepListener();
                } else {
                    addBasePedometerListener();
                }
            }
        }).start();
    }

    private void addCountStepListener() {
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (countSensor != null) {
            stepSensorType = Sensor.TYPE_STEP_COUNTER;
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else if (detectorSensor != null) {
            stepSensorType = Sensor.TYPE_STEP_DETECTOR;
            sensorManager.registerListener(this, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            addBasePedometerListener();
        }
    }

    private void addBasePedometerListener() {
        walkCounter = new WalkCounter();
        walkCounter.setSteps(steps);

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean isAvailable = sensorManager.registerListener(walkCounter.getWalkDetector(), sensor, SensorManager.SENSOR_DELAY_UI);
        walkCounter.initListener(new IWalkValueCallback() {
            @Override
            public void changed(int value) {
                steps = value;
                updateNotification();
            }
        });

        if (isAvailable) {
            log.show( "is available" );
        } else {
            log.show( "is available" );
        }
    }

    private void updateNotification() {
         if( iWalkCallback != null ) {
             iWalkCallback.update(steps);
         }
    }

    public void start(IWalkCallback iWalkCallback) {
        this.iWalkCallback = iWalkCallback;
        startDetector();
    }

    public void reset() {
        hasRecord = false;
        hasStepCount = 0;
        previousStepCount = 0;
        steps = 0;

        log.show( "" );
    }

    //  =======================================================================================

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (stepSensorType == Sensor.TYPE_STEP_COUNTER) {
            int tempStep = (int) event.values[0];
            int thisStepCount = 0;
            int thisStep = 0;

            if (!hasRecord) {
                hasRecord = true;
                hasStepCount = tempStep;
                steps = CacheManager.getInstance().getCurrentItem(this).step;
            } else {
                thisStepCount = tempStep - hasStepCount;
                thisStep = thisStepCount - previousStepCount;
                steps += (thisStep);
                previousStepCount = thisStepCount;
            }
        } else if (stepSensorType == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0] == 1.0) {
                steps++;
            }
        }

        updateNotification();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void startTimeCount() {
        if (time == null) {
            time = new TimeCount(DURATION, 1000);
        }
        time.start();
    }


    //  =====================================================================================

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            time.cancel();
            //  save();
            startTimeCount();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

    }

}