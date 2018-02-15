package com.android.kreators.tortoise.view.bank;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.model.bank.BankItem;
import com.android.nobug.core.BaseRelativeLayout;
import com.android.nobug.util.CalculationUtil;
import com.android.nobug.util.StringUtil;

/**
 * Created by rrobbie on 19/12/2017.
 */

public class BankAccount extends BaseRelativeLayout {

    private TextView nameField;
    private TextView nickNameField;
    private TextView numberField;
    private TextView balanceField;

    //  ======================================================================================

    public BankAccount(Context context) {
        super(context);
    }

    public BankAccount(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BankAccount(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.component_bank_account;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        nameField = (TextView) mView.findViewById(R.id.nameField);
        nickNameField = (TextView) mView.findViewById(R.id.nickNameField);
        numberField = (TextView) mView.findViewById(R.id.numberField);
        balanceField = (TextView) mView.findViewById(R.id.balanceField);

    }

    @Override
    public void setProperties() {
        super.setProperties();

    }

    //  ======================================================================================

    public void setItem(BankItem item) {
        try {
            nickNameField.setText( item.name );
            numberField.setText( item.account_num );
            nameField.setText( item.bank_id );
            balanceField.setText(StringUtil.toSentFormat(item.balance, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ======================================================================================



}
