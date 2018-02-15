package com.android.kreators.tortoise.factory;

import android.content.Context;
import android.os.Bundle;

import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.fragment.history.HistoryDailyFragment;
import com.android.kreators.tortoise.fragment.history.HistoryTotalFragment;
import com.android.kreators.tortoise.fragment.home.HomeFragment;
import com.android.kreators.tortoise.fragment.home.daily.DailyStepChildFragment;
import com.android.kreators.tortoise.fragment.home.friend.FriendStepChildFragment;
import com.android.kreators.tortoise.fragment.info.bank.BankAccountFragment;
import com.android.kreators.tortoise.fragment.info.bank.BankNameFragment;
import com.android.kreators.tortoise.fragment.info.bank.BankSelectAccountFragment;
import com.android.kreators.tortoise.fragment.info.goal.GoalDepositFragment;
import com.android.kreators.tortoise.fragment.info.goal.GoalStepFragment;
import com.android.kreators.tortoise.fragment.info.user.UserAddressFragment;
import com.android.kreators.tortoise.fragment.info.InfoGuideFragment;
import com.android.kreators.tortoise.fragment.info.user.UserCityFragment;
import com.android.kreators.tortoise.fragment.info.user.UserConfirmFragment;
import com.android.kreators.tortoise.fragment.info.user.UserNameFragment;
import com.android.kreators.tortoise.fragment.info.user.UserPhoneFragment;
import com.android.kreators.tortoise.fragment.tutorial.TutorialStepFragment;
import com.android.kreators.tortoise.model.friend.FriendGroup;
import com.android.kreators.tortoise.model.step.chart.UserStepGroupData;
import com.android.nobug.core.BaseFragment;

import java.util.ArrayList;

public class FragmentFactory {

    //  ======================================================================================

    public static ArrayList<BaseFragment> getMain() {
        ArrayList<BaseFragment> items = new ArrayList<BaseFragment>();
        HomeFragment fragment = new HomeFragment();
        items.add(fragment);
        return items;
    }

    //  ======================================================================================

    public static ArrayList<BaseFragment> getTutorialStepFragments(Context context, int[] backgroundImages, int[] images, int[] titles, int[] descriptions){
        int len = titles.length;
        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();

        for (int i = 0; i<len; i++) {
            TutorialStepFragment fragment = new TutorialStepFragment();
            String title = context.getString(titles[i]);
            String description = context.getString(descriptions[i]);

            Bundle args = new Bundle();
            args.putInt(IntentProperty.POSITION, i);
            args.putInt(IntentProperty.BACKGROUND, backgroundImages[i]);
            args.putInt(IntentProperty.IMAGE, images[i]);
            args.putString(IntentProperty.TITLE, title);
            args.putString(IntentProperty.DESCRIPTION, description);
            fragment.setArguments(args);
            fragments.add(fragment);
        }
        return fragments;
    }

    //  ======================================================================================

    public static ArrayList<BaseFragment> getHistoryFragments(long seq){
        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();
        Bundle args = new Bundle();
        args.putLong(IntentProperty.SEQ, seq);

        HistoryDailyFragment dailyFragment = new HistoryDailyFragment();
        HistoryTotalFragment totalFragment = new HistoryTotalFragment();

        dailyFragment.setArguments(args);
        totalFragment.setArguments(args);

        fragments.add(dailyFragment);
        fragments.add(totalFragment);
        return fragments;
    }

    public static ArrayList<BaseFragment> getDailyStepFragments(UserStepGroupData item){
        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();
        int size = 4;

        for (int i =0; i<size; i++) {
            DailyStepChildFragment dailyStepChildFragment = new DailyStepChildFragment();
            Bundle args = new Bundle();
            args.putInt(IntentProperty.POSITION, i );
            args.putSerializable(DailyStepChildFragment.class.toString(), item);
            dailyStepChildFragment.setArguments(args);
            fragments.add(dailyStepChildFragment);
        }
        return fragments;
    }

    public static ArrayList<BaseFragment> getFriendStepFragments(FriendGroup group){
        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();

        int groupSize = group.data.size();
        int count = FriendStepChildFragment.FRIEND_COUNT;
        int size = 1;

        if( groupSize > count ) {
            size = ( groupSize % count == 0 ) ?  (groupSize / count) : (groupSize / count + 1);
        }

        for (int i =0; i<size; i++) {
            FriendStepChildFragment stepFragment = new FriendStepChildFragment();
            Bundle args = new Bundle();
            args.putInt(IntentProperty.POSITION, i );
            args.putSerializable(FriendStepChildFragment.class.toString(), group);
            stepFragment.setArguments(args);
            fragments.add(stepFragment);
        }
        return fragments;
    }

    //  ======================================================================================

    public static ArrayList<BaseFragment> getUserInfoFragments(){
        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();
        Bundle args = new Bundle();
        args.putInt(IntentProperty.POSITION, 0);

        InfoGuideFragment guideFragment = new InfoGuideFragment();
        guideFragment.setArguments(args);

        UserNameFragment nameFragment = new UserNameFragment();
        UserPhoneFragment phoneFragment = new UserPhoneFragment();
        UserAddressFragment addressFragment = new UserAddressFragment();
        UserConfirmFragment confirmFragment = new UserConfirmFragment();
        UserCityFragment cityFragment = new UserCityFragment();

        fragments.add(guideFragment);
        fragments.add(nameFragment);
        fragments.add(phoneFragment);
        fragments.add(addressFragment);
        fragments.add(confirmFragment);
        fragments.add(cityFragment);

        return fragments;
    }

    //  ======================================================================================

    public static ArrayList<BaseFragment> getBankInfoFragments(){
        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();
        Bundle args = new Bundle();
        args.putInt(IntentProperty.POSITION, 1);

        InfoGuideFragment guideFragment = new InfoGuideFragment();
        guideFragment.setArguments(args);
        BankNameFragment bankNameFragment = new BankNameFragment();
        BankAccountFragment bankAccountFragment = new BankAccountFragment();
        BankSelectAccountFragment bankSelectAccountFragment = new BankSelectAccountFragment();

        fragments.add(guideFragment);
        fragments.add(bankNameFragment);
        fragments.add(bankAccountFragment);
        fragments.add(bankSelectAccountFragment);
        return fragments;
    }

    //  ======================================================================================

    public static ArrayList<BaseFragment> getGoalInfoFragments(){
        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();
        Bundle args = new Bundle();
        args.putInt(IntentProperty.POSITION, 2);

        InfoGuideFragment guideFragment = new InfoGuideFragment();
        guideFragment.setArguments(args);
        GoalStepFragment goalStepFragment = new GoalStepFragment();
        GoalDepositFragment goalDepositFragment = new GoalDepositFragment();

        fragments.add(guideFragment);
        fragments.add(goalStepFragment);
        fragments.add(goalDepositFragment);

        return fragments;
    }


}


