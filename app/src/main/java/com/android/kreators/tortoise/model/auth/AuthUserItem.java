package com.android.kreators.tortoise.model.auth;

import com.android.kreators.tortoise.model.place.PlaceItem;
import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 2017. 8. 24..
 */

public class AuthUserItem extends BaseModel {

    private static final long serialVersionUID = 4649277597305759560L;

    //  ======================================================================================

    public String address_city;
    public String address_detail;
    public String address_states;
    public String address_street;
    public String address_zip_code;

    public PlaceItem placeItem;

    public String birth;
    public String phone_number;
    public String first_name;
    public String last_name;

}
