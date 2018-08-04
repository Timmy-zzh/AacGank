package com.timmy.aacgank.ui.simple;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SimplePagerAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[]{"Tab1", "Tab2", "Tab3", "Tab4", "Tab5", "Tab6", "Tab7"};

    public SimplePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new SimpleFragment();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
