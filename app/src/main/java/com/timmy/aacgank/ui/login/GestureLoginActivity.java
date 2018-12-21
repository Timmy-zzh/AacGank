package com.timmy.aacgank.ui.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.MainActivity;
import com.timmy.baselib.utils.SpHelper;
import com.timmy.customeview.gesturelock.GestureLockLayout;

/**
 * 手势密码登陆
 */
public class GestureLoginActivity extends AppCompatActivity {

    private GestureLockLayout gestureLockLayout;
    private String mCurrNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_login);
        initView();
    }

    private void initView() {
        gestureLockLayout = findViewById(R.id.gesture_lock_layout);

        gestureLockLayout.setGestureCallback(new GestureLockLayout.GestureCallback() {
            @Override
            public void onGestureFinished(@NonNull String numbers) {
                if (numbers.length() < 6) {
                    Toast.makeText(GestureLoginActivity.this, "至少连接6个点", Toast.LENGTH_SHORT).show();
                    gestureLockLayout.cleanDrawLineState(0);
                    return;
                }
                if (mCurrNumber == null || mCurrNumber.isEmpty()) {
                    mCurrNumber = numbers;
                    Toast.makeText(GestureLoginActivity.this, "请再次绘制", Toast.LENGTH_SHORT).show();
                    gestureLockLayout.cleanDrawLineState(0);
                    return;
                }
                if (!mCurrNumber.equalsIgnoreCase(numbers)) {
                    mCurrNumber = "";
                    Toast.makeText(GestureLoginActivity.this, "两次绘制不一致，请重新绘制", Toast.LENGTH_SHORT).show();
                    gestureLockLayout.cleanDrawLineState(1500, true);
                    return;
                }
                mCurrNumber = "";
                gestureLockLayout.cleanDrawLineState(1500);
                Toast.makeText(GestureLoginActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();

                SpHelper.put("isLogin", true);
                startActivity(new Intent(GestureLoginActivity.this, MainActivity.class));
                GestureLoginActivity.this.finish();
            }
        });
    }
}
