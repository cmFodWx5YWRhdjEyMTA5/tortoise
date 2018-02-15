package com.android.kreators.tortoise.activity.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.factory.FragmentFactory;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.fragment.home.HomeFragment;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.kreators.tortoise.sensor.walk.IWalkCallback;
import com.android.kreators.tortoise.service.WalkSensorService;
import com.android.kreators.tortoise.service.WalkSensorServiceBinder;
import com.android.kreators.tortoise.service.broadcast.AlarmBootReceiver;
import com.android.nobug.core.BaseActivity;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.StringUtil;
import com.android.nobug.view.viewpager.adapter.FragmentAdapter;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

/**
 * Created by rrobbie on 2017. 4. 12..
 */

public class WrapperHomeActivity extends BaseActivity {

    protected RequestManager mGlideRequestManager;

    protected ViewPager nonViewPager;
    protected FragmentAdapter fragmentAdapter;

    protected Toolbar toolbar;
    protected NavigationView navigationView;
    protected DrawerLayout drawer;
    protected ActionBarDrawerToggle toggle;

    protected HomeFragment homeFragment;

    protected WalkSensorService walkSensorService;
    protected WalkSensorServiceBinder walkSensorServiceBinder;
    protected ServiceConnection mConnection;

    protected AlarmManager alarmManager;
    protected PendingIntent alarmIntent;

    protected AuthItem authItem;

    protected UserCacheItem userCacheItem;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initialize() {
        super.initialize();

    }

    @Override
    public void createChildren() {
        super.createChildren();

        nonViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        try {
            authItem = (AuthItem) getIntent().getSerializableExtra(IntentProperty.AUTH_ITEM);

            if( authItem.isFacebook() )
                userCacheItem = PreferenceFactory.getUserItem(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupPager();
    }

    //  ======================================================================================

    private void setupPager() {
        Bundle bundle = new Bundle();
        bundle.putLong(IntentProperty.SEQ, authItem.seq);
        bundle.putSerializable(IntentProperty.FACEBOOK_ITEM, userCacheItem);

        ArrayList<BaseFragment> items = FragmentFactory.getMain();
        homeFragment = (HomeFragment) items.get(0);
        homeFragment.setArguments(bundle);
        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager(), items);
        nonViewPager.setAdapter(fragmentAdapter);
    }

    private void setupAlarmManager() {
        int repeatTime = CacheManager.getInstance().getSettingItem().step_cycle * 1000;
        Intent intent = new Intent(getContext(), AlarmBootReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), repeatTime, alarmIntent);
    }

    private void setupAlarmReceiver() {
        ComponentName receiver = new ComponentName(getContext(), AlarmBootReceiver.class);
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);  // 비활성 COMPONENT_ENABLED_STATE_DISABLED
    }

    //  ======================================================================================

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getContext(), WalkSensorService.class));

        if( mConnection != null ) {
            unbindService(mConnection);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        defineServiceConnection();
        bindService(new Intent(this, WalkSensorService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    //  =======================================================================================

    protected void defineServiceConnection() {
        mConnection = new ServiceConnection() {
            @Override public void onServiceDisconnected(ComponentName name) {}

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                startService(new Intent(getContext(), WalkSensorService.class));
                walkSensorServiceBinder = (WalkSensorServiceBinder) service;
                walkSensorService = walkSensorServiceBinder.getService();
                walkSensorService.start(iWalkCallback);

                setupAlarmReceiver();
                setupAlarmManager();
            }

        };
    }

    //  =======================================================================================

    IWalkCallback iWalkCallback = new IWalkCallback() {
        @Override
        public void update(int value) {
            BaseStepItem item = PreferenceFactory.getCurrentStepItem(getContext());
            item.target_date = StringUtil.getTodayDate();
            item.step = value;
            boolean isUpdated = CacheManager.getInstance().save(getContext(), item);
            homeFragment.setProgress();

            if( isUpdated ) {
                walkSensorService.reset();
            }
        }
    };



}
