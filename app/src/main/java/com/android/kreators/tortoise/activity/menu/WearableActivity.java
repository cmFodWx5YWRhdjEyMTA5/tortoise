package com.android.kreators.tortoise.activity.menu;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.WearableDeviceAdapter;
import com.android.nobug.activity.PendingActivity;
import com.android.nobug.view.listview.BaseListView;

import java.util.ArrayList;

public class WearableActivity extends PendingActivity {

    //  ====================================================================================

    private final int[] images = new int[] {
            R.drawable.logo_fitbit
    };

    //  ====================================================================================

    private BaseListView listView;
    private WearableDeviceAdapter wearableDeviceAdapter;

    //  =====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_wearable;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        listView = (BaseListView) findViewById(R.id.listView);

    }

    @Override
    public void setProperties() {
        super.setProperties();

        setupAdapter();
    }

    @Override
    public void configureListener() {
        super.configureListener();

    }

    //  =====================================================================================

    private void setupAdapter() {
        ArrayList<Integer> items = new ArrayList<>();

        for (int i =0; i<images.length; i++) {
            items.add(images[i]);
        }

        wearableDeviceAdapter = new WearableDeviceAdapter(this, items);
        listView.setAdapter(wearableDeviceAdapter);
    }



}



