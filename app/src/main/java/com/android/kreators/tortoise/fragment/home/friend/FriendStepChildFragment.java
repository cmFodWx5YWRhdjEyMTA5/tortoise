package com.android.kreators.tortoise.fragment.home.friend;

import android.os.Bundle;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.FriendStepAdapter;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.model.friend.FriendGroup;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.view.listview.BaseGridView;

import java.util.ArrayList;

/**
 * Created by rrobbie on 05/12/2017.
 */

public class FriendStepChildFragment extends BaseFragment {

    public static int FRIEND_COUNT = 4;

    private int position = 0;
    private FriendGroup group;

    private BaseGridView gridView;
    private FriendStepAdapter friendStepAdapter;

    //  ====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_friend_step_child;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        try {
            position = (Integer) args.get(IntentProperty.POSITION);
            group = (FriendGroup) args.getSerializable(FriendStepChildFragment.class.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ====================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        gridView = mView.findViewById(R.id.gridView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        update();
    }

    //  ====================================================================================

    private void update() {
        int groupSize = group.data.size();
        int start = position * FRIEND_COUNT;
        int end = (groupSize > (start+FRIEND_COUNT)) ? start + FRIEND_COUNT : groupSize;

        ArrayList<AuthItem> items = new ArrayList<>(group.data.subList(start, end));
        friendStepAdapter = new FriendStepAdapter(getActivity(), items);
        gridView.setAdapter(friendStepAdapter);
    }


}
