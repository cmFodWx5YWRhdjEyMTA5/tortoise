package com.android.kreators.tortoise.model.history;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 05/12/2017.
 */

public class HistoryItem extends BaseModel {

    private static final long serialVersionUID = -196528345088898171L;

    //  ====================================================================================

    public String target_date;

    public double daily_deposit_daily;
    public double icon_deposit_daily;
    public double manual_deposit_daily;
    public double saving_bonus_daily;
    public double withdraw_daily;

    //  ====================================================================================

    public double getBalance() {
        return daily_deposit_daily + icon_deposit_daily + manual_deposit_daily + saving_bonus_daily - withdraw_daily;
    }

}