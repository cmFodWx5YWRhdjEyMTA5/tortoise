package com.android.kreators.tortoise.model.history;

import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by rrobbie on 05/12/2017.
 */

public class HistoryItemGroup extends BaseModel {

    private static final long serialVersionUID = 1730543878871294834L;

    //  =====================================================================================

    public String start_date;
    public String end_date;
    public StartBalance start_balance;
    public ArrayList<HistoryItem> step_daily_list;

}