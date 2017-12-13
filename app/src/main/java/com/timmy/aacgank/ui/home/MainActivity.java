package com.timmy.aacgank.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ActivityMainBinding;
import com.timmy.aacgank.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar bottomNavigationBar;
    private int lastSelectedPosition;
    private Fragment[] fragmensts = new Fragment[4];
    private FragmentManager fragmentManager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "福利").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Android").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "我的").setActiveColorResource(R.color.blue))
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

    }

    private void setTabItemSelected(int position) {
        switch (position) {
            case 0:
                if (fragmensts[position] == null) {
                    fragmensts[position] = HomeFragment.newInstance();
                }
                break;
            case 1:
                if (fragmensts[position] == null) {
                    fragmensts[position] = TextFrgment.newInstance();
                }
                break;
            case 2:
                if (fragmensts[position] == null) {
                    fragmensts[position] = MyFragment.newInstance();
                }
                break;
            case 3:
                if (fragmensts[position] == null) {
                    fragmensts[position] = TextFrgment.newInstance();
                }
                break;
        }
        FragmentTransaction trx = fragmentManager.beginTransaction();
        if (!fragmensts[position].isAdded()) {
            trx.add(R.id.frame_layout, fragmensts[position]);
        }
        for (int i = 0, size = fragmensts.length; i < size; i++) {
            if (fragmensts[i] != null) {
                if (i == position) {
                    trx.show(fragmensts[i]);
                } else {
                    trx.hide(fragmensts[i]);
                }
            }
        }
        trx.commitAllowingStateLoss();
    }

}
