package com.android.kreators.tortoise.adapter.component;

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
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NumberKeyboardAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> items;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;
    private boolean isGreen;

    //  ========================================================================================

    public NumberKeyboardAdapter(Context context, ArrayList<String> items, boolean isGreen) {
        super(context, 0, items);

        mContext = context;
        this.items = items;
        this.isGreen = isGreen;
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

        final String item = items.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();

            int layout = isGreen ? R.layout.adapter_number_keyboard_item_green : R.layout.adapter_number_keyboard_item;

            view = inflater.inflate(layout, null, false);
            viewHolder.container = (RelativeLayout) view.findViewById(R.id.container);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.titleField = (TextView) view.findViewById(R.id.titleField);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, item);
        return view;
    }

    private void update(ViewHolder viewHolder, String value) {
        viewHolder.titleField.setText( value );
        viewHolder.imageView.setVisibility( "".equals(value) ? View.VISIBLE : View.INVISIBLE );
    }

    // =======================================================================================

    public class ViewHolder {

        public RelativeLayout container;
        public ImageView imageView;
        public TextView titleField;

    }



}

