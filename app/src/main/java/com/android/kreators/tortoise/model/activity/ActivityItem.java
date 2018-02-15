package com.android.kreators.tortoise.model.activity;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 28/11/2017.
 */

public class ActivityItem extends BaseModel {

    private static final long serialVersionUID = -6931486402647892092L;

    //  ======================================================================================

    public int user_sq;

    public double balance;
    public double deposit;
    public double withdraw;

    public int deposit_type_num; //  deposit_type 0:daily, 1:icon, 2:number, 3:withdraw, 4:saving-return
    public String image_url;
    public String name;
    public String target_date;

}
