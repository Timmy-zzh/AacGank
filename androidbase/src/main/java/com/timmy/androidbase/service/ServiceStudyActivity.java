package com.timmy.androidbase.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;
import com.timmy.baselib.webview.WebViewActivity;

/**
 * Android知识体系:服务
 * 1.startService()开启服务:
 * ----a.生命周期调用方法:onCreate()->onStartCommand()
 * ----b.stopService()方法停止服务:onDestroy()
 * ----c.当前Acctivity销毁后,Service还是继续在后台运行,两侧没有交互,互不影响
 * 2.bindService()绑定服务:
 * ----a.生命周期调用方法:onCreate()->onBind()
 * ----b.调用unBindService()解邦定, onUnbind()->onDestroy()
 * ----c.当前Activity销毁后,Service也会销毁,属于同生共死
 * 3.混合模式启动Service
 */
public class ServiceStudyActivity extends BaseActivity {

    public static final String ACTION_UPLOAD_RESULT = "intetnService.uploadimg.result";
    private String TAG = this.getClass().getSimpleName();
    private BindService.DemoBinder demoBinder;
    private boolean isBinded;
    private LinearLayout llContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_study);
        Log.d(TAG, "currentThread:" + +Thread.currentThread().getId());
        llContainer = findViewById(R.id.ll_container);

        registerBroadCast();
    }


    /**
     * startService()后,当前Activity退出后,后台服务还在后台运行
     *
     * @param view
     */
    public void startService(View view) {
        Intent serviceIntent = new Intent(this, StartedService.class);
        startService(serviceIntent);
    }

    public void stopService(View view) {
        Intent serviceIntent = new Intent(this, StartedService.class);
        stopService(serviceIntent);
    }

    /***********************************************************************************
     * 通过绑定服务,拿到IBinder实例
     * 实现Activity与Service进行通信
     */
    public void bindService(View view) {
        Intent serviceIntent = new Intent(this, BindService.class);
        isBinded = bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            demoBinder = (BindService.DemoBinder) service;
            demoBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    /**
     * 解除绑定
     */
    public void unbindService(View view) {
        if (isBinded) {
            unbindService(serviceConnection);
            isBinded = false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBinded && serviceConnection != null) {
            unbindService(serviceConnection);
            serviceConnection = null;
        }

        unregisterReceiver(broadcastReceiver);
    }


    /**
     * 注册广播,接收图片上传后的结果信息
     */
    private void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPLOAD_RESULT);
        registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == ACTION_UPLOAD_RESULT) {
                String path = intent.getStringExtra(UploadImgIntentService.EXTRA_IMG_PATH);

                TextView tv = llContainer.findViewWithTag(path);
                tv.setText(path + " upload success ~~~");
            }
        }
    };

    int i = 0;

    /**
     * IntentService使用
     * 源码解析
     */
    public void intentServiceUse(View view) {
        //模拟路径
        String path = "/sdcard/imgs/" + (++i) + ".png";
        UploadImgIntentService.startUploadImg(this, path);

        TextView tv = new TextView(this);
        llContainer.addView(tv);
        tv.setText(path + " is uploading ...");
        tv.setTag(path);
    }

    public void sourceLook(View view) {
        WebViewActivity.startAction(this,
                "https://blog.csdn.net/pihailailou/article/details/78570067");
    }
}
