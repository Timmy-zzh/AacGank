package com.timmy.androidbase.recyclveriew.layoutmanager;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;
import com.timmy.baselib.simple.SimpleAdapter;

import java.util.Arrays;

/**
 * 自定义LayoutManager
 *
 */
public class MyLayoutManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        initView();
    }

    private void initView() {
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        String[] datas = getResources().getStringArray(R.array.date);
//        recyclerView.setLayoutManager(new MyLayoutManager());
        recyclerView.setLayoutManager(new FlowayoutManager());
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new SimpleAdapter(this, Arrays.asList(datas)));
    }
}
