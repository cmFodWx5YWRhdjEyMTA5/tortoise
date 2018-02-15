package com.android.kreators.tortoise.model.auth;

public class AuthItem extends AuthUserItem {

    private static final long serialVersionUID = 2906127445160051325L;

    //  ======================================================================================

    public long seq;

    public String api_key;

    public String facebook_id;

    public int user_type;
    public int is_registered;
    public int include;

    public int step;
    public int step_goal;
    public int deposit_goal;

    public String profile;

    public String email;
    public String password;

    //  ======================================================================================

    public boolean isFacebook() {
        return (user_type == 0);
    }

    public boolean isRegister() {
        return toBoolean(is_registered);
    }

    public boolean isInclude() {
        return toBoolean(include);
    }



}


