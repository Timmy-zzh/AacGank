package com.timmy.androidbase.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.timmy.androidbase.R;


public class DemoService extends Service {

    private String TAG = this.getClass().getSimpleName();

    public DemoService() {
        Log.d(TAG, "DemoService");
    }

    private DemoBinder binder = new DemoBinder();

    class DemoBinder extends Binder {

        public void startDownload() {
            Log.d(TAG, "开始下载--耗时操作");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "线程操作");
                }
            }).start();

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        Log.d(TAG, "currentThread:" + +Thread.currentThread().getId());
        //创建前台服务
        Intent notificationIntent = new Intent(this, ServiceStudyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_dots)
                .setContentText("ContentText")
                .setContentIntent(pendingIntent)
                .setContentTitle("ContentTitle")
                .build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
