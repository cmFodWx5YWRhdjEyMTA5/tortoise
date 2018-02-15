package com.android.kreators.tortoise.adapter.recyclerview.holder.setting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.nobug.util.DeviceInfoUtil;

/**
 * Created by rrobbie on 2017. 11. 2..
 */

public class SettingCTypeHolder extends RecyclerView.ViewHolder {

    public TextView textField;
    public TextView descriptionField;

    //  ======================================================================================

    public SettingCTypeHolder(View itemView) {
        super(itemView);

        textField = (TextView)itemView.findViewById(R.id.textField);
        descriptionField = (TextView)itemView.findViewById(R.id.descriptionField);

    }

}

