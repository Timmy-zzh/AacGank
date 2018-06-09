package com.timmy.aacgank.ui.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.timmy.aacgank.ui.gank.GankPagerFragment;

public class GankPagerAdapter extends FragmentPagerAdapter {

    private final String[] gankTypes;

    public GankPagerAdapter(FragmentManager fm, String[] gankTypes) {
        super(fm);
        this.gankTypes = gankTypes;
    }

    @Override
    public Fragment getItem(int position) {
        return GankPagerFragment.newInstance(gankTypes[position],position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return gankTypes[position];
    }

    @Override
    public int getCount() {
        return gankTypes.length;
    }
}
