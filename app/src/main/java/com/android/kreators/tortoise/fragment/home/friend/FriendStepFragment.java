package com.android.kreators.tortoise.fragment.home.friend;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.factory.FragmentFactory;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.friend.FriendGroup;
import com.android.kreators.tortoise.model.step.chart.UserStepGroup;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.log;
import com.android.nobug.view.viewpager.adapter.FragmentAdapter;
import com.android.nobug.view.viewpager.indicator.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rrobbie on 05/12/2017.
 */

public class FriendStepFragment extends BaseFragment implements Callback {

    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    private FragmentAdapter fragmentAdapter;

    private ProgressBar progressBar;

    private UserCacheItem userCacheItem;

    //  ====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_friend_step;
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

    public void setItem(UserCacheItem item) {
        userCacheItem = item;
        request();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void request() {
        try {
            RetrofitBuilder.withUnsafe(getActivity()).getFriendSteps(userCacheItem.facebook_id).enqueue(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Response response) {
        try {
            FriendGroup group = (FriendGroup) response.body();
            fragmentAdapter = new FragmentAdapter(getActivity(), getChildFragmentManager(), FragmentFactory.getFriendStepFragments(group));
            viewPager.setAdapter(fragmentAdapter);
            viewPager.setOffscreenPageLimit(fragmentAdapter.getCount());
            indicator.setViewPager(viewPager);
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
        log.show( "on failure : " + call.request().url().toString() + " / " + t.getLocalizedMessage().toString() );
        progressBar.setVisibility(View.GONE);
    }



}
