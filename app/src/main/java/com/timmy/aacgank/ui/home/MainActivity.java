package com.timmy.aacgank.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ActivityMainBinding;
import com.timmy.baselib.activity.TBaseBindingActivity;

public class MainActivity extends TBaseBindingActivity<ActivityMainBinding> implements BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar bottomNavigationBar;
    private int lastSelectedPosition;
    private Fragment[] mFragmensts = new Fragment[5];
    private FragmentManager fragmentManager;

    @Override
    protected void onRefresh() {

    }
    /**
     * 处理Fragment界面重叠问题
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
//        if (mFragmensts[0] == null && fragment instanceof HomeFragment) {
//            mFragmensts[0] = fragment;
//        }
//        if (mFragmensts[1] == null && fragment instanceof ShoppingFragment) {
//            mFragmensts[1] = fragment;
//        }
//        if (mFragmensts[2] == null && fragment instanceof MyFragment) {
//            mFragmensts[2] = fragment;
//        }
        /*if (mFragmensts[2] == null && fragment instanceof CommunityFragment) {
            mFragmensts[2] = fragment;
        }
        if (mFragmensts[3] == null && fragment instanceof MyFragment) {
            mFragmensts[3] = fragment;
        }*/
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToolbar(false);
        showContentLayout();
        bottomNavigationBar = binding.bottomNavigationBar;
        fragmentManager = getSupportFragmentManager();
        initView();
    }

    private void initView() {
        //为底部导航栏设置展示数据
        //设置展示模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        //设置背景样式
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Gank").setActiveColorResource(R.color.orange))//简书首页
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp , "Music").setActiveColorResource(R.color.teal))//网易云音乐
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Movie").setActiveColorResource(R.color.blue))//腾讯视频-视屏播放
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Book").setActiveColorResource(R.color.blue))//书籍 GitHub
                .addItem(new BottomNavigationItem(R.drawable.ic_github_circle_white_24dp, "My").setActiveColorResource(R.color.blue))//个人中心
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.selectTab(0);
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        setTabItemSelected(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        //tab重新点击
    }

    private void setTabItemSelected(int position) {
        switch (position) {
            case 0:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = HomeFragment.newInstance();
                }
                break;
            case 1:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = TextFrgment.newInstance();
                }
                break;
            case 2:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = MyFragment.newInstance();
                }
                break;
            case 3:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = TextFrgment.newInstance();
                }
                break;
            case 4:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = TextFrgment.newInstance();
                }
                break;
        }
        FragmentTransaction trx = fragmentManager.beginTransaction();
        if (!mFragmensts[position].isAdded()) {
            trx.add(R.id.frame_layout, mFragmensts[position]);
        }
        for (int i = 0, size = mFragmensts.length; i < size; i++) {
            if (mFragmensts[i] != null) {
                if (i == position) {
                    trx.show(mFragmensts[i]);
                } else {
                    trx.hide(mFragmensts[i]);
                }
            }
        }
        trx.commitAllowingStateLoss();
    }

}
