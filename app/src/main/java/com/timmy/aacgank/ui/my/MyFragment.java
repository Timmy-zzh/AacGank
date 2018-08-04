package com.timmy.aacgank.ui.my;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentMyBinding;
import com.timmy.aacgank.ui.person.TechnologyPageFragment;
import com.timmy.aacgank.ui.person.behavior.BehaviorActivity;
import com.timmy.baselib.base.fragment.TBaseContentFragment;

/**
 * 高仿京东个人中心
 */
public class MyFragment extends TBaseContentFragment<FragmentMyBinding> {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initBase() {
        super.initBase();
        binding.setFragment(this);
        initView();
    }

    private void initView() {
        binding.tlTab.setupWithViewPager(binding.viewPager);
        binding.viewPager.setAdapter(new TabPagerAdapter(this.getChildFragmentManager()));
        binding.viewPager.setOffscreenPageLimit(4);

        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //设置Toolbar背景色变化

            }
        });


    }

    public void onViewClicked(View view) {
//        startActivity(new Intent(getContext(), CitySelectActivity.class));
        startActivity(new Intent(getContext(), BehaviorActivity.class));
    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {
        private String tabTitles[] = new String[]{"高级ui", "自定义控件", "项目技术点", "框架"};

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TechnologyPageFragment.newInstance(position + 1);
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
