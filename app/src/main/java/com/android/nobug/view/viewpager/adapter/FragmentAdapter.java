package com.android.nobug.view.viewpager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.nobug.core.BaseFragment;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private ArrayList<BaseFragment> fragments;

    //  =========================================================================================

    public FragmentAdapter(Context context, FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        mContext = context;
        this.fragments = fragments;
    }

    public void addFragment(int index, BaseFragment frg){
        if( fragments == null )
                fragments = new ArrayList<BaseFragment>();

        fragments.add(index, frg);
    }

    public Context getContext() {
        return mContext;
    }

    //  =========================================================================================

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    //  =========================================================================================




}