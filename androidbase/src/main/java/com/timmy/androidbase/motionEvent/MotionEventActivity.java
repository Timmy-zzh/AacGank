package com.timmy.androidbase.motionEvent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;

/**
 * 事件分发:
 * Activity->ViewGroup->View
 */
public class MotionEventActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);
    }

    public void eventCode(View view) {
        startActivity(new Intent(this, EventSecondActivity.class));
    }
}
