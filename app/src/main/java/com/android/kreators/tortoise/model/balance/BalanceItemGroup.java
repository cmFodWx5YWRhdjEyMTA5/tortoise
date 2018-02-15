package com.android.kreators.tortoise.model.balance;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 11/12/2017.
 */

public class BalanceItemGroup extends BaseModel {

    private static final long serialVersionUID = 6834255085369297039L;

    //  =====================================================================================

    public int code;
    public BalanceItem data;
    public String message = "";

    //  ======================================================================================

    public boolean isSuccess() {
        return ( code == 1 );
    }

}
