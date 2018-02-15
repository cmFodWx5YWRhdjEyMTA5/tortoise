package com.android.kreators.tortoise.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.event.AuthEvent;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.model.auth.AuthItemGroup;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.auth.sns.FacebookItem;
import com.android.nobug.core.BaseActivity;
import com.android.nobug.event.provider.BusProvider;
import com.android.nobug.manager.auth.AuthManager;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.log;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by rrobbie on 2017. 10. 18..
 */

public class BaseAuthActivity extends BaseActivity {

    private Collection<String> PERMISSINS = Arrays.asList("public_profile", "user_friends");

    protected FacebookItem facebookItem;

    //  =====================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void setProperties() {
        super.setProperties();

        try {
            facebookItem = (FacebookItem) getIntent().getSerializableExtra(IntentProperty.FACEBOOK_ITEM);
            AuthManager.getInstance().getFacebookManager().login(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  =====================================================================================

    protected void signIn() {
        //  TODO
    }

    protected void signUp() {
        //  TODO
    }

    protected void facebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, PERMISSINS);
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.NATIVE_ONLY);
    }

    protected void signUpPlus() {
        IntentFactory.snsSignUp(this, facebookItem);
    }

    protected void home(AuthEvent event) {
        try {
            AuthItemGroup group = (AuthItemGroup) event.data;
            AuthItem item = group.data;

            if( item.isRegister() ) {
                IntentFactory.home(getContext(), item );
                finish();
            } else {
                moveInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void moveInfo() {
        try {
            UserCacheItem userCacheItem = PreferenceFactory.getUserItem(this);

            log.show( ObjectUtil.toProperties(userCacheItem) );

            if( userCacheItem.authUserItem != null ) {
                if( userCacheItem.authUserItem.address_city == null ) {
                    IntentFactory.userInfo(this);
                    return;
                }
            } else {
                IntentFactory.userInfo(this);
                return;
            }

            if( userCacheItem.bankItem != null ) {
                if( userCacheItem.bankItem.bank_id == null ) {
                    IntentFactory.bankInfo(this);
                    return;
                }
            } else {
                IntentFactory.bankInfo(this);
                return;
            }

            IntentFactory.goalInfo(this);
        } catch (Exception e) {
            e.printStackTrace();
            IntentFactory.userInfo(this);
        }
    }

    protected void update(AuthEvent event) {
        log.show( "AuthEvent : " + ObjectUtil.toProperties(event) );
        AuthItemGroup group = (AuthItemGroup) event.data;

        try {
            if( event.type == EventType.ERROR ) {
                Toast.makeText(getContext(), group.message, Toast.LENGTH_SHORT).show();
                //  IntentFactory.intro(this);
                //  finish();
                return;
            } else if( event.type == EventType.FACEBOOK_SIGN_IN || event.type == EventType.EMAIL_SIGN_IN ) {
                home(event);
            } else if( event.type == EventType.FACEBOOK_SIGN_UP ) {
                signUpPlus();
            } else if( event.type == EventType.EMAIL_SIGN_UP ) {
                IntentFactory.userInfo(getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getText(R.string.network_error_message), Toast.LENGTH_SHORT).show();
            IntentFactory.intro(this);
            return;
        }

        finish();
    }

    //  =====================================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AuthManager.getInstance().getFacebookManager().getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }


}
