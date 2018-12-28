package com.timmy.androidbase.motionEvent;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;

public class EventSecondActivity extends BaseActivity {

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_second);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent:" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent:" + event.getAction());
        return super.onTouchEvent(event);
    }
}
