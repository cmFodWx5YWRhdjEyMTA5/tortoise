package com.android.kreators.tortoise.fragment.info.user;

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

public class UserCityFragment extends BaseInfoFragment {

    private TextInputLayout cityInputLayout;
    private EditText cityField;

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
        return R.layout.fragment_info_user_city;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        cityInputLayout = (TextInputLayout) mView.findViewById(R.id.cityInputLayout);
        cityField = (EditText) mView.findViewById(R.id.cityField);
    }

    @Override
    public void setProperties() {
        super.setProperties();

    }

    @Override
    public void configureListener() {
        super.configureListener();

        cityField.addTextChangedListener(new ValidateTextWatcher(getActivity(), cityField, null, cityInputLayout));
    }


    //  ======================================================================================




}
