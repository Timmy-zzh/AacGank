package com.timmy.aacgank.ui.android.uiDraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.timmy.aacgank.R;

/**
 * UI绘制流程
 */
public class UIDrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uidraw);
    }

    //UI绘制流程源码解析
    public void sourceLook(View view) {
//        view.requestLayout();
//        view.invalidate();
//        view.postInvalidate();
    }
}
