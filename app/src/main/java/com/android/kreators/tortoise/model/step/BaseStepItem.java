package com.android.kreators.tortoise.model.step;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 2017. 9. 21..
 */

public class BaseStepItem extends BaseModel {

    private static final long serialVersionUID = -6495616329202363530L;

    //  ====================================================================================

    public int user_seq;
    public String target_date;
    public String close_date;
    public int is_closed = 0;
    public int step = 0;
    public int step_goal = 1000;
    public double deposit = 0;
    public double deposit_goal = 0;
    public double balance = 0;
    public double saving_bonus_amount = 0;

    public int status = 0;


    //  ====================================================================================

}
