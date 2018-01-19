package com.timmy.aacgank;

import android.app.Application;

import com.timmy.baselib.utils.LogUtils;

/**
 * Created by admin on 2017/11/26.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLib();
    }

    private void initLib() {
        //开启Logger
        LogUtils.init(BuildConfig.DEBUG);
    }
}
