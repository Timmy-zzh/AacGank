package com.timmy.androidbase.recyclveriew.itemdecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;
import com.timmy.baselib.simple.SimpleAdapter;

import java.util.Arrays;

/**
 * @author zhuzhonghua
 */
public class ItemDecorationActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recycler_view);

        recyclerView = findViewById(R.id.recycler_view);
        String[] stringArray = getResources().getStringArray(R.array.dateArr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new LinearItemDecoration(0, 20, false, false, LinearItemDecoration.VERTICAL));
        recyclerView.setAdapter(new SimpleAdapter(this, Arrays.asList(stringArray)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_linear) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            return true;
        } else if (itemId == R.id.menu_grid) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            return true;
        } else if (itemId == R.id.menu_stagger) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}