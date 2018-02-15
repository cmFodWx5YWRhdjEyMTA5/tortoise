package com.android.kreators.tortoise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.config.IconItem;
import com.android.nobug.util.log;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GoalDepositAdapter extends ArrayAdapter<IconItem> {

    private Context mContext;
    private ArrayList<IconItem> items;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;

    //  ========================================================================================

    public GoalDepositAdapter(Context context, ArrayList<IconItem> items) {
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

        final IconItem item = items.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_add_deposit_item, null, false);
            viewHolder.container = (RelativeLayout) view.findViewById(R.id.container);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, item);
        return view;
    }

    // =======================================================================================

    private void update(ViewHolder holder, IconItem item) {
        String url= CacheManager.getInstance().getVersionItem().image + item.icon;
        Glide.with(mContext).load( url ).into(holder.imageView);



    }

    // =======================================================================================

    public class ViewHolder {

        public RelativeLayout container;
        public ImageView imageView;

    }



}

