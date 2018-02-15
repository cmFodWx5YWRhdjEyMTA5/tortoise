package com.android.kreators.tortoise.manager;

import android.content.Context;

import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.model.balance.BalanceItem;
import com.android.kreators.tortoise.model.config.InterestRateItem;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.kreators.tortoise.view.hexagon.Hexagon;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.log;

import java.util.ArrayList;

/**
 * Created by rrobbie on 2017. 9. 27..
 */

public class BalanceManager {

    private static BalanceManager uniqueInstance;

    //  =======================================================================================

    public static BalanceManager getInstance() {
        if( uniqueInstance == null ) {
            uniqueInstance = new BalanceManager();
        }
        return uniqueInstance;
    }

    public double getCurrentBalance(Context context) {
        return 0;
    }

    public double getEstimatedBalance(Context context) {
        BalanceItem item = CacheManager.getInstance().getBalanceItem();

        double avaliable = getAvaliableValue(item);
        double walking = getWalkingValue(context);
        double dailyBonus = getDailyInterestRateValue(context, item);
        double estimated = avaliable + item.pending_total + item.saving_bonus_total + (walking + dailyBonus);
        return estimated;
    }

    public double getAvaliableValue(BalanceItem item) {
        return item.icon_deposit_total + item.manual_deposit_total + item.daily_deposit_total + item.saving_bonus_total + item.saving_bonus_total - item.withdraw_total;
    }

    public double getWalkingValue(Context context) {
        BaseStepItem item = PreferenceFactory.getCurrentStepItem(context);

        if( item.step == 0 || item.step_goal == 0 || item.deposit_goal == 0 )
            return 0;
        return item.step / item.step_goal * item.deposit_goal;
    }

    public double getDailyInterestRateValue(Context context, BalanceItem item) {
        BaseStepItem stepItem = PreferenceFactory.getCurrentStepItem(context);
        int step = stepItem.step > stepItem.step_goal ? stepItem.step_goal : stepItem.step;
        return getAvaliableValue(item) *  (getInterestRate(step).daily_interest_rate / 100);
    }

    public static InterestRateItem getInterestRate(int step) {
        ArrayList<InterestRateItem> items = CacheManager.getInstance().getSettingItem().interest_rates;
        int size = items.size();
        InterestRateItem resultItem = items.get(0);

        for (int i=0; i<size; i++) {
            InterestRateItem item = items.get(i);
            if( item.interval < step ) {
                resultItem = item;
            }
        }
        return resultItem;
    }

    public double getDoublePercent(int step, int stepGoal) {
        double dStep = step;
        double dStepGoal = stepGoal;
        return (dStep / dStepGoal);
    }

    public int getAccelerationTime(Context context) {
        BaseStepItem stepItem = PreferenceFactory.getCurrentStepItem(context);
        double dTime = Hexagon.INTERVAL * getDoublePercent(stepItem.step, stepItem.step_goal);
        int time = (int)dTime > 700 ? (int)dTime : 700;
        return time;
    }

    //  =======================================================================================



}
