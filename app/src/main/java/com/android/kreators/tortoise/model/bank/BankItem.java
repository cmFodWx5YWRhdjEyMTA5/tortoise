package com.android.kreators.tortoise.model.bank;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 08/12/2017.
 */

public class BankItem extends BaseModel {

    private static final long serialVersionUID = 864512383028016870L;

    //  ======================================================================================

    public int seq = 0;
    public long user_seq = 0;

    public String target_date;
    public String bank_id;
    public String bank_key;
    public String bank_code;
    public String account_num;
    public String name;
    public String mfa_answer;

    public double balance = 0;

    public int account_type = 0;
    public int status = 0;

}
