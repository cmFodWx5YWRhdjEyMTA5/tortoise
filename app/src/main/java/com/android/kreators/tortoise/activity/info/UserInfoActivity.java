package com.android.kreators.tortoise.activity.info;

import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.factory.FragmentFactory;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.nobug.core.BaseFragment;

import java.util.ArrayList;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class UserInfoActivity extends BaseInfoActivity {

    //  =====================================================================================

    @Override
    protected int getEventType() {
        return EventType.BANK_INFO_INDEX;
    }

    @Override
    protected ArrayList<BaseFragment> getFragments() {
        return FragmentFactory.getUserInfoFragments();
    }

    @Override
    protected void next() {
        int position = viewPager.getCurrentItem();
        BaseInfoFragment fragment = (BaseInfoFragment) fragments.get(position);

        if( fragment.next() ) {
            int current = position + 1;

            if( current == (fragmentCount - 1) ) {
                IntentFactory.bankInfo(this);
                return;
            }

            if( current < fragmentCount ) {
                viewPager.setCurrentItem(current);
            }
        }
    }

    //  =====================================================================================



}
