package com.android.kreators.tortoise.adapter.myaccount;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.model.myaccount.pendingtransfer.PendingTransferItem;
import com.android.nobug.util.StringUtil;

import java.util.ArrayList;

/**
 * Created by rrobbie on 12/12/2017.
 */

public class PendingTransferAdapter extends ArrayAdapter<PendingTransferItem> {

    private Context mContext;
    private ArrayList<PendingTransferItem> items;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;

    //  ========================================================================================

    public PendingTransferAdapter(Context context, ArrayList<PendingTransferItem> items) {
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

        final PendingTransferItem item = items.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_pending_transfer_item, null, false);
            viewHolder.statusField = (TextView) view.findViewById(R.id.statusField);
            viewHolder.balanceField = (TextView) view.findViewById(R.id.balanceField);
            viewHolder.createDateField = (TextView) view.findViewById(R.id.createDateField);
            viewHolder.clearDateField = (TextView) view.findViewById(R.id.clearDateField);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, item);
        return view;
    }

    // =======================================================================================

    private void update(ViewHolder holder, PendingTransferItem item) {
        try {
            holder.statusField.setText( item.type );
            holder.balanceField.setText(StringUtil.toSentFormat(item.balance, 2));

            String create = mContext.getString(R.string.create_on) + " " + item.create_date;
            String clear = mContext.getString(R.string.estimated_clear_date_is) + " " + item.clear_date;

            Spannable createText = new SpannableString( create );
            Spannable clearText = new SpannableString( clear );

            StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD);
            createText.setSpan(bold, (create.length() - item.create_date.length()), create.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            clearText.setSpan(bold, (clear.length() - item.clear_date.length()), clear.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            holder.createDateField.setText( createText );
            holder.clearDateField.setText( clearText );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =======================================================================================

    public class ViewHolder {

        public TextView statusField;
        public TextView balanceField;
        public TextView createDateField;
        public TextView clearDateField;

    }

}