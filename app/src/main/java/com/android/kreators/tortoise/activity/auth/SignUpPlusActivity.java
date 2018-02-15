package com.android.kreators.tortoise.activity.auth;

import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;
import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.event.AuthEvent;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.kreators.tortoise.model.auth.AuthItemGroup;
import com.android.nobug.manager.auth.AuthManager;
import com.android.nobug.util.CommonUtil;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.log;
import com.squareup.otto.Subscribe;

/**
 * Created by rrobbie on 2017. 8. 31..
 */

public class SignUpPlusActivity extends BaseSignUpActivity {

    private TextInputLayout emailInputLayout;
    private EditText emailField;

    private Button signUpPlusButton;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_sns_sign_up;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        emailInputLayout = (TextInputLayout) findViewById(R.id.emailInputLayout);
        emailField = (EditText) findViewById(R.id.emailField);
        signUpPlusButton = (Button) findViewById(R.id.signUpPlusButton);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        signUpPlusButton.setOnClickListener(this);
        emailField.addTextChangedListener(new ValidateTextWatcher(this, emailField, null, emailInputLayout));
    }

    //  ======================================================================================

    @Override
    protected void signUpPlus() {
        if ( !CommonUtil.validateEmail(this, emailField, emailInputLayout) ) {
            return;
        }

        //  TODO requst api
        if( facebookItem != null ) {
            String email = emailField.getText().toString();
            AuthManager.getInstance().getFacebookManager().signIn(facebookItem.id, email, null, null, facebookItem.profile);
        }
    }

    @Subscribe
    public void OnAuthEvent(AuthEvent event) {
        AuthItemGroup group = null;
        log.show( "AuthEvent : " + ObjectUtil.toProperties(event) );

        try {
            group = (AuthItemGroup) event.data;
            if( event.type == EventType.FACEBOOK_SIGN_IN || event.type == EventType.FACEBOOK_SIGN_UP ) {
                IntentFactory.userInfo(this);
                return;
            }

            String message = ( event.type == EventType.ERROR ) ? group.message : getString(R.string.network_error_message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            IntentFactory.intro(this);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
