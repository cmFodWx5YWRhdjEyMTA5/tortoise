package com.android.nobug.event;

/**
 * Created by rrobbie on 16. 6. 14..
 */
public class NotificationEvent {

    public int type;
    public Object data;

    //  ======================================================================================

    public NotificationEvent(int type, Object data) {
        this.type = type;
        this.data = data;
    }

}
