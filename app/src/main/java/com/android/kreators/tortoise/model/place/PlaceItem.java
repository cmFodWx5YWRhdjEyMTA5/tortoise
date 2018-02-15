package com.android.kreators.tortoise.model.place;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 2017. 8. 26..
 */

public class PlaceItem extends BaseModel {

    private static final long serialVersionUID = 6171188924186034336L;

    //  =====================================================================================

    public String id;

    public String address;
    public String address_street;
    public String address_city;
    public String address_subdivision;
    public String address_postal_code;
    public String address_country_code;

}

