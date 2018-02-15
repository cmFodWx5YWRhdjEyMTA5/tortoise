package com.android.kreators.tortoise.fragment.home.daily;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.factory.FragmentFactory;
import com.android.kreators.tortoise.model.step.chart.UserStepGroup;
import com.android.kreators.tortoise.model.step.chart.UserStepItem;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.log;
import com.android.nobug.view.viewpager.adapter.FragmentAdapter;
import com.android.nobug.view.viewpager.indicator.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rrobbie on 05/12/2017.
 */

public class MyStepFragment extends BaseFragment implements Callback {

    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    private FragmentAdapter fragmentAdapter;

    private ProgressBar progressBar;

    private long user_seq = 0;

    //  ====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_my_step;
    }

    //  ====================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        viewPager = (ViewPager) mView.findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator) mView.findViewById(R.id.indicator);
        progressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
    }

    //  ====================================================================================

    public void setItem(long seq) {
        user_seq = seq;
        request();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void request() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            String end_date = simpleDateFormat.format(cal.getTime());

            cal.setTime(date);
            cal.add(Calendar.DATE, -28);
            String start_date = simpleDateFormat.format(cal.getTime());

            RetrofitBuilder.withUnsafe(getActivity()).getDailySteps(user_seq, start_date, end_date).enqueue(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Response response) {
        try {
            UserStepGroup group = (UserStepGroup) response.body();
            group.data.update();

            Collections.reverse(group.data.stepList);

            fragmentAdapter = new FragmentAdapter(getActivity(), getChildFragmentManager(), FragmentFactory.getDailyStepFragments(group.data));
            viewPager.setAdapter(fragmentAdapter);
            viewPager.setOffscreenPageLimit(fragmentAdapter.getCount());
            indicator.setViewPager(viewPager);
            viewPager.setCurrentItem( (fragmentAdapter.getCount()-1) );

        } catch (Exception e) {
            e.printStackTrace();
        }

        progressBar.setVisibility(View.GONE);
    }

    //  ====================================================================================

    @Override
    public void onResponse(Call call, Response response) {
        update(response);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        progressBar.setVisibility(View.GONE);
    }
}
