package com.android.kreators.tortoise.fragment.info.user;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.kreators.tortoise.model.auth.AuthUserItem;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.util.textwatcher.ValidateTextWatcher;
import com.android.nobug.util.CommonUtil;
import com.android.nobug.util.log;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class UserNameFragment extends BaseInfoFragment {

    private TextInputLayout firstNameInputLayout;
    private TextInputLayout lastNameInputLayout;

    private EditText firstNameField;
    private EditText lastNameField;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_user_name;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        firstNameInputLayout = (TextInputLayout) mView.findViewById(R.id.firstNameInputLayout);
        lastNameInputLayout = (TextInputLayout) mView.findViewById(R.id.lastNameInputLayout);

        firstNameField = (EditText) mView.findViewById(R.id.firstNameField);
        lastNameField = (EditText) mView.findViewById(R.id.lastNameField);
    }

    @Override
    public void setProperties() {
        super.setProperties();

    }

    @Override
    public void configureListener() {
        super.configureListener();

        firstNameField.addTextChangedListener(new ValidateTextWatcher(getActivity(), firstNameField, null, firstNameInputLayout));
        lastNameField.addTextChangedListener(new ValidateTextWatcher(getActivity(), lastNameField, null, lastNameInputLayout));
    }

    //  ======================================================================================

    @Override
    public boolean next() {
        boolean first = CommonUtil.validateField(getActivity(), firstNameField, firstNameInputLayout);

        if( !first )
            return false;

        boolean last = CommonUtil.validateField(getActivity(), lastNameField, lastNameInputLayout);

        if( last ) {
            UserCacheItem item = PreferenceFactory.getUserItem(getActivity());
            item.authUserItem = new AuthUserItem();
            item.authUserItem.first_name = firstNameField.getText().toString().trim();
            item.authUserItem.last_name = lastNameField.getText().toString().trim();
        }

        log.show( "user name next : " + first + " / "  + last );

        return ( first && last );
    }





}
