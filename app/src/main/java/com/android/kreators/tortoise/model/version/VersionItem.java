package com.android.kreators.tortoise.model.version;

import com.android.nobug.model.BaseModel;

/**
 * Created by rrobbie on 2017. 4. 11..
 */

public class VersionItem extends BaseModel {

    private static final long serialVersionUID = -6158832434746147207L;

    //  ======================================================================================

    public int version;
    public String host;
    public String image;
    public int force = 0;

    //  ======================================================================================

    public boolean isMainTenance() {
        return (force == 1);
    }

}




