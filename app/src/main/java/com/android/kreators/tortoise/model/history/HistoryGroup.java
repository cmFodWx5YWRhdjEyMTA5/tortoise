package com.android.kreators.tortoise.model.history;

import com.android.kreators.tortoise.model.activity.ActivityItem;
import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 05/12/2017.
 */

public class HistoryGroup extends BaseModel {

    private static final long serialVersionUID = -3028674577218707300L;

    //  ======================================================================================

    public int code;
    public HistoryItemGroup data;
    public String message = "";

    //  ======================================================================================

    public boolean isSuccess() {
        return ( code == 1 );
    }


}
