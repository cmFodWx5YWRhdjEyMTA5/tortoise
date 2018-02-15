package com.android.kreators.tortoise.model.auth;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 28/11/2017.
 */

public class AuthItemGroup extends BaseModel {

    private static final long serialVersionUID = 3776619548775090604L;

    //  ======================================================================================

    public int code;
    public AuthItem data;
    public String message = "";

    //  ======================================================================================

    public boolean isSuccess() {
        return ( code == 1 );
    }



}
