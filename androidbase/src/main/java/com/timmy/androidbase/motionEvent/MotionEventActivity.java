package com.timmy.androidbase.motionEvent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;

/**
 * 事件分发:
 * Activity->ViewGroup->View
 */
public class MotionEventActivity extends BaseActivity {

    private String TAG = this.getClass().getSimpleName();

    public static final int ACTION_DOWN = 0;
    public static final int ACTION_UP = 1;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_CANCEL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);
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
