package com.android.nobug.manager.auth;

import android.content.Context;
import android.widget.Toast;

import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.event.AuthEvent;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.auth.AuthItemGroup;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.auth.sns.FacebookItem;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.event.provider.BusProvider;
import com.android.nobug.util.log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacebookManager {

    private Context mContext;

    private static FacebookManager instance;
    private CallbackManager facebookCallbackManager = CallbackManager.Factory.create();
    private FacebookItem facebookItem;

    //  =========================================================================================

    public static FacebookManager getInstance() {
        if (instance == null) {
            instance = new FacebookManager();
        }
        return instance;
    }

    public FacebookItem getItem() {
        return facebookItem;
    }

    public CallbackManager getCallbackManager() {
        return facebookCallbackManager;
    }

    //  =========================================================================================

    public void login(final Context context) {
        mContext = context;
        setupCallback(context);
    }

    //  =====================================================================================

    public void signIn(String id, String email, String firstName, String lastName, String image) {
        try {
            RetrofitBuilder.withUnsafe(mContext).postFacebook(id, email, firstName, lastName, image).enqueue(new Callback<AuthItemGroup>() {
                @Override
                public void onResponse(Call<AuthItemGroup> call, Response<AuthItemGroup> response) {
                    success(response);
                }

                @Override
                public void onFailure(Call<AuthItemGroup> call, Throwable t) {
                    log.show( "on failure : " + call.request().url().toString() + " / " + t.getLocalizedMessage() );
                    fail();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postInclude(int seq, boolean flag) {
        try {
            int type = flag == true ? 1 : 0;
            RetrofitBuilder.withUnsafe(mContext).postFacebookInclude(seq, type).enqueue(new Callback<AuthItemGroup>() {
                @Override
                public void onResponse(Call<AuthItemGroup> call, Response<AuthItemGroup> response) {
                    //  log.show( "on response : " + ObjectUtil.toProperties(response.body()) );
                }

                @Override
                public void onFailure(Call<AuthItemGroup> call, Throwable t) {
                    log.show( "on failure : " + call.request().url().toString() + " / " + t.getLocalizedMessage() );
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ======================================================================================

    private void success(Response<AuthItemGroup> response) {
        try {
            AuthItemGroup group = (AuthItemGroup) response.body();

            int type = group.data.isRegister() ? EventType.FACEBOOK_SIGN_IN : EventType.FACEBOOK_SIGN_UP;
            UserCacheItem userCacheItem = new UserCacheItem();
            userCacheItem.facebook_token = getFacebookItem().token;
            userCacheItem.auth_token = group.data.api_key;
            userCacheItem.user_type = group.data.user_type;
            userCacheItem.facebook_id = group.data.facebook_id;
            group.data.profile = getFacebookItem().profile;

            PreferenceFactory.setUserItem(mContext, userCacheItem);

            BusProvider.getInstance().post(new AuthEvent(type, group));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private void fail() {
        BusProvider.getInstance().post(new AuthEvent(EventType.ERROR, null));
    }

    private FacebookItem getFacebookItem() {
        if( facebookItem == null )
            facebookItem = new FacebookItem();
        return facebookItem;
    }

    private void setupCallback(final Context context) {
        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {
                try {
                    UserCacheItem userCacheItem = new UserCacheItem();
                    facebookItem = getFacebookItem();
                    userCacheItem.facebook_id = facebookItem.id = loginResult.getAccessToken().getUserId();
                    userCacheItem.facebook_token = facebookItem.token = loginResult.getAccessToken().getToken();
                    facebookItem.profile = new URL("https://graph.facebook.com/" + facebookItem.id + "/picture?width=300&height=300").toString();

                    PreferenceFactory.setUserItem(mContext, userCacheItem);
                    signIn(facebookItem.id, null, null, null, facebookItem.profile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {
                log.show("facebook onCancel : ");
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(context, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                log.show("facebook onError : " + exception.toString());
                fail();
            }

        });
    }


}

