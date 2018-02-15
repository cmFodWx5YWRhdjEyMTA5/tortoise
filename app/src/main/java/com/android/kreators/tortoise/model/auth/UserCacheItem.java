package com.android.kreators.tortoise.model.auth;

import com.android.kreators.tortoise.model.bank.BankItem;
import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 2017. 4. 11..
 */

public class UserCacheItem extends BaseModel {

    private static final long serialVersionUID = 1431498746780219966L;

    //  ========================================================================================

    public String auth_token;
    public String facebook_token;
    public String facebook_id;
    public int user_type;
    public String email;
    public String password;

    public AuthUserItem authUserItem;
    public BankItem bankItem;
    public GoalItem goalItem;

    public boolean isFacebook() {
        return (user_type == 0);
    }

}




