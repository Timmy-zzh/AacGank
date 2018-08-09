package com.timmy.aacgank.ui.person.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.timmy.aacgank.R;

/**
 * Android知识体系:服务
 * <video class="player" src="https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200fc10000bdi0gtbrh3p0ag4hk3eg&amp;line=0"
 * poster="https://p1.pstatp.com/large/a18d00134bdf2e51c0eb.jpg" type="video/mp4" controls="controls" style="width: 100%;"></video>
 */
public class ServiceStudyActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private DemoService.DemoBinder demoBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_study);
        Log.d(TAG,"currentThread:"+ + Thread.currentThread().getId());
    }

    /**
     * startService()后,当前Activity退出后,后台服务还在后台运行
     * @param view
     */
    public void startService(View view) {
        Intent  serviceIntent = new Intent(this, DemoService.class);
        startService(serviceIntent);
    }

    public void stopService(View view) {
        Intent serviceIntent = new Intent(this, DemoService.class);
        stopService(serviceIntent);
    }

    /***********************************************************************************
     * 通过绑定服务,拿到IBinder实例
     * 实现Activity与Service进行通信
     */
    public void bindService(View view) {
        Intent  serviceIntent = new Intent(this, DemoService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            demoBinder = (DemoService.DemoBinder) service;
            demoBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    public void unbindService(View view) {
        unbindService(serviceConnection);
    }

    /*******************************************************************************************
     * AIDL启动远程服务:实现IPC
     *
     */
    public void bindRemoteService(View view) {

    }

    public void unbindRemoteService(View view) {

    }
}
