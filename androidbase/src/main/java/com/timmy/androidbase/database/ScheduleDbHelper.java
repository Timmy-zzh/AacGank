package com.timmy.androidbase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite数据库辅助类:
 * 包括:1.据库名称
 * 2.多张数据库表
 */
public class ScheduleDbHelper extends SQLiteOpenHelper {

    //数据库名
    private static final String NAME_DB = "timmy_schedule.db";
    //数据库表名
    public static final String NAME_TABLE_SCHEDULE = "schedule_list";
    //数据库表索引名
    private static final String NAME_INDEX_SCHEDULE = "schedule_index";
    //数据库版本号
    private static final int VERDION_DB = 1;
    /**
     * 创建数据库表
     * char 定长字符串
     * varchar非定长字符串
     */
    private static String SQL_CREATE_TABLE_SCHEDULE =
            "create table if not exists " + NAME_TABLE_SCHEDULE +
                    "(_id integer primary key autoincrement, " +
                    "scheduleId char ," +  //
                    "createTime char ," +  //日程创建事件 yyyy-MM-dd HH:mm:ss
                    "title varchar ," +
                    "description varchar ," +
                    "startTime char ," +
                    "endTime char ," +
                    "allDay integer ," +
                    "remindAheadTime char ," +
                    "eventId char ," +
                    "remindId char" +
                    ")";

    /**
     * 创建数据库表索引
     * 唯一索引
     */
    private static String SQL_CREATE_INDEX_SCHEDULE =
            "create unique index " + NAME_INDEX_SCHEDULE +
                    " on " + NAME_TABLE_SCHEDULE +
                    " (scheduleId)";

    public ScheduleDbHelper(Context context) {
        super(context, NAME_DB, null, VERDION_DB);
    }

    /**
     * 创建数据库的时候会调用该方法
     * 在该方法中创建数据库表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建User数据库表,包含两个字段 id与name
        db.execSQL(SQL_CREATE_TABLE_SCHEDULE);
        db.execSQL(SQL_CREATE_INDEX_SCHEDULE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
