package com.timmy.aacgank.ui.android.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.timmy.aacgank.C;

public class OrderReceiver3 extends BroadcastReceiver {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String msg = getResultExtras(true).getString(C.Params2);
        Log.d(TAG, "action:" + action + ", msg:" + msg);
    }
}
