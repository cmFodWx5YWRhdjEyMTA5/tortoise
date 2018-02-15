package com.android.kreators.tortoise.activity.info;

import android.support.v4.view.ViewPager;

import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.event.TortoiseKeyboardEvent;
import com.android.kreators.tortoise.factory.FragmentFactory;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.kreators.tortoise.fragment.info.goal.GoalDepositFragment;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.log;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class GoalInfoActivity extends BaseInfoActivity {

    private GoalDepositFragment goalDepositFragment;

    //  =====================================================================================

    @Override
    public void setProperties() {
        super.setProperties();

        goalDepositFragment = (GoalDepositFragment) fragments.get(fragmentCount-1);
    }

    @Override
    public void configureListener() {
        super.configureListener();
    }

    @Override
    protected int getEventType() {
        return EventType.GOAL_INFO_INDEX;
    }

    @Override
    protected ArrayList<BaseFragment> getFragments() {
        return FragmentFactory.getGoalInfoFragments();
    }

    @Override
    protected void next() {
        int current = viewPager.getCurrentItem() + 1;

        if( current == fragmentCount ) {
            IntentFactory.setPatternActivity(this);
            return;
        }

        if( current < fragmentCount ) {
            viewPager.setCurrentItem(current);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if( position == 2 ) {
            goalDepositFragment.slideUp();
        } else {
            goalDepositFragment.slideDown();
        }
    }

    //  =====================================================================================

    @Subscribe
    public void OnTortoiseKeyboardEvent(TortoiseKeyboardEvent event) {
        try {
            String item = "$" + event.data;
            goalDepositFragment.update(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
