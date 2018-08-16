package com.timmy.aacgank.ui.android.recyclveriew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.android.recyclveriew.itemdecoration.ItemDecorationActivity;

/**
 * @author zhuzhonghua
 * RecyclerView使用
 */
public class RecyclerViewActivity extends AppCompatActivity {

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
}
