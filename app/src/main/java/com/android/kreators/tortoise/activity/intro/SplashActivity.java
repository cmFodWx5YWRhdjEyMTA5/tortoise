package com.android.kreators.tortoise.activity.intro;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.kreators.tortoise.event.AuthEvent;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.nobug.manager.auth.AuthManager;
import com.android.nobug.util.log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.squareup.otto.Subscribe;

public class SplashActivity extends BaseSplashActivity {


    // ========================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // ========================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

    }

    @Override
    public void setProperties() {
        super.setProperties();

        setDynamicLink();
    }

    @Override
    public void configureListener() {
        super.configureListener();
    }

    //  ======================================================================================

    private void setDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        if (pendingDynamicLinkData != null) {
                            Uri deepLink = pendingDynamicLinkData.getLink();
                            //  TODO    각 scheme 처리

                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        log.show("getDynamicLink:onFailure");
                    }
                });
    }

    //  ======================================================================================

    @Override
    protected void next() {
        try {
            if (AuthManager.getInstance().isLogin(this)) {
                UserCacheItem userCacheItem = PreferenceFactory.getUserItem(this);

                if( userCacheItem.isFacebook() ) {
                    AuthManager.getInstance().getFacebookManager().login(this);
                    facebook();
                } else {
                    AuthManager.getInstance().getEmailManager().signIn(this, userCacheItem.email, userCacheItem.password);
                }
            } else {
                IntentFactory.intro(this);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  =======================================================================================

    @Subscribe
    public void OnAuthEvent(AuthEvent event) {
        update(event);
    }

}

