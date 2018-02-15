package com.android.kreators.tortoise.activity.myaccount;

import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.myaccount.PendingTransferAdapter;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.model.myaccount.pendingtransfer.PendingTransferItemGoup;
import com.android.nobug.activity.PendingActivity;
import com.android.nobug.util.StringUtil;
import com.android.nobug.util.log;
import com.android.nobug.view.listview.BaseListView;

/**
 * Created by rrobbie on 12/12/2017.
 */

public class PendingTransferActivity extends PendingActivity {

    private BaseListView listView;
    private PendingTransferAdapter pendingTransferAdapter;

    private TextView avaliableField;
    private TextView pendingDepositField;
    private TextView pendingWithdrawField;

    private AuthItem authItem;
    private PendingTransferItemGoup pendingTransferItemGoup;

    //  ====================================================================================


    @Override
    public int getLayoutContentView() {
        return R.layout.activity_pending_transfer;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        listView = (BaseListView) findViewById(R.id.listView);
        avaliableField = (TextView) findViewById(R.id.avaliableField);
        pendingDepositField = (TextView) findViewById(R.id.pendingDepositField);
        pendingWithdrawField = (TextView) findViewById(R.id.pendingWithdrawField);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        try {
            authItem = (AuthItem) getIntent().getSerializableExtra(IntentProperty.AUTH_ITEM);
            pendingTransferItemGoup = (PendingTransferItemGoup) getIntent().getSerializableExtra(IntentProperty.PENDING_ITEM);

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
        try {

            log.show( "update : " + pendingTransferItemGoup.items.size() );

            pendingTransferAdapter = new PendingTransferAdapter(this, pendingTransferItemGoup.items);
            listView.setAdapter(pendingTransferAdapter);

            avaliableField.setText(StringUtil.toSentFormat(pendingTransferItemGoup.avaliableForWithdrawal, 2));
            pendingDepositField.setText(StringUtil.toSentFormat(pendingTransferItemGoup.inPendingDeposit, 2));
            pendingWithdrawField.setText(StringUtil.toSentFormat(pendingTransferItemGoup.inPendingWithdraw, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  =====================================================================================


}
