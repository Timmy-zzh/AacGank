package com.timmy.aacgank.ui.multimedia.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentSmallVideoBinding;
import com.timmy.baselib.basemvvm.fragment.TPageLazyBaseFragment;
import com.timmy.baselib.utils.LogUtils;

public class SmallVideoFragment extends TPageLazyBaseFragment<FragmentSmallVideoBinding> {

    private String tabTitles[] = new String[]{"热门", "话题"};

    public static SmallVideoFragment newInstance() {
        SmallVideoFragment fragment = new SmallVideoFragment();
        return fragment;
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_small_video;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void lazyLoadData() {
        LogUtils.d("SmallVideoFragment  lazyLoadData  ");
        initView();
    }

    @Override
    protected void initBase() {
        super.initBase();
        LogUtils.d("SmallVideoFragment  initBase  ");
        showContentLayout();
    }

    private void initView() {
        binding.viewPager.setAdapter(new SmallVideoFragment.TabPagerAdapter(this.getChildFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.viewPager.setOffscreenPageLimit(2);
    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HotVideoFragment.newInstance();
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}

