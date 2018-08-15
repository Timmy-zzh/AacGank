package com.timmy.aacgank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ActivityMainBinding;
import com.timmy.aacgank.ui.home.HomeFragment;
import com.timmy.aacgank.ui.movie.MovieFragment;
import com.timmy.aacgank.ui.my.MyFragment;
import com.timmy.aacgank.ui.home.TextFrgment;
import com.timmy.aacgank.ui.multimedia.ui.VideoFragment;
import com.timmy.baselib.base.activity.TBaseBindingActivity;
import com.timmy.baselib.statusbar.StatusBarUtil;

/**
 * 项目结构:
 * Tab1:Gank
 * Tab2:音视频
 * Tab3:豆瓣  电影,图书,
 * Tab4:音乐
 * Tab5:个人中心--技术总结
 */
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
        if (mFragmensts[0] == null && fragment instanceof HomeFragment) {
            mFragmensts[0] = fragment;
        }
        if (mFragmensts[1] == null && fragment instanceof VideoFragment) {
            mFragmensts[1] = fragment;
        }
        if (mFragmensts[2] == null && fragment instanceof MovieFragment) {
            mFragmensts[2] = fragment;
        }
        if (mFragmensts[4] == null && fragment instanceof MyFragment) {
            mFragmensts[4] = fragment;
        }
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

        /**
         * 给你两个从小到大已排序好的数组A和B，
         * 例如A: [3,7,8,13,16,18, 27]
         * B: [2,5,6,7,9,12,12,13,15,18,23,31]
         * 请你编写一段代码，输出数组A和B中出现的重复元素。对于上面这个例子，
         * 应该输出[7,12,13,18] 编写代码时，请考虑执行效率，尽可能以最优的方式（比较次数最少）去实现
         */


        Integer[] array1 = {3,7,8,13,16,18, 27};
        Integer[] array2 = {2,5,6,7,9,12,12,13,15,18,23,31};

        int i=0,j=0;
        find(i,array1, j,array2);
    }

    /**
     * 因为是已经排好序的，所有可边比较大小，边进行数据位置移动
     * 采用递归方式
     * 方式二：
     * 建立一个排序好的并集数组，和临时数组
     * 通过遍历并集数组，找到相邻相等的元素
     */
    private void find(int i,Integer[] array1, int j,Integer[] array2) {
        if (i==array1.length || j==array2.length){//去除边界异常
            return;
        }
        if (array1[i] > array2[j]){
            find(i,array1,j+1,array2);
        }else if (array1[i] < array2[j]){
            find(i+1,array1,j,array2);
        }else{
            Log.d("Timmy","相同元素："+array1[i]);
            find(i+1,array1,j+1,array2);
        }
    }

    private void initView() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        //为底部导航栏设置展示数据
        //设置展示模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        //设置背景样式
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Gank").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Video").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Douban").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.pink))
                .addItem(new BottomNavigationItem(R.drawable.ic_github_circle_white_24dp, "My").setActiveColorResource(R.color.c_wallet_negative))
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
//                StatusBarUtil.setColor(this,getResources().getColor(R.color.orange));
                break;
            case 1:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = VideoFragment.newInstance();
                }
//                StatusBarUtil.setColor(this,getResources().getColor(R.color.teal));
                break;
            case 2:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = MovieFragment.newInstance();
                }
//                StatusBarUtil.setColor(this,getResources().getColor(R.color.blue));
                break;
            case 3:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = TextFrgment.newInstance();
                }
//                StatusBarUtil.setColor(this,getResources().getColor(R.color.pink));
                break;
            case 4:
                if (mFragmensts[position] == null) {
                    mFragmensts[position] = MyFragment.newInstance();
                }
//                StatusBarUtil.setColor(this,getResources().getColor(R.color.c_wallet_negative),0);
//                StatusBarUtil.setTranslucent(this);
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
//        trx.commit();
        trx.commitAllowingStateLoss();
    }

}
