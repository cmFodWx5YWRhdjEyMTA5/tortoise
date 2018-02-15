package com.android.kreators.tortoise.model.bank;

import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 08/12/2017.
 */

public class BankItemGroup extends BaseModel {

    private static final long serialVersionUID = 906610292589892012L;

    //  ======================================================================================

    public int code;
    public ArrayList<BankItem> data;
    public String message = "";

    //  ======================================================================================

    public boolean isSuccess() {
        return ( code == 1 );
    }

}
