package com.android.kreators.tortoise.util.textwatcher;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.nobug.util.CommonUtil;

public class ValidateTextWatcher implements TextWatcher {

    private Activity mActivity;
    private EditText field1;
    private EditText field2;
    private TextInputLayout inputLayout;

    //  =====================================================================================


    public ValidateTextWatcher(Activity activity, EditText field1, TextInputLayout inputLayout) {
        this.mActivity = activity;
        this.field1 = field1;
        this.field2 = field2;
        this.inputLayout = inputLayout;
    }

    public ValidateTextWatcher(Activity activity, EditText field1, EditText field2, TextInputLayout inputLayout) {
        this.mActivity = activity;
        this.field1 = field1;
        this.field2 = field2;
        this.inputLayout = inputLayout;
    }

    //  ===================================================================================

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    public void afterTextChanged(Editable editable) {

        switch (field1.getId()) {
            case R.id.emailField:
                CommonUtil.validateEmail(mActivity, field1, inputLayout);
                break;

            case R.id.passwordField:
                CommonUtil.validatePassword(mActivity, field1, inputLayout);
                break;

            case R.id.newPasswordField:
                CommonUtil.validatePassword(mActivity, field1, inputLayout);
                break;

            case R.id.confirmPasswordField:
                CommonUtil.validateConfirmPassword(mActivity, field1, field2, inputLayout);
                break;

            case R.id.firstNameField:
            case R.id.lastNameField:
                CommonUtil.validateField(mActivity, field1, inputLayout);
                break;

            case R.id.phoneField:
                CommonUtil.validatePhone(mActivity, field1, field2, inputLayout, this);
                break;

            case R.id.birthField:
                CommonUtil.validateBirth(mActivity, field1, inputLayout, this);
                break;

            case R.id.address1Field:
                CommonUtil.validateAddress(mActivity, field1, inputLayout);
                break;

            case R.id.address2Field:
                CommonUtil.validateAddress(mActivity, field1, inputLayout);
                break;

        }
    }

}