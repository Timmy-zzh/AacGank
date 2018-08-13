package com.timmy.aacgank.ui.android.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 内容提供者
 */
public class MyContentProvider extends ContentProvider {

    private String TAG = this.getClass().getSimpleName();
    //设置ContentProvider的唯一标示
    public static final String Autohority = "com.timmy.myprovider";

    public static final int User_Code = 1;

    private static UriMatcher mUriMatcher;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //若URI资源路径为:content://com.timmy.myprovider/user  则返回注册码User_Code
        mUriMatcher.addURI(Autohority, "user", User_Code);
    }

    private SQLiteDatabase database;
    private Context context;

    /**
     * 初始化ContentProvider,
     * 1.初始化数据库
     */
    @Override
    public boolean onCreate() {
        context = getContext();
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();

        //先清空user表
        database.execSQL("delete from user");
        //加入一条记录
        database.execSQL("insert into user values(1,'Timmy');");
        database.execSQL("insert into user values(2,'Divid');");

        return true;
    }

    /**
     * 查询
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        Cursor query = database.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
        return query;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * 添加数据
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "uri:" + uri.getPath());
        //根据Uri,判断响应的表明
        String tableName = getTableName(uri);
        database.insert(tableName, null, values);

        //数据发生变化,通知观察者
        context.getContentResolver().notifyChange(uri, null);

        return uri;
    }

    /**
     * 删除数据
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 更新数据
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    private String getTableName(Uri uri) {
        if (mUriMatcher.match(uri) == User_Code) {
            return DBHelper.User_Table_Name;
        }
        return "";
    }
}
