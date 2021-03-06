package com.timmy.aacgank.ui.launcher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.timmy.aacgank.R;
import com.timmy.aacgank.util.StatusBarUtils;

/**
 * 启动页面
 * 白屏处理
 * 设置主题windowBackground -- 图层layer
 */
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startActivity(new Intent(LauncherActivity.this, AdvertiseActivity.class));
//        finish();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LauncherActivity.this, AdvertiseActivity.class));
                finish();
            }
        }, 1000);
    }
}

