package com.android.kreators.tortoise.activity.auth;

import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;
import com.android.kreators.tortoise.event.AuthEvent;
import com.android.nobug.manager.auth.AuthManager;
import com.android.nobug.util.CommonUtil;
import com.android.nobug.util.log;
import com.squareup.otto.Subscribe;

/**
 * Created by rrobbie on 2017. 5. 1..
 */

public class CreateAccountActivity extends BaseSignUpActivity {

    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout confirmPasswordInputLayout;

    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;

    private Button signUpButton;
    private Button facebookButton;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_create_account;
    }

    //  =======================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        emailInputLayout = (TextInputLayout) findViewById(R.id.emailInputLayout);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);
        confirmPasswordInputLayout = (TextInputLayout) findViewById(R.id.confirmPasswordInputLayout);

        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        confirmPasswordField = (EditText) findViewById(R.id.confirmPasswordField);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        facebookButton = (Button) findViewById(R.id.facebookButton);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        signUpButton.setOnClickListener(this);
        facebookButton.setOnClickListener(this);

        emailField.addTextChangedListener(new ValidateTextWatcher(this, emailField, null, emailInputLayout));
        passwordField.addTextChangedListener(new ValidateTextWatcher(this, passwordField, null, passwordInputLayout));
        confirmPasswordField.addTextChangedListener(new ValidateTextWatcher(this, confirmPasswordField, passwordField, confirmPasswordInputLayout));
    }

    //  =====================================================================================

    @Override
    protected void signUp() {
        if (!CommonUtil.validateEmail(this, emailField, emailInputLayout)) {
            return;
        }

        if (!CommonUtil.validatePassword(this, passwordField, passwordInputLayout)) {
            return;
        }

        if (!CommonUtil.validateConfirmPassword(this, confirmPasswordField, passwordField, confirmPasswordInputLayout)) {
            return;
        }

        //  TODO requst api

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        AuthManager.getInstance().getEmailManager().signUp(getContext(), email, password);
    }

    @Subscribe
    public void OnAuthEvent(AuthEvent event) {
        update(event);
    }
}
