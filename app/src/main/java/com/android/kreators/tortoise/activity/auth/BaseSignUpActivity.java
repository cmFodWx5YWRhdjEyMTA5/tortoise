package com.android.kreators.tortoise.activity.auth;

import android.view.View;

import com.android.kreators.tortoise.R;

/**
 * Created by rrobbie on 2017. 9. 12..
 */

public class BaseSignUpActivity extends BaseAuthActivity implements View.OnClickListener {


    //  ======================================================================================

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInButton:
                signIn();
                break;

            case R.id.signUpButton:
                signUp();
                break;

            case R.id.signUpPlusButton:
                signUpPlus();
                break;

            case R.id.facebookButton:
                facebook();
                break;
        }
    }

}
