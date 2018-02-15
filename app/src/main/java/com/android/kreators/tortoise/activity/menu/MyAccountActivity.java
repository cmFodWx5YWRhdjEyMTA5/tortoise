package com.android.kreators.tortoise.activity.menu;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.constants.property.TransactionProperty;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.kreators.tortoise.manager.BalanceManager;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.model.myaccount.MyAccountGroup;
import com.android.kreators.tortoise.model.myaccount.TransactionItem;
import com.android.kreators.tortoise.model.myaccount.pendingtransfer.PendingTransferItem;
import com.android.kreators.tortoise.model.myaccount.pendingtransfer.PendingTransferItemGoup;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.kreators.tortoise.view.bank.BankAccount;
import com.android.nobug.activity.PendingActivity;
import com.android.nobug.util.CalculationUtil;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.StringUtil;
import com.android.nobug.util.log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountActivity extends PendingActivity implements View.OnClickListener, Callback {

    private TextView balanceField;
    private TextView nameField;
    private TextView phoneField;
    private TextView addressField;

    private BankAccount bankAccount;

    private Button transferButton;

    private RelativeLayout balanceLayer;

    private AuthItem authItem;

    private MyAccountGroup accountGroup;
    private PendingTransferItemGoup pendingTransferItemGoup;

    private double settled = 0;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_my_account;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        balanceLayer = (RelativeLayout) findViewById(R.id.balanceLayer);

        balanceField = (TextView) findViewById(R.id.balanceField);
        nameField = (TextView) findViewById(R.id.nameField);
        phoneField = (TextView) findViewById(R.id.phoneField);
        addressField = (TextView) findViewById(R.id.addressField);

        transferButton = (Button) findViewById(R.id.transferButton);

        bankAccount = (BankAccount) findViewById(R.id.bankAccount);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        request();
    }

    @Override
    public void configureListener() {
        super.configureListener();

        balanceLayer.setOnClickListener(this);
        transferButton.setOnClickListener(this);
    }

    //  =======================================================================================

    private void request() {
        //  TODO request memer api
        try {
            authItem = (AuthItem) getIntent().getSerializableExtra(IntentProperty.AUTH_ITEM);
            setUserItem();
            RetrofitBuilder.withUnsafe(this).getMyAccount(authItem.seq, TransactionProperty.PENDING).enqueue(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUserItem() {
        if( authItem != null ) {
            String name = authItem.first_name + " " + authItem.last_name;
            nameField.setText( name );
            phoneField.setText( authItem.phone_number );
            addressField.setText( authItem.address_street );
            bankAccount.setItem( CacheManager.getInstance().getBankItem() );
        }
    }

    private void update(Response response) {
        try {
            accountGroup = (MyAccountGroup) response.body();
            calculate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculate() {
        ArrayList<TransactionItem> transactionItems = accountGroup.data.transaction_list;

        settled = BalanceManager.getInstance().getAvaliableValue(accountGroup.data.balance);
        double pendingDeposit =  0;
        double pendingWithdraw =  0;

        pendingTransferItemGoup = new PendingTransferItemGoup();
        pendingTransferItemGoup.avaliableForWithdrawal = settled - pendingWithdraw;
        pendingTransferItemGoup.items = new ArrayList<PendingTransferItem>();

        for (TransactionItem item : transactionItems) {
            PendingTransferItem pendingTransferItem = new PendingTransferItem();

            if( item.deposit_type_num == 0 || item.deposit_type_num == 2 || item.deposit_type_num == 2 ) {
                pendingDeposit+= item.deposit;
                pendingTransferItem = getPendingItem(getString(R.string.in_pending_deposit), item.recent_status_date, item.deposit);
                pendingTransferItemGoup.items.add(pendingTransferItem);
            } else if( item.deposit_type_num == 3 ) {
                pendingWithdraw+= item.withdraw;
                pendingTransferItem = getPendingItem(getString(R.string.in_pending_withdrawals), item.recent_status_date, item.withdraw);
                pendingTransferItemGoup.items.add(pendingTransferItem);
            }
        }

        double currentBalance = settled + pendingDeposit - pendingWithdraw;
        balanceField.setText( StringUtil.toSentFormat(currentBalance, 2) );
        pendingTransferItemGoup.inPendingDeposit = pendingDeposit;
        pendingTransferItemGoup.inPendingWithdraw = pendingWithdraw;
    }

    private PendingTransferItem getPendingItem(String type, long recent_status_date, double balance) {
        Date date = new Date(TimeUnit.MILLISECONDS.convert(recent_status_date / 1000, TimeUnit.SECONDS) );

        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(date);

        PendingTransferItem pendingTransferItem = new PendingTransferItem();
        pendingTransferItem.type = type;
        pendingTransferItem.balance = balance;
        pendingTransferItem.create_date = CalculationUtil.getDate(calendar);
        pendingTransferItem.clear_date = CalculationUtil.getClearDate(calendar);
        return pendingTransferItem;
    }

    //  =======================================================================================

    @Override
    public void onResponse(Call call, Response response) {
        update(response);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        log.show( call.request().url().toString() + " / " + t.getLocalizedMessage() );
    }

    //  =======================================================================================

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.transferButton:
                IntentFactory.transferToBank(this, authItem, settled);
                break;

            case R.id.balanceLayer:
                IntentFactory.pendingToTransfer(this, authItem, pendingTransferItemGoup);
                break;
        }
    }






}
