package com.timmy.thirdframework.eventbus;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.timmy.baselib.base.BaseActivity;
import com.timmy.baselib.utils.LogUtils;
import com.timmy.thirdframework.eventbus.fw.Subscribe;
import com.timmy.thirdframework.eventbus.fw.ThreadMode;
import com.timmy.thirdframework.eventbus.fw.TimmyEventBus;
import com.timmy.thirdframework.R;

public class EventBusActivity extends BaseActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        button = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.tv_content);
        button.setText("界面跳转");
        TimmyEventBus.getDefault().register(this);
    }

    public void jump(View view){
        startActivity(new Intent(this,SendEventActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.PostThread)
    public void receiveEventPost(Person timmy){
        textView.setText(timmy.toString());
        LogUtils.d(" PostThread -- "+timmy.getName()+",thread:"+Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void receiveEventBack(Person timmy){
        LogUtils.d(" BackgroundThread -- "+timmy.getName()+",thread:"+Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void receiveEventMain(Person timmy){
        LogUtils.d(" MainThread -- "+timmy.getName()+",thread:"+Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimmyEventBus.getDefault().unregister(this);
    }
}
