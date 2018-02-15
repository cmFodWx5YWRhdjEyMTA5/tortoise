package com.android.kreators.tortoise.fragment.info.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.info.user.UserConfirmAdapter;
import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.event.IndexEvent;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.kreators.tortoise.model.BaseItem;
import com.android.kreators.tortoise.model.auth.AuthUserItem;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.nobug.event.provider.BusProvider;
import com.android.nobug.view.listview.BaseListView;

import java.util.ArrayList;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class UserConfirmFragment extends BaseInfoFragment implements AdapterView.OnItemClickListener {

    private BaseListView listView;
    private UserConfirmAdapter userConfirmAdapter;

    private ArrayList<BaseItem> items;

    //  ======================================================================================

    private final int[] hints = new int[] {
            R.string.first_name,
            R.string.last_name,
            R.string.hint_phone,
            R.string.hint_birth,
            R.string.hint_address_1,
            R.string.hint_address_2_option,
            R.string.city,
            R.string.state,
            R.string.zip_code
    };

    //  ======================================================================================

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        if (args != null) {
            //  position = args.getInt(IntentProperty.POSITION, 0);
        }
    }

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_user_confirm;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        listView = (BaseListView) mView.findViewById(R.id.listView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        UserCacheItem userCacheItem = PreferenceFactory.getUserItem(getActivity());
        userConfirmAdapter = new UserConfirmAdapter(getActivity(), getItems(), userCacheItem.authUserItem);
        listView.setAdapter(userConfirmAdapter);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        listView.setOnItemClickListener(this);
    }

    //  ======================================================================================

    private ArrayList<BaseItem> getItems() {
        ArrayList<BaseItem> items = new ArrayList<>();

        try {
            int size = hints.length;

            UserCacheItem userCacheItem = PreferenceFactory.getUserItem(getActivity());
            AuthUserItem authUserItem = userCacheItem.authUserItem;

            for (int i=0; i<size; i++) {
                BaseItem item = new BaseItem();
                item.status = 0;
                item.title = getString(hints[i]);

                int id = hints[i];

                if( id == R.string.hint_address_2_option ) {
                    if( authUserItem.address_detail != null ) {
                        item.status = 1;
                        item.title = authUserItem.address_detail;
                    }
                } else if( id == R.string.city  ) {
                    if( authUserItem.address_city != null ) {
                        item.title = authUserItem.address_city;
                        item.status = 1;
                    } else {
                        item.status = -1;
                    }
                }
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    //  ======================================================================================

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int index = 0;

        if( position < 2 ) {
            index = 1;
        } else if( position > 1 && position < 4 ) {
            index = 2;
        } else {
            if( position == 6 ) {
                UserCacheItem userCacheItem = PreferenceFactory.getUserItem(getActivity());
                AuthUserItem authUserItem = userCacheItem.authUserItem;
                index = ( authUserItem.address_city == null ) ? 5 : 3;
            } else {
                index = 3;
            }
        }

        BusProvider.getInstance().post(new IndexEvent(EventType.USER_INFO_INDEX, index));
    }

    

}
