package com.android.kreators.tortoise.adapter.info;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.model.info.InfoGuideItem;
import com.android.nobug.util.StringUtil;
import com.android.nobug.util.log;

import java.util.ArrayList;

/**
 * Created by seonjonghun on 2017. 4. 14..
 */

public class InfoGuideAdapter extends ArrayAdapter<InfoGuideItem> {

    private ArrayList<InfoGuideItem> items;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;
    private Context mContext;

    //  ========================================================================================

    public InfoGuideAdapter(Context context, ArrayList<InfoGuideItem> items) {
        super(context, 0, items);

        mContext = context;
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

        final InfoGuideItem item = items.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_info_guide_item, null, false);
            viewHolder.drawerLayer = (RelativeLayout) view.findViewById(R.id.drawerLayer);
            viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
            viewHolder.numberField = (TextView) view.findViewById(R.id.numberField);
            viewHolder.titleField = (TextView) view.findViewById(R.id.titleField);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, item);
        return view;
    }

    private void update(ViewHolder holder, InfoGuideItem item) {
        try {
            holder.numberField.setText( item.number );
            holder.titleField.setText( item.title );

            int titleDisableColor = ContextCompat.getColor(mContext, R.color.color_757575);
            int disableColor = ContextCompat.getColor(mContext, R.color.color_dddddd);
            int numberColor = ContextCompat.getColor(mContext, android.R.color.white);
            int titleColor = ContextCompat.getColor(mContext, R.color.colorPrimary);

            holder.drawerLayer.setSelected( item.status == 0 ? false : true );
            holder.icon.setVisibility( item.status == 2 ? View.VISIBLE : View.INVISIBLE );
            holder.numberField.setTextColor(item.status == 0 ? disableColor : numberColor );
            holder.numberField.setVisibility(item.status == 2 ? View.INVISIBLE : View.VISIBLE );

            if( item.status == 1 ) {
                holder.titleField.setTextColor(titleColor);
            } else if( item.status == 2 ) {
                holder.titleField.setTextColor(titleDisableColor);
            } else {
                holder.titleField.setTextColor(disableColor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =======================================================================================

    public class ViewHolder {
        public RelativeLayout drawerLayer;
        public TextView numberField;
        public TextView titleField;
        public ImageView icon;

    }


}

