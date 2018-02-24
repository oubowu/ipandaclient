package com.oubowu.ipanda.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Oubowu on 2018/2/7 15:58.
 */
public class PandaLiveFragmentAdapter extends FragmentPagerAdapter {

    @NonNull
    public List<Fragment> getFragments() {
        return mFragments;
    }

    @NonNull
    private List<Fragment> mFragments;
    @NonNull
    private List<String> mTitles;

    public PandaLiveFragmentAdapter(FragmentManager fm, @NonNull List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
