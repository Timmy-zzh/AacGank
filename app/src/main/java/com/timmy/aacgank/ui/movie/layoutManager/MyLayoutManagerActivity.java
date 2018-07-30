package com.timmy.aacgank.ui.movie.layoutManager;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timmy.aacgank.R;

import java.util.Arrays;

public class MyLayoutManagerActivity extends AppCompatActivity {

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
        recyclerView.setLayoutManager(new MyLayoutManager());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new SimpleAdapter(this, Arrays.asList(datas)));
    }
}
