package com.android.kreators.tortoise.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.info.InfoGuideAdapter;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.model.info.InfoGuideItem;
import com.android.nobug.view.listview.BaseListView;

import java.util.ArrayList;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class InfoGuideFragment extends BaseInfoFragment {

    private TextView titleField;
    private TextView descriptionField;
    private BaseListView listView;
    private InfoGuideAdapter infoGuideAdapter;

    //  ======================================================================================

    private int position = 0;

    private final int[] titles = new int[] {
            R.string.user_info_title,
            R.string.bank_info_title,
            R.string.goal_info_title
    };

    private final int[] menuTitles = new int[] {
            R.string.user_info,
            R.string.bank_info,
            R.string.set_your_goal
    };

    private final int[] descriptions = new int[] {
            R.string.user_info_description,
            R.string.bank_info_description,
            R.string.goal_info_description
    };

    //  ======================================================================================


    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        if (args != null) {
            position = args.getInt(IntentProperty.POSITION, 0);
        }
    }

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_guide;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        titleField = (TextView) mView.findViewById(R.id.titleField);
        descriptionField = (TextView) mView.findViewById(R.id.descriptionField);

        listView = (BaseListView) mView.findViewById(R.id.listView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        update();
    }

    @Override
    public void configureListener() {
        super.configureListener();
    }

    //  ======================================================================================

    private ArrayList<InfoGuideItem> getItems() {
        ArrayList<InfoGuideItem> items = new ArrayList<>();
        int size = menuTitles.length;

        for (int i = 0; i<size; i++) {
            InfoGuideItem item = new InfoGuideItem();
            if( position == i ) {
                item.status = 1;
            } else {
                if( position > i ) {
                    item.status = 2;
                } else {
                    item.status = 0;
                }
            }
            item.number = (i + 1) + "";
            item.title = getString(menuTitles[i]);
            items.add( item );
        }

        return items;
    }

    private void update() {
        try {
            String title = getString(titles[position]);
            String description = getString(descriptions[position]);

            titleField.setText( title );
            descriptionField.setText( description );

            infoGuideAdapter = new InfoGuideAdapter(getActivity(), getItems());
            listView.setAdapter( infoGuideAdapter );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ======================================================================================

    @Override
    public boolean next() {
        return true;
    }



}
