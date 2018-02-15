package com.android.kreators.tortoise.model.friend;

import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 2017. 9. 2..
 */

public class FriendGroup extends BaseModel {

    private static final long serialVersionUID = -5023207912976576243L;

    //  ======================================================================================

    public int code;
    public ArrayList<AuthItem> data;

}
