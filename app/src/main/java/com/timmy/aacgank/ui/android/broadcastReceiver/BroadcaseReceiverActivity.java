package com.timmy.aacgank.ui.android.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.timmy.aacgank.C;
import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.other.WebViewActivity;

public class BroadcaseReceiverActivity extends AppCompatActivity {

    private static final String BROADCAST_NORMAL = "com.timmy.broadcast.action.normal";
    private static final String BROADCAST_ORDER = "com.timmy.broadcast.action.order";
    private static final String DYNAMIC_BROADCAST = "com.timmy.broadcast.static";
    private static final String LOCAL_BROADCAST = "com.timmy.broadcast.local";
    private static final String PERMISSION_BROADCAST = "com.timmy.broadcast.timmy";
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcase_receiver);
        registerDynamicReceiver();
        registerLocalReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /*************************************************************************
     * 静态注册:标准广播
     * @param view
     */
    public void sendBroadNormal(View view) {
        Intent intent = new Intent(BROADCAST_NORMAL);
        intent.putExtra(C.Params, "Normal Broadcast 111");
        sendBroadcast(intent);
    }


    /**
     * 有序广播
     * 清单文件中设置android:priority控制广播接收优先级
     */
    public void sendBroadOrder(View view) {
        Intent intent = new Intent(BROADCAST_ORDER);
        intent.putExtra(C.Params, "Order Broadcast 111");
        sendOrderedBroadcast(intent, null);
    }

    /*************************************************************************
     * 动态注册广播
     * @param view
     */
    public void registerBroad(View view) {
        sendBroadcast(new Intent(DYNAMIC_BROADCAST));
    }

    //注册动态广播
    private void registerDynamicReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DYNAMIC_BROADCAST);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "ACTION:" + intent.getAction());
            }
        };

        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    /*************************************************************************
     * 本地广播
     * @param view
     */
    public void localBroadcast(View view) {
        Intent intent = new Intent(LOCAL_BROADCAST);
        intent.putExtra(C.Params, "local Broad");
        localBroadcastManager.sendBroadcast(intent);
    }

    //注册静态广播
    private void registerLocalReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localReceiver = new LocalReceiver();
        IntentFilter filter = new IntentFilter(LOCAL_BROADCAST);
        localBroadcastManager.registerReceiver(localReceiver, filter);
    }

    /**
     * 私有权限广播
     *
     * @param view
     */
    public void permissionBroadcast(View view) {
        sendBroadcast(new Intent("Tim"), PERMISSION_BROADCAST);
    }

    public void sourceLook(View view) {
        WebViewActivity.startAction(this, "https://blog.csdn.net/pihailailou/article/details/78640128");
    }
}
