package com.android.kreators.tortoise.model.history;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 05/12/2017.
 */

public class StartBalance extends BaseModel {

    private static final long serialVersionUID = 1682642304128756009L;

    //  =====================================================================================

    public double sum_daily_deposit_daily;
    public double sum_icon_deposit_daily;
    public double sum_manual_deposit_daily;
    public double sum_saving_bonus_daily;
    public double sum_withdraw_daily;

    //  =====================================================================================

    public double getBalance() {
        return sum_daily_deposit_daily + sum_icon_deposit_daily + sum_manual_deposit_daily + sum_saving_bonus_daily - sum_withdraw_daily;
    }

}
