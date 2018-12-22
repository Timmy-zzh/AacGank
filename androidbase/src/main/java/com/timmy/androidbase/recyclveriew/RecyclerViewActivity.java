package com.timmy.androidbase.recyclveriew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.timmy.androidbase.R;
import com.timmy.androidbase.recyclveriew.itemdecoration.ItemDecorationActivity;
import com.timmy.androidbase.recyclveriew.layoutmanager.MyLayoutManagerActivity;
import com.timmy.baselib.base.BaseActivity;

/**
 * @author zhuzhonghua
 * RecyclerView使用
 */
public class RecyclerViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
    }

    /**
     * 添加分割线
     */
    public void addItemDecoration(View view) {
        startActivity(new Intent(this, ItemDecorationActivity.class));
    }

    public void simpleLayoutManager(View view) {
        startActivity(new Intent(this, MyLayoutManagerActivity.class));
    }
}
