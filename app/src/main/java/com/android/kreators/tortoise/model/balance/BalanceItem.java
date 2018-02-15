package com.android.kreators.tortoise.model.balance;

import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 11/12/2017.
 */

public class BalanceItem extends BaseModel {

    private static final long serialVersionUID = 7986753469929430389L;

    //  ====================================================================================

    public long user_seq;
    public String target_date;

    public double withdraw_total;
    public double icon_deposit_total;
    public double manual_deposit_total;
    public double daily_deposit_total;
    public double saving_bonus_total;
    public double estimated_saving_bonus_total;
    public double pending_total;

    public BaseStepItem step;

    //  ====================================================================================



}
