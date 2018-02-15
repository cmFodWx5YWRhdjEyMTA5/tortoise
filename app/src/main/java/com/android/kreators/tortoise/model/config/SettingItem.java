package com.android.kreators.tortoise.model.config;

import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 2017. 8. 22..
 */

public class SettingItem extends BaseModel {


    private static final long serialVersionUID = 3857435023578350088L;

    //  ======================================================================================


    public double interest_rate;

    public int setting_version = -1;

    public int min_deposit = 1;
    public int max_deposit = 100;
    public int step_cycle = 3600;
    public int timer_cycle = 1200;
    public ShareItem share;
    public InviteItem inviteItem;

    public ArrayList<InterestRateItem> interest_rates;
    public ArrayList<IconItem> emojies;

    public SettingActivityItem activity;

}
