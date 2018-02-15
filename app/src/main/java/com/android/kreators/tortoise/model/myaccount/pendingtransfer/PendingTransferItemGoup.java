package com.android.kreators.tortoise.model.myaccount.pendingtransfer;

import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 12/12/2017.
 */

public class PendingTransferItemGoup extends BaseModel {

    private static final long serialVersionUID = 2349244815751356987L;

    //  ======================================================================================

    public double avaliableForWithdrawal;
    public double inPendingDeposit;
    public double inPendingWithdraw;

    public ArrayList<PendingTransferItem> items;

}
