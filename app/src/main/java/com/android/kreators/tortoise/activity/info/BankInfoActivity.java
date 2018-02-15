package com.android.kreators.tortoise.activity.info;

import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.factory.FragmentFactory;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.nobug.core.BaseFragment;

import java.util.ArrayList;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class BankInfoActivity extends BaseInfoActivity {

    //  =====================================================================================

    @Override
    protected int getEventType() {
        return EventType.BANK_INFO_INDEX;
    }

    @Override
    protected ArrayList<BaseFragment> getFragments() {
        return FragmentFactory.getBankInfoFragments();
    }

    @Override
    protected void next() {
        int current = viewPager.getCurrentItem() + 1;

        if( current == fragmentCount ) {
            IntentFactory.goalInfo(this);
            return;
        }

        if( current < fragmentCount ) {
            viewPager.setCurrentItem(current);
        }
    }

    //  =====================================================================================



}
