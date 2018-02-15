package com.android.kreators.tortoise.activity.intro;

import android.content.DialogInterface;

import com.android.kreators.tortoise.BuildConfig;
import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.activity.auth.BaseAuthActivity;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.config.SettingItem;
import com.android.kreators.tortoise.model.version.VersionGroup;
import com.android.kreators.tortoise.model.version.VersionItem;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.helper.ActionHelper;
import com.android.nobug.util.AlertUtil;
import com.android.nobug.util.DeviceInfoUtil;
import com.android.nobug.util.VersionUtil;
import com.android.nobug.util.log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseSplashActivity extends BaseAuthActivity implements Callback {

    protected VersionGroup itemGroup;

    //  ======================================================================================

    @Override
    public void setUp() {
        super.setUp();

        CacheManager.getInstance().setup(this);
        requestVersion();
    }

    //  ======================================================================================

    private DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            ActionHelper.showMarket(getContext());
            finish();
        }
    };

    //  =====================================================================================

    protected void requestVersion() {
        RetrofitBuilder.with(this, BuildConfig.CDN_HOST).getVersion().enqueue(this);
    }

    protected void requestSetting() {
        SettingItem preferenceSettingItem = PreferenceFactory.getSettingItem(this);

        if( preferenceSettingItem.setting_version < itemGroup.setting_version ) {
            RetrofitBuilder.withUnsafe(this).getSetting().enqueue(this);
        } else {
            CacheManager.getInstance().setSettingItem(preferenceSettingItem);
            next();
        }
    }

    protected void updateCheck(VersionItem item) {
        String title = getContext().getString(R.string.version_update);
        String message = getContext().getString(R.string.need_app_update_desc);
        boolean force = item.toBoolean(item.force);

        if(force) {
            AlertUtil.alertPositive(this, title, message, null, false, okListener);
        } else {
            AlertUtil.alertNegative(this, title, message, null, null, false, okListener, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    next();
                }
            });
        }
    }

    protected void updateVersion(Response response) {
        try {
            itemGroup = (VersionGroup) response.body();
            VersionItem versionItem = null;

            int appVersionCode = DeviceInfoUtil.getAppVersionCode(this);
            int size = itemGroup.histories.size();

            if( size > 0 ) {
                int currentIndex = size - 1;
                versionItem = itemGroup.histories.get(currentIndex);

                itemGroup.current = itemGroup.histories.get(currentIndex).version;
                itemGroup.min = itemGroup.histories.get(0).version;

                for (VersionItem item : itemGroup.histories) {
                    if( item.version == appVersionCode ) {
                        if (item.isMainTenance() ) {
                            maintenance();
                            return;
                        } else {
                            CacheManager.getInstance().setVersionItem(item);
                        }
                        break;
                    }
                }
            }

            if (VersionUtil.compareVersionCode(appVersionCode, itemGroup.current)) {
                updateCheck(versionItem);
            } else {
                requestSetting();
            }
        } catch (Exception e) {
            e.printStackTrace();
            requestSetting();
        }
    }

    private void updateSetting(Response response) {
        try {
            SettingItem settingItem = (SettingItem) response.body();
            int size = settingItem.interest_rates.size() - 1;
            settingItem.interest_rate = settingItem.interest_rates.get(size).interest_rate;
            settingItem.setting_version = itemGroup.setting_version;
            CacheManager.getInstance().setSettingItem(settingItem);
            next();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void next() {
    }

    protected void maintenance() {
        String title = getContext().getString(R.string.maintenance_title);
        String message = getContext().getString(R.string.maintenance_description);
        AlertUtil.alert(this, title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    //  =======================================================================================

    @Override
    public void onResponse(final Call call, Response response) {
        if( response.body() instanceof VersionGroup) {
            updateVersion(response);
        } else if (response.body() instanceof SettingItem){
            updateSetting(response);
        }
    }

    @Override
    public void onFailure(final Call call, Throwable t) {
        log.show("onFailure : " + t.getLocalizedMessage() + " / " + call.request().url().toString() );
        SettingItem preferenceSettingItem = PreferenceFactory.getSettingItem(this);
        CacheManager.getInstance().setSettingItem(preferenceSettingItem);
        next();
    }



}