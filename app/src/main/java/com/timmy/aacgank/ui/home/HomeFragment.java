package com.timmy.aacgank.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentGankBinding;
import com.timmy.aacgank.ui.gank.adapter.GankPagerAdapter;
import com.timmy.baselib.base.fragment.DjBaseContentFragment;
import com.timmy.baselib.utils.LogUtils;

/**
 * 使用正常方式进行处理
 * 1.获取数据,使用retrofit
 * 2.展示recyclerview
 */
public class HomeFragment extends DjBaseContentFragment<FragmentGankBinding> {

    private static String[] gankTypes = {"福利", "Android", "iOS", "休息视频", "拓展资源", "前端", "all"};

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.d("onAttach ");
    }

    @Override
    protected int getLayoutRes() {
        LogUtils.d("onCreateView ");
        return R.layout.fragment_gank;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate ");
    }

    @Override
    protected void initBase() {
        super.initBase();
        LogUtils.d("onViewCreated ");
        initView();
    }

    private void initView() {
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可以滑动
        for (String gankType : gankTypes) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(gankType));
        }
        //TabLayout与ViewPager进行滑动关联
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.viewPager));
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.viewPager.setAdapter(new GankPagerAdapter(getActivity().getSupportFragmentManager(),gankTypes));
    }

}