package com.android.kreators.tortoise.model.myaccount;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 11/12/2017.
 */

public class TransactionItem extends BaseModel {

    private static final long serialVersionUID = 9015925312277822909L;

    //  ======================================================================================

    public long user_seq;

    public int bank_seq;
    public int deposit_type_num;
    public int seq;
    public int target_bank_seq;

    public double balance;
    public double deposit;
    public double withdraw;

    public String image_url;
    public String name;
    public String recent_status;
    public String target_date;

    public long recent_status_date;


}
