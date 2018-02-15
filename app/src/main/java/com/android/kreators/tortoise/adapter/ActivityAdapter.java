package com.android.kreators.tortoise.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.activity.ActivityItem;
import com.android.nobug.util.CalculationUtil;
import com.android.nobug.util.StringUtil;
import com.bumptech.glide.RequestManager;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rrobbie on 28/11/2017.
 */

public class ActivityAdapter extends ArrayAdapter<ActivityItem> {

    private Context mContext;
    private ArrayList<ActivityItem> items;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;

    private RequestManager mGlideRequestManager;

    //  ========================================================================================

    public ActivityAdapter(Context context, RequestManager mGlideRequestManager, ArrayList<ActivityItem> items) {
        super(context, 0, items);
        mContext = context;
        this.mGlideRequestManager = mGlideRequestManager;
        this.items = items;
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

        final ActivityItem item = items.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.holder_activity_item, null, false);
            viewHolder.dateField = (TextView) view.findViewById(R.id.dateField);
            viewHolder.timeField = (TextView) view.findViewById(R.id.timeField);
            viewHolder.messageField = (TextView) view.findViewById(R.id.messageField);
            viewHolder.moneyField = (TextView) view.findViewById(R.id.moneyField);
            viewHolder.typeField = (TextView) view.findViewById(R.id.typeField);

            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, item);
        return view;
    }

    private void update(ViewHolder viewHolder, ActivityItem item) {
        try {
            Calendar calendar = StringUtil.getDate(item.target_date, "yyyy-MM-dd HH:mm");

            viewHolder.timeField.setText(CalculationUtil.getTime(mContext, calendar) );
            viewHolder.dateField.setText( CalculationUtil.getDate(calendar) );

            int color = ContextCompat.getColor(mContext, R.color.colorPrimary);
            String message = "";
            String money = "+$" + item.deposit;
            String type = mContext.getString(R.string.deposit).toUpperCase();
            String url = CacheManager.getInstance().getVersionItem().image;

            if( item.deposit_type_num == 0 ) {
                message = mContext.getString(R.string.saved_money_walking);
                url += CacheManager.getInstance().getSettingItem().activity.walking;
            } else if( item.deposit_type_num == 1 ) {
                message = mContext.getString(R.string.saved_money_emoji);
                url += item.image_url;
            } else if( item.deposit_type_num == 2 ) {
                message = mContext.getString(R.string.saved_money_menual);
                url+= CacheManager.getInstance().getSettingItem().activity.menual;
            } else if( item.deposit_type_num == 3 ) {
                color = ContextCompat.getColor(mContext, R.color.color_ef4b4c);
                message = mContext.getString(R.string.withdraw_money);
                url+= CacheManager.getInstance().getSettingItem().activity.withdraw;
                money = "-$" + item.withdraw;
                type = mContext.getString(R.string.withdraw).toUpperCase();
            }

            viewHolder.typeField.setText(type);
            viewHolder.moneyField.setText(money);
            viewHolder.messageField.setText( String.format(message, item.name) );
            mGlideRequestManager.load( url ).into(viewHolder.imageView);

            viewHolder.moneyField.setTextColor(color);
            viewHolder.typeField.setBackgroundColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =======================================================================================

    public class ViewHolder {

        public TextView typeField;

        public TextView timeField;
        public TextView dateField;
        public TextView messageField;
        public TextView moneyField;

        public ImageView imageView;


    }


}



