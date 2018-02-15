package com.android.nobug.util;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;


public class CommonUtil {

    //  ======================================================================================

    public static boolean isValidEmail(String value) {
        return !TextUtils.isEmpty(value) && Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    public static void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //  ======================================================================================

    public static boolean validateEmail(Activity activity, EditText emailField, TextInputLayout emailInputLayout) {
        emailInputLayout.setErrorEnabled(true);
        String email = emailField.getText().toString();
        boolean flag = TextUtils.isEmpty(email);

        if (flag || !CommonUtil.isValidEmail(email)) {
            emailInputLayout.setError(activity.getString(R.string.err_msg_email));
            CommonUtil.requestFocus(activity, emailField);
            return false;
        } else {
            emailInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public static boolean validatePassword(Activity activity, EditText passwordField, TextInputLayout passwordInputLayout) {
        passwordInputLayout.setErrorEnabled(true);
        String password = passwordField.getText().toString();
        boolean flag = TextUtils.isEmpty(password);

        if (flag) {
            passwordInputLayout.setError(activity.getString(R.string.err_msg_password));
            requestFocus(activity, passwordField);
            return false;
        } else {
            passwordInputLayout.setErrorEnabled(false);
        }
         return true;
    }

    public static boolean validateConfirmPassword(Activity activity, EditText confirmPasswordField, EditText passwordField, TextInputLayout confirmPasswordInputLayout) {
        confirmPasswordInputLayout.setErrorEnabled(true);
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String message = activity.getString(R.string.err_msg_confirm_password);
        boolean passwordFlag = TextUtils.isEmpty(password);
        boolean confirmPasswordFlag = TextUtils.isEmpty(confirmPassword);

        if (!passwordFlag && !confirmPasswordFlag) {
            if( confirmPasswordField.getText().toString().equals( password ) ) {
                confirmPasswordInputLayout.setErrorEnabled(false);
                return true;
            }
        }
        confirmPasswordInputLayout.setError( message );
        requestFocus(activity, confirmPasswordField);
        return false;
    }

    public static boolean validateField(Activity activity, EditText editField, TextInputLayout inputLayout) {
        boolean flag = TextUtils.isEmpty(editField.getText().toString());
        String message = flag ? activity.getString(R.string.empty_input) : null;
        inputLayout.setError( message );
        requestFocus(activity, editField);
        return !flag;
    }

    public static boolean validatePhone(Activity activity, EditText editField, EditText editField2, TextInputLayout inputLayout, ValidateTextWatcher watcher) {
        boolean flag = validateField(activity, editField, inputLayout);

        if( !flag ) {
            return false;
        }

        String value = (editField.getText().toString() + "").replaceAll("-","");
        boolean isValid = PhoneNumberUtils.isGlobalPhoneNumber(value);
        String message = isValid ? null : activity.getString(R.string.err_msg_phone);

        if( value.length() >= 10 ) {
            String phone = makePhoneNumber(value);
            editField.removeTextChangedListener(watcher);
            editField.setText(phone);
            editField.addTextChangedListener(watcher);
            requestFocus(activity, editField2);
            return true;
        }

        inputLayout.setError( message );
        requestFocus(activity, editField);
        return false;
    }

    public static boolean validateBirth(Activity activity, EditText editField,TextInputLayout inputLayout, ValidateTextWatcher watcher) {
        boolean flag = validateField(activity, editField, inputLayout);

        if( !flag ) {
            return false;
        }

        String value = (editField.getText().toString() + "").replaceAll("/","");
        String message = activity.getString(R.string.err_msg_birth);

        if( value.length() >= 8 ) {
            String birthday = makeBirthday(value);
            editField.removeTextChangedListener(watcher);
            editField.setText(birthday);
            editField.addTextChangedListener(watcher);
            return true;
        }

        inputLayout.setError( message );
        requestFocus(activity, editField);
        return false;
    }

    public static String makePhoneNumber(String phoneNumber) {
        String regEx = "(\\d{3})(\\d{4})(\\d{3})";
        //  if(!Pattern.matches(regEx, phoneNumber)) return null;
        return phoneNumber.replaceAll(regEx, "$1-$2-$3");
    }

    public static String makeBirthday(String birthday) {
        String regEx = "(\\d{2})(\\d{2})(\\d{4})";
        return birthday.replaceAll(regEx, "$1/$2/$3");
    }

    public static boolean validateAddress(Activity activity, EditText editField, TextInputLayout inputLayout) {
        inputLayout.setErrorEnabled(true);
        String field = editField.getText().toString();
        boolean flag = TextUtils.isEmpty(field);
        String message = activity.getString(R.string.err_msg_address);

        if (flag) {
            inputLayout.setError(message);
            requestFocus(activity, editField);
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }

        inputLayout.setError( message );
        requestFocus(activity, editField);
        return false;
    }

}
