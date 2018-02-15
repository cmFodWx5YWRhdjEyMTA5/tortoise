package com.android.kreators.tortoise.activity.intro;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.event.IndexEvent;
import com.android.kreators.tortoise.event.TortoiseKeyboardEvent;
import com.android.kreators.tortoise.factory.FragmentFactory;
import com.android.nobug.core.BaseActivity;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.event.provider.BusProvider;
import com.android.nobug.util.AlertUtil;
import com.android.nobug.util.CommonUtil;
import com.android.nobug.util.DeviceInfoUtil;
import com.android.nobug.util.log;
import com.android.nobug.view.viewpager.adapter.FragmentAdapter;
import com.android.nobug.view.viewpager.indicator.CirclePageIndicator;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by rrobbie on 2017. 8. 21..
 */

public class IntroActivity extends BaseActivity {

    private CirclePageIndicator indicator;
    private FragmentAdapter fragmentAdapter;
    private ViewPager viewPager;

    // ========================================================================================

    private final int[] backgroundImages = new int[] {
            R.drawable.background_tutorial,
            R.drawable.background_tutorial,
            R.drawable.background_tutorial,
            R.drawable.background_tutorial,
            R.drawable.background_tutorial,
            R.drawable.background_tutorial
    };

    private final int[] images = new int[] {
            android.R.drawable.screen_background_dark_transparent,
            R.drawable.phone_tutorial_2,
            R.drawable.phone_tutorial_3,
            R.drawable.phone_tutorial_4,
            R.drawable.phone_tutorial_5,
            android.R.drawable.screen_background_dark_transparent,
    };

    private final int[] titles = new int[] {
            R.string.tutorial_step_1_title,
            R.string.tutorial_step_2_title,
            R.string.tutorial_step_3_title,
            R.string.tutorial_step_4_title,
            R.string.tutorial_step_5_title,
            R.string.tutorial_step_6_title
    };

    private final int[] descriptions = new int[] {
            R.string.tutorial_step_1_description,
            R.string.tutorial_step_2_description,
            R.string.tutorial_step_3_description,
            R.string.tutorial_step_4_description,
            R.string.tutorial_step_5_description,
            R.string.tutorial_step_6_description
    };

    //  =======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_intro;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    // ========================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        ArrayList<BaseFragment> fragments = FragmentFactory.getTutorialStepFragments(this, backgroundImages, images, titles, descriptions);
        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager(), fragments);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(fragmentAdapter.getCount());
        indicator.setViewPager(viewPager);
    }

    //  =====================================================================================

    @Subscribe
    public void OnIndexEvent(IndexEvent event) {
        try {
            int index = event.data;
            viewPager.setCurrentItem(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
