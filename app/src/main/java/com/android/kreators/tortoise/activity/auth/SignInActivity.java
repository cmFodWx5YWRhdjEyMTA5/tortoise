package com.android.kreators.tortoise.activity.auth;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;
import com.android.kreators.tortoise.event.AuthEvent;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.nobug.manager.auth.AuthManager;
import com.android.nobug.util.CommonUtil;
import com.android.nobug.util.log;
import com.squareup.otto.Subscribe;

/**
 * Created by rrobbie on 2017. 4. 17..
 */

public class SignInActivity extends BaseSignUpActivity {


    private Button facebookButton;
    private Button signInButton;

    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    private EditText emailField;
    private EditText passwordField;

    private TextView forgotPasswordField;

    //  =====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        facebookButton = (Button) findViewById(R.id.facebookButton);
        signInButton = (Button) findViewById(R.id.signInButton);
        emailInputLayout = (TextInputLayout) findViewById(R.id.emailInputLayout);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        forgotPasswordField = (TextView) findViewById(R.id.forgotPasswordField);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        facebookButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        emailField.addTextChangedListener(new ValidateTextWatcher(this, emailField, null, emailInputLayout));
        passwordField.addTextChangedListener(new ValidateTextWatcher(this, passwordField, null, passwordInputLayout));
        forgotPasswordField.setOnClickListener(this);
    }

    //  =====================================================================================

    @Override
    protected void signIn() {
        if (!CommonUtil.validateEmail(this, emailField, emailInputLayout)) {
            return;
        }

        if (!CommonUtil.validatePassword(this, passwordField, passwordInputLayout)) {
            return;
        }

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        AuthManager.getInstance().getEmailManager().signIn(getContext(), email, password);
    }

    @Subscribe
    public void OnAuthEvent(AuthEvent event) {
        update(event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if( v.getId() == R.id.forgotPasswordField ) {
            IntentFactory.forgotPassword(this);
        }

    }
}
