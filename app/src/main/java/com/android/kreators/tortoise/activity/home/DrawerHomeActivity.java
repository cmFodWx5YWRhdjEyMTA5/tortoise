package com.android.kreators.tortoise.activity.home;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by rrobbie on 2017. 4. 12..
 */

public class DrawerHomeActivity extends WrapperHomeActivity implements DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener {

    //  =====================================================================================

    protected ImageView imageView;
    protected TextView firstNameField;
    protected TextView lastNameField;
    protected TextView emailField;

    protected View header;

    protected int menuSelectedId = 0;

    //  =======================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        mGlideRequestManager = Glide.with(getContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        createHeader();
    }

    @Override
    public void setProperties() {
        super.setProperties();

        updateUser(authItem);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        navigationView.setNavigationItemSelectedListener(this);

        drawer.addDrawerListener(this);
        toggle.syncState();
    }

    //  =======================================================================================

    private void share() {
        //  FriendsManager.invite(this);

        try {
            String message = CacheManager.getInstance().getSettingItem().share.message;
            String url = CacheManager.getInstance().getSettingItem().share.url;

            String text = message + "\n" + url;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            Intent chooser = Intent.createChooser(intent, "share");
            startActivity(chooser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createHeader() {
        header = navigationView.getHeaderView(0);

        imageView = (ImageView) header.findViewById(R.id.imageView);
        firstNameField = (TextView) header.findViewById(R.id.firstNameField);
        lastNameField = (TextView) header.findViewById(R.id.lastNameField);
        emailField = (TextView) header.findViewById(R.id.emailField);
    }

    protected void updateUser(AuthItem item) {
        if (mGlideRequestManager != null) {
            if( item.profile != null ) {
                mGlideRequestManager.load(item.profile)
                        .override(300, 300).bitmapTransform(new CropCircleTransformation(getContext())).into(imageView);
            } else {
                mGlideRequestManager.load(R.drawable.logo)
                        .override(300, 300).bitmapTransform(new CropCircleTransformation(getContext())).into(imageView);
            }
        }
        firstNameField.setText( item.first_name );
        lastNameField.setText( item.last_name );
        emailField.setText( item.email );
    }

    private void showMenu(int id) {
        if( id == R.id.navAccount ) {
            IntentFactory.myAccount(this, authItem);
        } else if (id == R.id.navHistory) {
            IntentFactory.history(this, authItem.seq);
        } else if (id == R.id.navInvite) {
            share();
        } else if (id == R.id.navActivity) {
            IntentFactory.activity(this, authItem.seq);
        } else if (id == R.id.navWearable) {
            IntentFactory.wearable(this);
        }else if (id == R.id.navSetting) {
            IntentFactory.settings(this);
        }
    }

    //  =======================================================================================

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_deposit) {
            IntentFactory.addDeposit(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //  =======================================================================================

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        menuSelectedId = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void onDrawerSlide(View drawerView, float slideOffset) {}
    @Override public void onDrawerOpened(View drawerView) {}
    @Override public void onDrawerStateChanged(int newState) {}

    @Override
    public void onDrawerClosed(View drawerView) {
        if( menuSelectedId > 0 ) {
            showMenu(menuSelectedId);
        }

        menuSelectedId = 0;
    }



}