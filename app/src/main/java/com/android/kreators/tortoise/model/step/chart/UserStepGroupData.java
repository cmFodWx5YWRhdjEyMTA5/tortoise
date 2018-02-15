package com.android.kreators.tortoise.model.step.chart;

import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 06/12/2017.
 */

public class UserStepGroupData extends BaseModel {

    private static final long serialVersionUID = -7481928570797147254L;

    //  =====================================================================================

    public ArrayList<UserStepItem> stepList;

    public int min = 0;
    public int max = 0;

    //  =====================================================================================

    public void update() {
        for (UserStepItem item : stepList) {
            if( item.step > max ) max = item.step;
        }
    }

}
