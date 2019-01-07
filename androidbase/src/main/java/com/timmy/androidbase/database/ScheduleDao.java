package com.timmy.androidbase.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.timmy.baselib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库耗时操作，需放在子线程进行
 */
public class ScheduleDao {

    /**
     * 1.先添加到系统日历中
     * 2。加入数据库
     */
    public static void add(Schedule schedule) {
        LogUtils.d(schedule.toString());
        SQLiteDatabase database = ScheduleDbManager.getInstance().getDatabase();

        ContentValues values = new ContentValues();
        values.put("title", schedule.title);
        values.put("description", schedule.description);
        values.put("startTime", schedule.startTime);
        values.put("endTime", schedule.endTime);
        values.put("allDay", schedule.allDay);
        values.put("remindAheadTime", schedule.remindAheadTime);

        database.insert(ScheduleDbHelper.NAME_TABLE_SCHEDULE, null, values);
//    database.execSQL();
    }

    public static List<Schedule> findSchedules(int page, int size) {
        List<Schedule> resultList = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase database = ScheduleDbManager.getInstance().getDatabase();

            String sql = "select * from " + ScheduleDbHelper.NAME_TABLE_SCHEDULE + " ";
            cursor = database.rawQuery(sql, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Schedule schedule = new Schedule();
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    long startTime = cursor.getLong(cursor.getColumnIndex("startTime"));
                    long endTime = cursor.getLong(cursor.getColumnIndex("endTime"));
                    int allDay = cursor.getInt(cursor.getColumnIndex("allDay"));
                    long remindAheadTime = cursor.getLong(cursor.getColumnIndex("remindAheadTime"));

                    schedule.title = title;
                    schedule.description = description;
                    schedule.allDay = allDay;
                    schedule.remindAheadTime = remindAheadTime;
                    schedule.startTime = startTime;
                    schedule.endTime = endTime;

                    resultList.add(schedule);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d(e.toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return resultList;
    }
}
