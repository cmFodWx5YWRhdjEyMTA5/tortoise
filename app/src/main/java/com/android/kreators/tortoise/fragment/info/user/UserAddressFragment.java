package com.android.kreators.tortoise.fragment.info.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.fragment.bank.AddressFragment;
import com.android.kreators.tortoise.model.auth.AuthUserItem;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class UserAddressFragment extends AddressFragment {

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
        return R.layout.fragment_info_user_address;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        address1InputLayout = (TextInputLayout) mView.findViewById(R.id.address1InputLayout);
        address2InputLayout = (TextInputLayout) mView.findViewById(R.id.address2InputLayout);

        address1Field = (EditText) mView.findViewById(R.id.address1Field);
        address2Field = (EditText) mView.findViewById(R.id.address2Field);

    }

    @Override
    public void setProperties() {
        super.setProperties();


    }

    @Override
    public void configureListener() {
        super.configureListener();

        address1Field.addTextChangedListener(new PlaceTextWatcher());
        address2Field.addTextChangedListener(new ValidateTextWatcher(getActivity(), address2Field, null, address2InputLayout));
    }

    //  ======================================================================================

    @Override
    public boolean next() {
        UserCacheItem item = PreferenceFactory.getUserItem(getActivity());
        item.authUserItem = new AuthUserItem();
        /*
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
        */
        return false;
    }




}
