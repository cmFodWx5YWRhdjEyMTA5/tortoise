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

public class ForgotPasswordActivity extends BaseActivity implements Callback, View.OnClickListener {

    private TextInputLayout emailInputLayout;
    private EditText emailField;
    private Button sendButton;

    //  =====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_forgot_password;
    }

    //  =====================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        emailInputLayout = (TextInputLayout) findViewById(R.id.emailInputLayout);
        emailField = (EditText) findViewById(R.id.emailField);
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
        emailField.addTextChangedListener(new ValidateTextWatcher(this, emailField, null, emailInputLayout));
    }

    //  =====================================================================================

    private void send() {
        if (!CommonUtil.validateEmail(this, emailField, emailInputLayout)) {
            return;
        }

        //  TODO send api
        String email = emailField.getText().toString();
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



