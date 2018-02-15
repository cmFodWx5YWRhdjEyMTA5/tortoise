package com.android.kreators.tortoise.model.myaccount;

import com.android.kreators.tortoise.model.balance.BalanceItem;
import com.android.kreators.tortoise.model.bank.BankItem;
import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 11/12/2017.
 */

public class MyAccountItemGroup extends BaseModel {

    private static final long serialVersionUID = -2698775865463913454L;

    //  =====================================================================================

    public long user_seq;
    public String recent_status;
    public BalanceItem balance;
    public ArrayList<TransactionItem> transaction_list;

}