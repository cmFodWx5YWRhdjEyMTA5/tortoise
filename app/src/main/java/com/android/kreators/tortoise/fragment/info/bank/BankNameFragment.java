package com.android.kreators.tortoise.fragment.info.bank;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;

/**
 * Created by rrobbie on 21/12/2017.
 */

public class BankNameFragment extends BaseInfoFragment {

    private TextInputLayout nameInputLayout;
    private EditText nameField;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_bank_name;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        nameInputLayout = (TextInputLayout) mView.findViewById(R.id.nameInputLayout);
        nameField = (EditText) mView.findViewById(R.id.nameField);
    }

    @Override
    public void setProperties() {
        super.setProperties();

    }

    @Override
    public void configureListener() {
        super.configureListener();

        nameField.addTextChangedListener(new ValidateTextWatcher(getActivity(), nameField, null, nameInputLayout));
    }

}
