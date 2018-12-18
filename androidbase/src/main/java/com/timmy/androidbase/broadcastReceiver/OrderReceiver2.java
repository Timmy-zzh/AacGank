package com.timmy.androidbase.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.timmy.baselib.C;

public class OrderReceiver2 extends BroadcastReceiver {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // 取出上一级广播传递过来的参数
        String msg =getResultExtras(true).getString(C.Params1);
        Log.d(TAG, "action:" + action + ", msg:" + msg);

//        Bundle bundle = new Bundle();
//        bundle.putString(C.Params2, "OrderReceiver2  set data ");
//        setResultExtras(bundle);

        //截断广播
        abortBroadcast();
    }
}
