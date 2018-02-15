package com.android.kreators.tortoise.model.version;

import com.android.nobug.model.BaseModel;

import java.util.ArrayList;

public class VersionGroup extends BaseModel {


    private static final long serialVersionUID = 4694004505378794955L;

    //  ======================================================================================

    public int min = 0;
    public int current = 0;

    public int status = 0;
    public int setting_version = 0;
    public ArrayList<VersionItem> histories;

    //  ======================================================================================

    public boolean isMainTenance() {
        return (this.status == 0);
    }

}
