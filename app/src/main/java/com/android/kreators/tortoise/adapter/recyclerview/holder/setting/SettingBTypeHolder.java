package com.android.kreators.tortoise.adapter.recyclerview.holder.setting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.android.kreators.tortoise.R;

/**
 * Created by rrobbie on 2017. 11. 2..
 */

public class SettingBTypeHolder extends RecyclerView.ViewHolder {

    public TextView textField;
    public Switch switchButton;

    //  ======================================================================================

    public SettingBTypeHolder(View itemView) {
        super(itemView);

        textField = (TextView) itemView.findViewById(R.id.textField);
        switchButton = (Switch) itemView.findViewById(R.id.switchButton);
    }



}

