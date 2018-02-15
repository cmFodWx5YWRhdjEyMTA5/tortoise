package com.android.kreators.tortoise.factory;

import android.content.Context;

import com.android.kreators.tortoise.constants.property.PreferenceProperty;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.config.SettingItem;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.kreators.tortoise.model.version.VersionItem;
import com.android.nobug.util.PreferenceUtil;
import com.android.nobug.util.StringUtil;
import com.google.gson.Gson;

/**
 * Created by rrobbie on 2017. 9. 21..
 */

public class PreferenceFactory {

    //  ======================================================================================

    public static BaseStepItem getCurrentStepItem(Context context){
        String json = PreferenceUtil.getValue(context, PreferenceProperty.CURRENT_STEP_ITEM, null);
        BaseStepItem item = new BaseStepItem();
        if( json != null ) {
            item = new Gson().fromJson(json, BaseStepItem.class);
        } else {
            item.target_date = StringUtil.getTodayDate();
        }
        return item;
    }

    public static void setCurrentStepItem(Context context, BaseStepItem item){
        Gson gson = new Gson();
        if( item == null ) {
            item = new BaseStepItem();
            item.target_date = StringUtil.getTodayDate();
        }
        PreferenceUtil.put(context, PreferenceProperty.CURRENT_STEP_ITEM, gson.toJson(item));
    }

    public static BaseStepItem getPreviousStepItem(Context context){
        String json = PreferenceUtil.getValue(context, PreferenceProperty.PREVIOUS_STEP_ITEM, null);
        BaseStepItem item = new BaseStepItem();
        if( json != null ) {
            item = new Gson().fromJson(json, BaseStepItem.class);
        } else {
            item.target_date = StringUtil.getTodayDate();
        }
        return item;
    }

    public static void setPreviousStepItem(Context context, BaseStepItem item){
        Gson gson = new Gson();
        if( item == null ) {
            item = new BaseStepItem();
            item.target_date = StringUtil.getTodayDate();
        }
        PreferenceUtil.put(context, PreferenceProperty.PREVIOUS_STEP_ITEM, gson.toJson(item));
    }

    //  ======================================================================================

    public static UserCacheItem getUserItem(Context context){
        String json = PreferenceUtil.getValue(context, PreferenceProperty.USER_CACHE_ITEM, null);
        UserCacheItem item = new UserCacheItem();
        if( json != null ) {
            item = new Gson().fromJson(json, UserCacheItem.class);
        }
        return item;
    }

    public static void setUserItem(Context context, UserCacheItem value){
        Gson gson = new Gson();
        PreferenceUtil.put(context, PreferenceProperty.USER_CACHE_ITEM, gson.toJson(value));
    }

    //  ======================================================================================

    public static SettingItem getSettingItem(Context context){
        String json = PreferenceUtil.getValue(context, PreferenceProperty.SETTING_ITEM, null);
        SettingItem item = new SettingItem();
        if( json != null ) {
            item = new Gson().fromJson(json, SettingItem.class);
        }
        return item;
    }

    public static void setSettingItem(Context context, SettingItem value){
        Gson gson = new Gson();
        PreferenceUtil.put(context, PreferenceProperty.SETTING_ITEM, gson.toJson(value));
    }

}



