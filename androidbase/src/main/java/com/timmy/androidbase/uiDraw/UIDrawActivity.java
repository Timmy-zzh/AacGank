package com.timmy.androidbase.uiDraw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;


/**
 * UI绘制流程
 */
public class UIDrawActivity extends BaseActivity {

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
