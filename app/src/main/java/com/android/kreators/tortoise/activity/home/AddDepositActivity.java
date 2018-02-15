package com.android.kreators.tortoise.activity.home;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.GoalDepositAdapter;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.config.IconItem;
import com.android.nobug.activity.PendingActivity;
import com.android.nobug.util.log;

import java.util.ArrayList;

public class AddDepositActivity extends PendingActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private GoalDepositAdapter goalDepositAdapter;

    private ArrayList<IconItem> items;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_add_deposit;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        gridView = (GridView) findViewById(R.id.gridView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        items = new ArrayList<IconItem>( CacheManager.getInstance().getSettingItem().emojies );
        goalDepositAdapter = new GoalDepositAdapter(this, items);
        gridView.setAdapter(goalDepositAdapter);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        gridView.setOnItemClickListener(this);
    }

    //  ======================================================================================

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        log.show( "on item click : " + position );



    }


}
