package com.timmy.androidbase.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.timmy.androidbase.R;
import com.timmy.androidbase.calendar.Schedule;
import com.timmy.baselib.base.BaseActivity;
import com.timmy.baselib.simple.SimpleAdapter;
import com.timmy.baselib.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 本地日程管理界面
 * 本地日历操作
 * SQLite数据库
 */
public class ScheduleManagerActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_manager);
        initView();
        getSchedulesFromDb();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 子线程中获取数据库数据
     */
    private void getSchedulesFromDb() {
        List<Schedule> scheduleList = ScheduleDao.findSchedules(1, 10);
        LogUtils.d(scheduleList.toString());
        recyclerView.setAdapter(new ScheduleAdapter(this, scheduleList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_schedule_add) {
            startActivity(new Intent(this, ScheduleAddOrEditActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
