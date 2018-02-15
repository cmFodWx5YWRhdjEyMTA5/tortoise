package com.android.kreators.tortoise.manager;

import android.content.Context;

import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.balance.BalanceItem;
import com.android.kreators.tortoise.model.bank.BankItem;
import com.android.kreators.tortoise.model.config.IconItem;
import com.android.kreators.tortoise.model.config.SettingItem;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.kreators.tortoise.model.version.VersionGroup;
import com.android.kreators.tortoise.model.version.VersionItem;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.util.log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CacheManager {

    private Context mContext;
    private static CacheManager uniqueInstance;

    private SettingItem settingItem;
    private VersionItem versionItem;
    private BalanceItem balanceItem;
    private BankItem tortoiseBankItem;
    private BankItem bankItem;

    //  =======================================================================================

    public void setup(Context context) {
        mContext = context;
        settingItem = new SettingItem();
        versionItem = new VersionItem();
        balanceItem = new BalanceItem();
        tortoiseBankItem = new BankItem();
        bankItem = new BankItem();
    }

    public static CacheManager getInstance() {
        if( uniqueInstance == null ) {
            uniqueInstance = new CacheManager();
        }
        return uniqueInstance;
    }

    //  =======================================================================================

    public void close() {
        //  TODO    마감처리. 랜덤 20분내. 서버 전송
        //  TODO    previous step item을 서버로 마감처리 진행후 정상 반환시 해당 previous step item을 비운다.

        BaseStepItem previousStepItem = PreferenceFactory.getPreviousStepItem(mContext);
        //  TODO    Request 마감 api

        log.show( "close api requested..." );
    }

    public boolean save(Context context, BaseStepItem item) {
        mContext = context;

        try {
            if( item != null ) {
                if( item.target_date == null ) {
                    PreferenceFactory.setCurrentStepItem(mContext, item);
                } else {
                    BaseStepItem currentItem = PreferenceFactory.getCurrentStepItem(context);

                    if( currentItem != null ) {
                        if( currentItem.target_date.equals( item.target_date ) ) {
                            PreferenceFactory.setCurrentStepItem(mContext, item);
                        } else {
                            //  TODO    마감되지 않은 current step item -> previous step item 이관.
                            PreferenceFactory.setPreviousStepItem(mContext, currentItem);

                            //  TODO    신규 모델 생성
                            item.step = 0;
                            PreferenceFactory.setCurrentStepItem(mContext, item);

                            close();

                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public BaseStepItem getCurrentItem(Context context) {
        return PreferenceFactory.getCurrentStepItem(context);
    }

    public BaseStepItem getPreviousItem(Context context) {
        return PreferenceFactory.getPreviousStepItem(context);
    }

    //  =======================================================================================

    public SettingItem getSettingItem() {
        return settingItem;
    }

    public void setSettingItem(SettingItem value) {
        this.settingItem = value;
        PreferenceFactory.setSettingItem(mContext, value);
    }

    //  =======================================================================================

    public VersionItem getVersionItem() {
        return versionItem;
    }

    public void setVersionItem(VersionItem value) {
        this.versionItem = value;
    }

    //  =======================================================================================

    public BankItem getTortoiseBankItem() {
        return tortoiseBankItem;
    }

    public void setTortoiseBankItem(BankItem value) {
        this.tortoiseBankItem = value;
    }

    //  =======================================================================================

    public BankItem getBankItem() {
        return bankItem;
    }

    public void setBankItem(BankItem value) {
        this.bankItem = value;
    }

    //  =======================================================================================

    public BalanceItem getBalanceItem() {
        return balanceItem;
    }

    public void setBalanceItem(BalanceItem value) {
        this.balanceItem = value;
    }

    //  =======================================================================================

}
