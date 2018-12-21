package com.timmy.aacgank.ui.multimedia.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentVideoBinding;
import com.timmy.baselib.basemvvm.fragment.TBaseContentFragment;

/**
 * 高仿UC浏览器视频模块功能
 */
public class VideoFragment extends TBaseContentFragment<FragmentVideoBinding> {

    private String tabTitles[] = new String[]{"推荐", "小视频", "播单"};

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initBase() {
        super.initBase();
        initView();
    }

    private void initView() {
        binding.viewPager.setAdapter(new VideoFragment.TabPagerAdapter(this.getChildFragmentManager()));
        binding.viewPager.setOffscreenPageLimit(3);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 1) {
                    binding.viewStatusBar.setBackgroundColor(Color.TRANSPARENT);
                    binding.flContent.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    binding.flContent.setBackgroundColor(getResources().getColor(R.color.teal));
                    binding.viewStatusBar.setBackgroundColor(getResources().getColor(R.color.teal));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //重新选择-->可添加刷新逻辑
            }
        });
    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return RecommendFragment.newInstance();
                case 1:
                    return SmallVideoFragment.newInstance();
                default:
                    return PlayerListFragment.newInstance();
            }
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
