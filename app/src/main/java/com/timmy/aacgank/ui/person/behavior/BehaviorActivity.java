package com.timmy.aacgank.ui.person.behavior;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.simple.SimpleAdapter;
import com.timmy.aacgank.ui.simple.SimplePagerAdapter;

import java.util.Arrays;

/**
 * Bedavior深入学习
 * behavior3:完成UC浏览器主页滑动效果
 * 头部控件作为被依赖对象
 * 1.内容控件在头部控件下方
 * ----内容控件依赖于头部控件,监听头部变化,改变内容部分控件的位置
 * 2.当内容控件滑动时
 * ----头部控件需要进行偏移,进行事件消费
 * 3.实现头部控件的滑动:仿照AppbarLayout写法
 * ----NestedLinearLayout实现NestedScrollingChild接口,并进行事件传递
 * ----头部Behavior处理
 * 4.滑动视差处理:定义好Header部分需要向上滑动的距离
 * 5.手指放开后的事件处理
 * ----fling事件
 */
public class BehaviorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_behavior);
//        setContentView(R.layout.activity_behavior2);
        setContentView(R.layout.activity_behavior3);
        initView();
    }

    private void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        String[] datas = getResources().getStringArray(R.array.date);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new SimpleAdapter(this, Arrays.asList(datas)));
    }
}
