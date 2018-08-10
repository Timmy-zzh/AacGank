package com.timmy.aacgank.ui.my;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentMyBinding;
import com.timmy.aacgank.ui.person.audio.AudioStudyActivity;
import com.timmy.baselib.base.fragment.TBaseContentFragment;
import com.timmy.baselib.utils.LogUtils;

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
        binding.toolbar.setTitle(getContext().getResources().getString(R.string.app_name));
        String webDesc = "CSDN:https://blog.csdn.net/Timmy_zzh\n" + "GitHub: https://github.com/Timmy-zzh";
        binding.tvWeb.setText(webDesc);

        binding.tlTab.setupWithViewPager(binding.viewPager);
        binding.viewPager.setAdapter(new TabPagerAdapter(this.getChildFragmentManager()));
        binding.viewPager.setOffscreenPageLimit(4);

        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //设置Toolbar背景色变化
                LogUtils.d("verticalOffset:" + verticalOffset);
                binding.toolbar.setBackgroundColor(
                        changeAlpha(getResources().getColor(R.color.c_wallet_negative),
                                Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));
                binding.tvToolbar.setAlpha(Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange() * 255);
            }
        });
    }

    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    public void onViewClicked(View view) {
//        startActivity(new Intent(getContext(), CitySelectActivity.class));
//        startActivity(new Intent(getContext(), BehaviorActivity.class));
        startActivity(new Intent(getContext(), AudioStudyActivity.class));
    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {
        private String tabTitles[] = new String[]{"Android基础", "音视频开发", "Java基础", "自定义控件", "性能优化", "框架源码"};

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TechnologyPageFragment.newInstance(tabTitles[position]);
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
