package com.android.kreators.tortoise.activity.myaccount;

import android.os.Bundle;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.event.AuthEvent;
import com.android.kreators.tortoise.event.TortoiseKeyboardEvent;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.view.keyboard.NumberKeyboard;
import com.android.nobug.activity.PendingActivity;
import com.android.nobug.event.provider.BusProvider;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.StringUtil;
import com.android.nobug.util.log;
import com.squareup.otto.Subscribe;

/**
 * Created by rrobbie on 12/12/2017.
 */

public class TransferToBankActivity extends PendingActivity {

    private TextView availableField;
    private TextView withdrawField;

    private double currentBalance;
    private AuthItem authItem;

    private NumberKeyboard numberKeyboard;

    //  ====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_transfer_bank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    //  ====================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        numberKeyboard = (NumberKeyboard) findViewById(R.id.numberKeyboard);
        availableField = (TextView) findViewById(R.id.availableField);
        withdrawField = (TextView) findViewById(R.id.withdrawField);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        try {
            authItem = (AuthItem) getIntent().getSerializableExtra(IntentProperty.AUTH_ITEM);
            currentBalance= getIntent().getDoubleExtra(IntentProperty.BALANCE, 0);
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configureListener() {
        super.configureListener();

    }

    //  =====================================================================================

    private void update() {
        withdrawField.setText( StringUtil.toSentFormat(0, 2) );
        availableField.setText(StringUtil.toSentFormat(currentBalance, 2));
    }

    //  =====================================================================================

    @Subscribe
    public void OnTortoiseKeyboardEvent(TortoiseKeyboardEvent event) {
        try {
            String item = "$" + event.data;
            withdrawField.setText( item );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
