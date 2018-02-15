package com.android.kreators.tortoise.view.keyboard;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.component.NumberKeyboardAdapter;
import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.event.TortoiseKeyboardEvent;
import com.android.nobug.core.BaseRelativeLayout;
import com.android.nobug.event.provider.BusProvider;
import com.android.nobug.view.listview.BaseGridView;

import java.util.ArrayList;

/**
 * Created by rrobbie on 13/12/2017.
 */

public class NumberKeyboard extends BaseRelativeLayout implements AdapterView.OnItemClickListener {

    private BaseGridView gridView;
    private NumberKeyboardAdapter numberKeyboardAdapter;
    private StringBuilder stringBuilder;

    private int ITEM_SIZE = 12;

    private ArrayList<String> items;

    //  =======================================================================================

    public NumberKeyboard(Context context) {
        super(context);
    }

    public NumberKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getLayoutContentView() {
        return R.layout.component_number_keyboard;
    }

    //  =======================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        gridView = (BaseGridView) findViewById(R.id.gridView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        stringBuilder = new StringBuilder();
        items = getItems();
        boolean isGreen = getTag() == null ? false : true;
        numberKeyboardAdapter = new NumberKeyboardAdapter(mContext, items, isGreen);
        gridView.setAdapter(numberKeyboardAdapter);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        gridView.setOnItemClickListener(this);
    }

    //  ======================================================================================

    private void update(int position) {
        try {
            String item = items.get(position);

            if( position == 9 ) {
                if( stringBuilder.toString().contains(".") ) {
                    return;
                }
            } else if( position == 11 ) {
                if( stringBuilder.length() > 0 ) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    BusProvider.getInstance().post(new TortoiseKeyboardEvent(EventType.NUMBER, stringBuilder.toString()));
                    return;
                }
            }

            stringBuilder.append(item);
            BusProvider.getInstance().post(new TortoiseKeyboardEvent(EventType.NUMBER, stringBuilder.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getItems() {
        ArrayList<String> temp = new ArrayList<>();

        for (int i= 0; i<ITEM_SIZE; i++) {
            String value = "";

            if( i == 9 ) {
                value = ".";
            } else if( i == 10 ) {
                value = "0";
            } else if( i == 11 ) {
                value = "";
            } else {
               value = (i + 1) + "";
            }
            temp.add(value);
        }

        return temp;
    }

    //  ======================================================================================

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        update(position);
    }

}
