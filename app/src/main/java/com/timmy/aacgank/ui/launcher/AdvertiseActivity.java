package com.timmy.aacgank.ui.launcher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.MainActivity;
import com.timmy.aacgank.ui.login.GestureLoginActivity;
import com.timmy.aacgank.ui.login.LoginActivity;
import com.timmy.aacgank.util.StatusBarUtils;
import com.timmy.baselib.utils.SpHelper;

/**
 * 广告页面
 * 三秒倒计时:Handler
 * SP判断登陆态：界面跳转
 *
 * todo 注意Handler内存泄漏问题
 */
public class AdvertiseActivity extends AppCompatActivity {

    private int second = 3;
    private TextView tvTime;

    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            second--;
            tvTime.setText(second + "s");
            if (second <= 0) {
                click(null);
            } else {
                mHandler.postDelayed(runnable, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);
        StatusBarUtils.setTranspant(this);
        tvTime = findViewById(R.id.tv_time);
        tvTime.setText(second + "s");
        mHandler.postDelayed(runnable, 1000);
    }

    public void click(View view) {
        clean();
        Intent intent;
        if ((boolean) SpHelper.get("isLogin", false)) {   //已登陆
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clean();
    }

    private void clean() {
        if (mHandler != null) {
            mHandler.removeCallbacks(runnable);
            runnable = null;
            mHandler = null;
        }
    }
}
