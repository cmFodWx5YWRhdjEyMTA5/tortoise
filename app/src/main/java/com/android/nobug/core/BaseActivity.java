package com.android.nobug.core;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.nobug.manager.ActivityManager;
import com.android.nobug.manager.analytics.AnalyticsManager;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private Context mContext;

    //  =======================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initialize();

        //  log.show("activity : " + this.getLocalClassName() );
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().remove(this);
        super.onDestroy();
    }

    //  ========================================================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home: {
                finish();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

//  =============================================================================================

    @Override
    public int getLayoutContentView() {
        return 0;
    }

    @Override
    public void initialize() {
        setUp();
        createChildren();
        configureListener();
        setProperties();
    }

    @Override
    public void createChildren() {
    }

    @Override
    public void configureListener() {
    }

    @Override
    public void setProperties() {
    }

    @Override
    public void setUp() {
        if( getLayoutContentView() > 0 ) {
            setContentView(getLayoutContentView());
        }

        if( getSupportActionBar() != null ) {
            showSupportActionbar(true);
        }

        AnalyticsManager.getInstance().sendScreen(getContext(), getClass().getSimpleName());
        ActivityManager.getInstance().add(this);
    }

    //  =======================================================================================

    protected void showSupportActionbar(boolean flag) {
        if( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
            getSupportActionBar().setDisplayShowHomeEnabled(flag);
        }
    }

    //  =======================================================================================

    protected Context getContext() {
        if( mContext == null )
            return this;
        return mContext;
    }

}
