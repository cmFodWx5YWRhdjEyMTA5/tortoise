package com.android.kreators.tortoise.activity.home;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.fragment.home.daily.MyStepFragment;
import com.android.kreators.tortoise.fragment.home.friend.FriendStepFragment;
import com.android.kreators.tortoise.manager.CacheManager;

public class HomeActivity extends DrawerHomeActivity implements View.OnClickListener {

    private Button stepButton;
    private Button friendsButton;
    private MyStepFragment myStepFragment;
    private FriendStepFragment friendStepFragment;
    private FrameLayout layer;
    private View currentView;

    private int DURATION = 200;

    private boolean isStepUp = true;
    private boolean isFriendUp = true;

    //  ======================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        friendsButton = (Button) findViewById(R.id.friendsButton);
        stepButton = (Button) findViewById(R.id.stepButton);

        myStepFragment = (MyStepFragment) getSupportFragmentManager().findFragmentById(R.id.myStepFragment);
        friendStepFragment = (FriendStepFragment) getSupportFragmentManager().findFragmentById(R.id.friendStepFragment);

        layer = (FrameLayout) findViewById(R.id.layer);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        setUpSliding();
    }

    @Override
    public void configureListener() {
        super.configureListener();

        friendsButton.setOnClickListener(this);
        stepButton.setOnClickListener(this);
        layer.setOnClickListener(this);
    }

    //  ======================================================================================

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        homeFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //  ======================================================================================

    private void setUpSliding() {
        myStepFragment.getView().setVisibility(View.INVISIBLE);
        friendStepFragment.getView().setVisibility(View.INVISIBLE);

        myStepFragment.getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                myStepFragment.getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                step();
            }
        });

        friendStepFragment.getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                friendStepFragment.getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                friend();
            }
        });
    }

    private void step() {
        if( isStepUp ) {
            slideDown(myStepFragment.getView());
        } else {
            slideUp(myStepFragment.getView());
        }
    }

    private void friend() {
        if( isFriendUp ) {
            slideDown(friendStepFragment.getView());
        } else {
            slideUp(friendStepFragment.getView());
        }
    }

    private void slideUp(final View view){
        view.setVisibility(View.VISIBLE);
        layer.setVisibility(View.VISIBLE);

        view.animate().translationYBy(view.getHeight()).translationY(0).setDuration(DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if( isMyStepFragment(view) ) {
                    myStepFragment.setItem(authItem.seq);
                } else {
                    friendStepFragment.setItem(userCacheItem);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        if( isMyStepFragment(view) ) {
            isStepUp = true;
        } else {
            isFriendUp = true;
        }

        currentView = view;
    }

    private void slideDown(View view){
        layer.setVisibility(View.GONE);

        view.animate().translationYBy(0).translationY(view.getHeight()).setDuration(DURATION);

        if( isMyStepFragment(view) ) {
            isStepUp = false;
        } else {
            isFriendUp = false;
        }
        currentView = view;
    }

    private boolean isMyStepFragment(View view) {
        return ( view == myStepFragment.getView() );
    }

    //  ======================================================================================

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friendsButton:
                friend();
                break;

            case R.id.stepButton:
                step();
                break;

            case R.id.layer:
                slideDown(currentView);
                break;
        }
    }




}
