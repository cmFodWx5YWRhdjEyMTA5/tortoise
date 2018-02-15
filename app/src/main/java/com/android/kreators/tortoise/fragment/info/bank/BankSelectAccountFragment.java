package com.android.kreators.tortoise.fragment.info.bank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.nobug.view.listview.BaseListView;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class BankSelectAccountFragment extends BaseInfoFragment implements AdapterView.OnItemClickListener {

    private BaseListView listView;

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
        return R.layout.fragment_info_bank_select_account;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        listView = (BaseListView) mView.findViewById(R.id.listView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        
    }

    @Override
    public void configureListener() {
        super.configureListener();


    }

    //  ======================================================================================

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
