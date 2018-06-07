package com.timmy.aacgank.ui.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.timmy.aacgank.ui.gank.GankPagerFragment;


/**
 * Created by Timmy on 2018/1/23.
 */

public class GankPagerAdapter extends FragmentPagerAdapter {

    private final String[] gankTypes;

    public GankPagerAdapter(FragmentManager fm, String[] gankTypes) {
        super(fm);
        this.gankTypes = gankTypes;
    }

    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return GankPagerMainFragment.newInstance(gankTypes[position], position);
//            case 1:
//                return GankPagerMainFragment1.newInstance(gankTypes[position], position);
//            case 2:
//                return GankPagerMainFragment2.newInstance(gankTypes[position], position);
//            case 3:
//                return GankPagerMainFragment3.newInstance(gankTypes[position], position);
//            case 4:
//                return GankPagerMainFragment4.newInstance(gankTypes[position], position);
//            default:
//                return GankPagerMainFragment.newInstance(gankTypes[position], position);
//        }
//        return GankPagerMainFragment.newInstance(gankTypes[position],position);
        return GankPagerFragment.newInstance(gankTypes[position],position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return gankTypes.length;
    }
}
