package com.timmy.aacgank.ui.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

//    @BindView(R.id.frame_layout)
//    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private int lastSelectedPosition;
    private Fragment[] fragmensts = new Fragment[4];

    @Nullable
    TextBadgeItem numberBadgeItem;

    @Nullable
    ShapeBadgeItem shapeBadgeItem;
    private FragmentManager fragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        fragmentManager = getSupportFragmentManager();
        initView();
    }

    private void initView() {
        //为底部导航栏设置展示数据
        //设置展示模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        //设置背景样式
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);

        //tab 数字标示
        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(false);

        shapeBadgeItem = new ShapeBadgeItem()
                .setShape(ShapeBadgeItem.SHAPE_OVAL)
                .setShapeColorResource(R.color.teal)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(true);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
//                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.selectTab(0);
    }


    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        if (numberBadgeItem != null) {
            numberBadgeItem.setText(Integer.toString(position));
        }
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
