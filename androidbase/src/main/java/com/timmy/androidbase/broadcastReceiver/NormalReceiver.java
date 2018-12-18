package com.timmy.androidbase.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.timmy.baselib.C;
import com.timmy.baselib.utils.ToastUtils;

public class NormalReceiver extends BroadcastReceiver {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String msg = intent.getStringExtra(C.Params);
        ToastUtils.showShort(TAG + ", msg:" + msg);
        Log.d(TAG, "action:" + action + ", msg:" + msg);
    }
}
