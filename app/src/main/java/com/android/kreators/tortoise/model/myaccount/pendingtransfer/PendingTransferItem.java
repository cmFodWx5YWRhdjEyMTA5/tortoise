package com.android.kreators.tortoise.model.myaccount.pendingtransfer;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 12/12/2017.
 */

public class PendingTransferItem extends BaseModel {

    private static final long serialVersionUID = 7410042397062521628L;

    //  ======================================================================================

    public String type;
    public double balance;
    public String create_date;
    public String clear_date;

}
