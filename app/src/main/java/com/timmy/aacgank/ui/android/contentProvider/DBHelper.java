package com.timmy.aacgank.ui.android.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite数据库辅助类:
 * 包括:1.据库名称
 * 2.多张数据库表
 */
public class DBHelper extends SQLiteOpenHelper {

    //数据库名
    private static final String DataBase_Name = "timmydb.db";
    //数据库表名
    public static final String User_Table_Name = "user";
    //数据库版本号
    private static final int DataBase_Version = 1;

    public DBHelper(Context context) {
        super(context, DataBase_Name, null, DataBase_Version);
    }

    /**
     * 创建数据库的时候会调用该方法
     * 在该方法中创建数据库表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建User数据库表,包含两个字段 id与name
        db.execSQL("create table if not exists " + User_Table_Name + "(_id integer primary key autoincrement,name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
