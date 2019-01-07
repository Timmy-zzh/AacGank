package com.timmy.androidbase.database;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;

import java.io.File;

public class DatabaseActivity extends BaseActivity {

    private String TAG = this.getClass().getSimpleName();
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        tvContent = findViewById(R.id.tv_content);
    }

    public void 创建本地日程(View view) {
        startActivity(new Intent(this, ScheduleManagerActivity.class));
    }

    /**
     * Android环境各种路径:
     * 分为:
     * 1.内部存储
     * 2.外部存储
     */
    public void androidPath(View view) {
        StringBuilder sb = new StringBuilder();
        String rootDirPath = Environment.getRootDirectory().getAbsolutePath();
        sb.append("内部存储: \n");
        sb.append("Environment.getRootDirectory(): " + rootDirPath);
        Log.d(TAG, "Environment.getRootDirectory():" + rootDirPath);

        sb.append("\nEnvironment.getDataDirectory(): " + Environment.getDataDirectory().getAbsolutePath());
        sb.append("\n  /data 目录下,还有私密文件: database / shared_prefs");
        sb.append("\nEnvironment.getDownloadCacheDirectory(): " + Environment.getDownloadCacheDirectory().getAbsolutePath());

        sb.append("\n\n外部存储: ");
        sb.append("\nEnvironment.getExternalStorageDirectory():  " + Environment.getExternalStorageDirectory().getAbsolutePath());

        sb.append("\n   九大公有存储目录: ");
        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
//        sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath());

        sb.append("\n   私有目录:包目录下 ");
        sb.append("\nContext.getExternalCacheDir(null): \n" + this.getExternalFilesDir(null).getAbsolutePath());
        File file = new File(this.getExternalFilesDir(null), "text.txt");
        sb.append("\nnew File(this.getExternalFilesDir(null),\"text.txt\"):  \n" + file.getAbsolutePath());
        sb.append("\nContext.getExternalCacheDir(): \n" + this.getExternalCacheDir().getAbsolutePath());

        tvContent.setText(sb.toString());
    }
}
