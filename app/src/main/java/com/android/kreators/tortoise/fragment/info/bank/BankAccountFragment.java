package com.android.kreators.tortoise.fragment.info.bank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class BankAccountFragment extends BaseInfoFragment {

    private TextInputLayout idInputLayout;
    private TextInputLayout passwordInputLayout;

    private EditText idField;
    private EditText passwordField;

    //  ======================================================================================

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        if (args != null) {
            //  position = args.getInt(IntentProperty.POSITION, 0);
        }
    }

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_bank_account;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        idInputLayout = (TextInputLayout) mView.findViewById(R.id.idInputLayout);
        passwordInputLayout = (TextInputLayout) mView.findViewById(R.id.passwordInputLayout);

        idField = (EditText) mView.findViewById(R.id.idField);
        passwordField = (EditText) mView.findViewById(R.id.passwordField);

    }

    @Override
    public void setProperties() {
        super.setProperties();


    }

    @Override
    public void configureListener() {
        super.configureListener();

        idField.addTextChangedListener(new ValidateTextWatcher(getActivity(), idField, null, idInputLayout));
        passwordField.addTextChangedListener(new ValidateTextWatcher(getActivity(), passwordField, null, passwordInputLayout));
    }


    //  ======================================================================================




}
