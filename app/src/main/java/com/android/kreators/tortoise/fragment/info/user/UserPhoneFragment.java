package com.android.kreators.tortoise.fragment.info.user;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.kreators.tortoise.model.auth.AuthUserItem;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;
import com.android.nobug.util.CommonUtil;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class UserPhoneFragment extends BaseInfoFragment {

    private TextInputLayout phoneInputLayout;
    private TextInputLayout birthInputLayout;

    private EditText phoneField;
    private EditText birthField;

    private ValidateTextWatcher phonetWatcher;
    private ValidateTextWatcher birthWatcher;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_user_phone;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        phoneInputLayout = (TextInputLayout) mView.findViewById(R.id.phoneInputLayout);
        birthInputLayout = (TextInputLayout) mView.findViewById(R.id.birthInputLayout);

        phoneField = (EditText) mView.findViewById(R.id.phoneField);
        birthField = (EditText) mView.findViewById(R.id.birthField);

    }

    @Override
    public void setProperties() {
        super.setProperties();

        phonetWatcher = new ValidateTextWatcher(getActivity(), phoneField, birthField, phoneInputLayout);
        birthWatcher = new ValidateTextWatcher(getActivity(), birthField, birthInputLayout);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        phoneField.addTextChangedListener(phonetWatcher);
        birthField.addTextChangedListener(birthWatcher);
    }

    //  ======================================================================================

    @Override
    public boolean next() {
        boolean phone = CommonUtil.validatePhone(getActivity(), phoneField, birthField, phoneInputLayout, phonetWatcher);

        if( !phone )
            return false;

        boolean birth = CommonUtil.validateBirth(getActivity(), birthField, birthInputLayout, birthWatcher);

        if( birth ) {
            UserCacheItem item = PreferenceFactory.getUserItem(getActivity());
            item.authUserItem = new AuthUserItem();
            item.authUserItem.phone_number = phoneField.getText().toString().trim();
            item.authUserItem.birth = birthField.getText().toString().trim();
        }

        return phone && birth;
    }



}
