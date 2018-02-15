package com.android.kreators.tortoise.activity.auth;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;
import com.android.nobug.core.BaseActivity;
import com.android.nobug.util.CommonUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rrobbie on 2017. 10. 30..
 */

public class ChangePasswordActivity extends BaseActivity implements Callback, View.OnClickListener {

    private TextInputLayout passwordInputLayout;
    private TextInputLayout newPasswordInputLayout;
    private TextInputLayout confirmPasswordInputLayout;

    private EditText passwordField;
    private EditText newPasswordField;
    private EditText confirmPasswordField;

    private Button sendButton;

    //  =====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_change_password;
    }

    //  =====================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);
        newPasswordInputLayout = (TextInputLayout) findViewById(R.id.newPasswordInputLayout);
        confirmPasswordInputLayout = (TextInputLayout) findViewById(R.id.confirmPasswordInputLayout);

        passwordField = (EditText) findViewById(R.id.passwordField);
        newPasswordField = (EditText) findViewById(R.id.newPasswordField);
        confirmPasswordField = (EditText) findViewById(R.id.confirmPasswordField);

        sendButton = (Button) findViewById(R.id.sendButton);
    }

    @Override
    public void setProperties() {
        super.setProperties();

    }

    @Override
    public void configureListener() {
        super.configureListener();

        sendButton.setOnClickListener(this);
        passwordField.addTextChangedListener(new ValidateTextWatcher(this, passwordField, null, passwordInputLayout));
        newPasswordField.addTextChangedListener(new ValidateTextWatcher(this, newPasswordField, null, newPasswordInputLayout));
        confirmPasswordField.addTextChangedListener(new ValidateTextWatcher(this, confirmPasswordField, null, confirmPasswordInputLayout));

    }

    //  =====================================================================================

    private void send() {
        if (!CommonUtil.validatePassword(this, passwordField, passwordInputLayout)) {
            return;
        }

        if (!CommonUtil.validatePassword(this, newPasswordField, newPasswordInputLayout)) {
            return;
        }

        if (!CommonUtil.validatePassword(this, confirmPasswordField, confirmPasswordInputLayout)) {
            return;
        }

        //  TODO send api
        String password = passwordField.getText().toString();
        String newPassword = newPasswordField.getText().toString();

        request();
    }

    private void request() {

    }

    //  =====================================================================================


    @Override
    public void onClick(View v) {
        send();
    }

    //  =====================================================================================

    @Override
    public void onResponse(Call call, Response response) {

    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }




}



