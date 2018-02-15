package com.android.kreators.tortoise.model.activity;

import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 28/11/2017.
 */

public class ActivityItemGroup extends BaseModel {

    private static final long serialVersionUID = 4378763513854996578L;

    //  ======================================================================================

    public int code;
    public ArrayList<ActivityItem> data;
    public String message = "";

    //  ======================================================================================

    public boolean isSuccess() {
        return ( code == 1 );
    }

}
