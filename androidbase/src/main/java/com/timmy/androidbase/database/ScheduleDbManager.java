package com.timmy.androidbase.database;

import android.database.sqlite.SQLiteDatabase;

import com.timmy.baselib.App;

public class ScheduleDbManager {

    private static ScheduleDbManager instance;
    private final ScheduleDbHelper dbHelper;

    private ScheduleDbManager(){
        dbHelper = new ScheduleDbHelper(App.getContext());
    }

    public static ScheduleDbManager getInstance(){
        if (instance == null){
            synchronized (ScheduleDbManager.class){
                if (instance == null){
                    instance = new ScheduleDbManager();
                }
            }
        }
        return instance;
    }

    public SQLiteDatabase getDatabase(){
        return dbHelper.getWritableDatabase();
    }

}
