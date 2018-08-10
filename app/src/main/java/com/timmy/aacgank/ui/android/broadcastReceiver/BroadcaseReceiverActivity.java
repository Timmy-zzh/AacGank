package com.timmy.aacgank.ui.android.broadcastReceiver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.aacgank.R;

public class BroadcaseReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcase_receiver);

//


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getActionBar()!= null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
