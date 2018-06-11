package com.timmy.aacgank.ui.gank.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;

/**
 * 高仿淘宝商品详情页实现界面
 */
public class AndroidDetailActivity extends AppCompatActivity {

    private Gank gank;

    public static void startAction(Context context, Gank gank) {
        Intent intent = new Intent(context, AndroidDetailActivity.class);
        intent.putExtra("data", gank);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_detail);
        gank = (Gank) getIntent().getSerializableExtra("data");


    }
}
