package com.timmy.thirdframework.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.timmy.baselib.base.BaseActivity;
import com.timmy.thirdframework.eventbus.core.TimmyEventBus;
import com.timmy.thirdframework.R;

public class SendEventActivity extends BaseActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        button = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.tv_content);
        button.setText("post发送事件");
    }

    public void jump(View view){
        TimmyEventBus.getDefault().post(new Person("timmy",26));
    }
}
