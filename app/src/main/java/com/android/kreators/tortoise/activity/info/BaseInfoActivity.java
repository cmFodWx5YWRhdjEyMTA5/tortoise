package com.android.kreators.tortoise.activity.info;

import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.activity.auth.BaseAuthActivity;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.event.IndexEvent;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.auth.AuthUserItem;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.log;
import com.android.nobug.view.viewpager.adapter.FragmentAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by rrobbie on 21/12/2017.
 */

public abstract class BaseInfoActivity extends BaseAuthActivity implements ViewPager.OnPageChangeListener {


    protected ViewPager viewPager;
    protected FragmentAdapter fragmentAdapter;

    protected Menu menu;

    protected ArrayList<BaseFragment> fragments;
    protected int fragmentCount = 0;

    //  =====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_base_info;
    }

    //  =====================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        try {
            UserCacheItem userCacheItem = PreferenceFactory.getUserItem(this);

            if( userCacheItem.authUserItem == null ) {
                userCacheItem.authUserItem = new AuthUserItem();
                PreferenceFactory.setUserItem(this, userCacheItem);
            }

            fragments = getFragments();
            fragmentCount = fragments.size();
            fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager(), fragments);
            viewPager.setAdapter(fragmentAdapter);
            viewPager.setOffscreenPageLimit(fragmentCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configureListener() {
        super.configureListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                prev();
                return true;

            case R.id.action_next:
                next();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //  =====================================================================================

    protected void prev() {
        int current = viewPager.getCurrentItem();
        if( current == 0 ) {
            finish();
        } else {
            viewPager.setCurrentItem(current-1);
        }
    }

    protected void next() {
        log.show( "activity next : " );
        int current = viewPager.getCurrentItem() + 1;

        if( current < fragmentCount ) {
            viewPager.setCurrentItem(current);
        }
    }

    //  =====================================================================================

    protected ArrayList<BaseFragment> getFragments() {
        //  TODO
        return null;
    }

    protected int getEventType() {
        //  TODO
        return 0;
    }

    protected void pageSelected(int position) {
        // TODO
    }

    //  =====================================================================================

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        pageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    //  =====================================================================================

    @Subscribe
    public void OnIndexEvent(IndexEvent event) {
        if( event.type == getEventType() )
            viewPager.setCurrentItem( event.type );
    }


}
