package com.android.kreators.tortoise.event;

/**
 * Created by rrobbie on 2017. 5. 1..
 */

public class AuthEvent {

    //  ======================================================================================

    public int type;
    public Object data;

    //  ======================================================================================

    public AuthEvent(int type, Object data) {
        this.type = type;
        this.data = data;
    }


}



