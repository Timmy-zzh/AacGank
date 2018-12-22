package com.timmy.baselib.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.timmy.baselib.R;
import com.timmy.baselib.statusbar.StatusBarUtil;

public class BaseActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base_new);
//    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_new);
        FrameLayout flContent = findViewById(R.id.fl_content);
        LayoutInflater.from(this).inflate(layoutResID, flContent, true);
        setStatusBar();
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        initToolbar(toolbar, toolbarTitle);
    }

    protected void initToolbar(Toolbar toolbar, TextView toolbarTitle) {
        toolbarTitle.setText(getTitle());
//        toolbar.setBackgroundColor(getResources().getColor(R.color.white));//设置Toolbar背景色
//        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
