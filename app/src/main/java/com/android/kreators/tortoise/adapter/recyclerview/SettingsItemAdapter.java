package com.android.kreators.tortoise.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.recyclerview.holder.setting.SettingATypeHolder;
import com.android.kreators.tortoise.adapter.recyclerview.holder.setting.SettingBTypeHolder;
import com.android.kreators.tortoise.adapter.recyclerview.holder.setting.SettingCTypeHolder;
import com.android.kreators.tortoise.adapter.recyclerview.holder.setting.SettingDTypeHolder;
import com.android.kreators.tortoise.model.setting.SettingsModeltem;

import java.util.ArrayList;

/**
 * Created by rrobbie on 2017. 11. 2..
 */

public class SettingsItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_A = 0;
    public static final int VIEW_TYPE_B = 1;
    public static final int VIEW_TYPE_C = 2;
    public static final int VIEW_TYPE_D = 3;

    private ArrayList<SettingsModeltem> mItems = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType == VIEW_TYPE_B ) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_setting_switch_item, parent, false);
            return new SettingBTypeHolder(itemView);
        } else if( viewType == VIEW_TYPE_C ) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_setting_text_item, parent, false);
            return new SettingCTypeHolder(itemView);
        } else if( viewType == VIEW_TYPE_D ) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_setting_preference, parent, false);
            return new SettingDTypeHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_setting_item, parent, false);
        return new SettingATypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SettingsModeltem item = getItem(position);

        if( holder instanceof SettingATypeHolder ) {
            ((SettingATypeHolder) holder).textField.setText(item.title);
        } else if( holder instanceof SettingBTypeHolder ) {
            ((SettingBTypeHolder) holder).textField.setText(item.title);
            ((SettingBTypeHolder) holder).switchButton.setFocusable(false);
            ((SettingBTypeHolder) holder).switchButton.setFocusableInTouchMode(false);
            //  ((SettingBTypeHolder) holder).switchButton.setOnCheckedChangeListener(this);
        } else if( holder instanceof SettingCTypeHolder) {
            ((SettingCTypeHolder) holder).textField.setText(item.title);
            ((SettingCTypeHolder) holder).descriptionField.setText(item.description);
        } else if( holder instanceof SettingDTypeHolder ) {
            ((SettingDTypeHolder) holder).textField.setText(item.title);
        }
    }

    private SettingsModeltem getItem(int position) {
        return mItems.get(position);
    }

    public void setItems(ArrayList<SettingsModeltem> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).type == VIEW_TYPE_A ) return VIEW_TYPE_A;
        if (getItem(position).type == VIEW_TYPE_B ) return VIEW_TYPE_B;
        if (getItem(position).type == VIEW_TYPE_C ) return VIEW_TYPE_C;
        return VIEW_TYPE_D;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }



}
