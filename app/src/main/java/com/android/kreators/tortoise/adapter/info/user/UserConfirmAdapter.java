package com.android.kreators.tortoise.adapter.info.user;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.model.BaseItem;
import com.android.kreators.tortoise.model.auth.AuthUserItem;

import java.util.ArrayList;

/**
 * Created by seonjonghun on 2017. 4. 14..
 */

public class UserConfirmAdapter extends ArrayAdapter<BaseItem> {

    private ArrayList<BaseItem> items;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;
    private Context mContext;
    private AuthUserItem bankItem;

    //  ========================================================================================

    public UserConfirmAdapter(Context context, ArrayList<BaseItem> items, AuthUserItem bankItem) {
        super(context, 0, items);

        mContext = context;
        this.items = items;
        this.bankItem = bankItem;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        final BaseItem item = items.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_info_user_confirm_item, null, false);
            viewHolder.field = (TextView) view.findViewById(R.id.field);
            viewHolder.bottomLine = (FrameLayout) view.findViewById(R.id.bottomLine);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, item);
        return view;
    }

    private void update(ViewHolder holder, BaseItem item) {
        try {
            holder.field.setText( item.title );

            int emptyColor = ContextCompat.getColor(mContext, R.color.color_ef4b4c );
            int defaultColor = ContextCompat.getColor(mContext, R.color.color_dddddd );
            int fieldColor = ContextCompat.getColor(mContext, R.color.color_212121 );

            if( item.status == -1 ) {
                holder.field.setTextColor(emptyColor);
                holder.bottomLine.setBackgroundColor(emptyColor);
            } else {
                if( item.status == 0 ) {
                    holder.field.setTextColor(defaultColor);
                } else {
                    holder.field.setTextColor(fieldColor);
                }
                holder.bottomLine.setBackgroundColor(defaultColor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =======================================================================================

    public class ViewHolder {

        public TextView field;
        public FrameLayout bottomLine;

    }


}

