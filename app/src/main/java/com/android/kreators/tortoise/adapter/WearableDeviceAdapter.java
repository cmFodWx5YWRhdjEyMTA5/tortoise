package com.android.kreators.tortoise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by seonjonghun on 2017. 4. 14..
 */

public class WearableDeviceAdapter extends ArrayAdapter<Integer> {

    private ArrayList<Integer> items;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;
    private Context mContext;

    //  ========================================================================================

    public WearableDeviceAdapter(Context context, ArrayList<Integer> items) {
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

        final int item = items.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_wearable_device_item, null, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, item);
        return view;
    }

    private void update(ViewHolder viewHolder, int id) {
        try {
            Glide.with(mContext).load( id ).into(viewHolder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =======================================================================================

    public class ViewHolder {

        public ImageView icon;
        public ImageView imageView;

    }



}

