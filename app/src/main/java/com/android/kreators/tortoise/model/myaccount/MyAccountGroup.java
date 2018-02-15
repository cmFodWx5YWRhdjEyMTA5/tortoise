package com.android.kreators.tortoise.model.myaccount;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 11/12/2017.
 */

public class MyAccountGroup extends BaseModel {

    private static final long serialVersionUID = 3418247367725678167L;

    //  =====================================================================================

    public int code;
    public MyAccountItemGroup data;
    public String message = "";

    //  ======================================================================================

    public boolean isSuccess() {
        return ( code == 1 );
    }

}
