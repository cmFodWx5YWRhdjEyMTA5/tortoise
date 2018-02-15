package com.android.kreators.tortoise.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.view.hexagon.Hexagon;
import com.android.nobug.util.DisplayUtil;
import com.android.nobug.util.StringUtil;
import com.android.nobug.util.log;

import java.util.ArrayList;

/**
 * Created by rrobbie on 28/11/2017.
 */

public class FriendStepAdapter extends ArrayAdapter<AuthItem> {

    private Context mContext;
    private ArrayList<AuthItem> items;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;

    //  ========================================================================================

    public FriendStepAdapter(Context context, ArrayList<AuthItem> items) {
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

        AuthItem item = items.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_friend_item, null, false);
            viewHolder.hexagon = (Hexagon) view.findViewById(R.id.hexagon);
            viewHolder.nameField = (TextView) view.findViewById(R.id.nameField);
            viewHolder.stepField = (TextView) view.findViewById(R.id.stepField);

            int px = DisplayUtil.dip2px(mContext, 5);
            viewHolder.hexagon.setBorderThickness(px);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, item);
        return view;
    }

    private void update(final ViewHolder viewHolder, final AuthItem item) {
        try {
            viewHolder.stepField.setText( StringUtil.toNumFormat(item.step) );
            viewHolder.nameField.setText( item.first_name + "\n" + item.last_name );

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    log.show( "step : " + item.step +" / " + item.step_goal );

                    viewHolder.hexagon.setProgress( item.step , item.step_goal );
                    handler.removeCallbacks(this);
                }
            }, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =======================================================================================

    public class ViewHolder {

        public Hexagon hexagon;
        public TextView nameField;
        public TextView stepField;

    }


}



