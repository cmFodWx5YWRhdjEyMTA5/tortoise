package com.android.nobug.manager.auth;

import android.content.Context;

import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.facebook.login.LoginManager;

/**
 * Created by rrobbie on 2017. 4. 11..
 */

public class AuthManager {

    private static AuthManager instance;

    //  =========================================================================================

    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    //  =========================================================================================

    public boolean isLogin(Context context) {
        UserCacheItem item = PreferenceFactory.getUserItem(context);
        boolean isLogin = ( item.email != null && item.password != null ) || ( item.facebook_token != null );
        return isLogin;
    }

    public void signOut(Context context) {
        LoginManager.getInstance().logOut();
        PreferenceFactory.setUserItem(context, new UserCacheItem());
    }

    //  =========================================================================================

    public FacebookManager getFacebookManager() {
        return FacebookManager.getInstance();
    }

    public EmailManager getEmailManager() {
        return EmailManager.getInstance();
    }

    //  =========================================================================================

}
