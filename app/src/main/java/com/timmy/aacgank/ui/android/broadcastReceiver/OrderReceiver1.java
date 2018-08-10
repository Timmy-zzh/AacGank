package com.timmy.aacgank.ui.android.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.timmy.aacgank.C;
import com.timmy.baselib.utils.ToastUtils;

public class OrderReceiver1 extends BroadcastReceiver {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String msg = intent.getStringExtra(C.Params);
        Log.d(TAG, "action:" + action + ", msg:" + msg);

        //向下一级广播接收者写入数据
        Bundle bundle = new Bundle();
        bundle.putString(C.Params1, "OrderReceiver1  set data ");
        setResultExtras(bundle);
    }
}
