package com.android.nobug.manager.auth;

import android.content.Context;
import android.widget.Toast;

import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.constants.RequestParameter;
import com.android.kreators.tortoise.event.AuthEvent;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.auth.AuthItemGroup;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.event.provider.BusProvider;
import com.android.nobug.util.AlertUtil;
import com.android.nobug.util.log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rrobbie on 2017. 4. 11..
 */
public class EmailManager {

    private static EmailManager instance;

    private Context mContext;

    //  =========================================================================================

    public static EmailManager getInstance() {
        if (instance == null) {
            instance = new EmailManager();
        }
        return instance;
    }

    //  =========================================================================================

    public void signIn(final Context context, final String email, final String password) {
        mContext = context;

        AlertUtil.showProgressDialog(mContext);

        RetrofitBuilder.withUnsafe(mContext).postSignIn( getParameter(email, password) ).enqueue(new Callback<AuthItemGroup>() {
            @Override
            public void onResponse(Call<AuthItemGroup> call, Response<AuthItemGroup> response) {
                success(response, EventType.EMAIL_SIGN_IN, email, password);
            }

            @Override
            public void onFailure(Call<AuthItemGroup> call, Throwable t) {
                log.show( "on failure : " + call.request().url().toString() + " / " + t.getLocalizedMessage() );
                fail();
            }
        });
    }

    public void signUp(Context context, final String email, final String password) {
        mContext = context;

        AlertUtil.showProgressDialog(mContext);

        RetrofitBuilder.withUnsafe(mContext).postSignUp( getParameter(email, password) ).enqueue(new Callback<AuthItemGroup>() {
            @Override
            public void onResponse(Call<AuthItemGroup> call, Response<AuthItemGroup> response) {
                success(response, EventType.EMAIL_SIGN_UP, email, password);
            }

            @Override
            public void onFailure(Call<AuthItemGroup> call, Throwable t) {
                log.show( "on failure : " + call.request().url().toString() + " / " + t.getLocalizedMessage() );
                fail();
            }
        });

    }

    // create params
    private HashMap<String, String> getParameter(String email, String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(RequestParameter.EMAIL, email);
        hashMap.put(RequestParameter.PASSWORD, password);
        return hashMap;
    }

    //  =====================================================================================

    private void success(Response<AuthItemGroup> response, int type, String email, String password) {
        AlertUtil.dismissProgressDialog();

        try {
            if( response.body() != null ) {
                AuthItemGroup group = (AuthItemGroup) response.body();

                if( group.code != 1 ) {
                    Toast.makeText(mContext, group.message, Toast.LENGTH_SHORT).show();
                    BusProvider.getInstance().post(new AuthEvent(EventType.ERROR, null));
                } else {
                    UserCacheItem userCacheItem = new UserCacheItem();
                    userCacheItem.user_type = group.data.user_type;
                    userCacheItem.email = email;
                    userCacheItem.password = password;
                    userCacheItem.auth_token = group.data.api_key;

                    PreferenceFactory.setUserItem(mContext, userCacheItem);

                    BusProvider.getInstance().post(new AuthEvent(type, group));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fail() {
        AlertUtil.dismissProgressDialog();
        BusProvider.getInstance().post(new AuthEvent(EventType.ERROR, null));
    }



}
