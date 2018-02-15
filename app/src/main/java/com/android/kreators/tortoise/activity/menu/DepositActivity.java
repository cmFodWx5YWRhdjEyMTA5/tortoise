package com.android.kreators.tortoise.activity.menu;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.view.keyboard.NumberKeyboard;
import com.android.nobug.activity.PendingActivity;

public class DepositActivity extends PendingActivity {

    private NumberKeyboard numberKeyboard;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_add_deposit;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        numberKeyboard = (NumberKeyboard) findViewById(R.id.numberKeyboard);
    }

    @Override
    public void setProperties() {
        super.setProperties();

    }

    @Override
    public void configureListener() {
        super.configureListener();

    }



}
