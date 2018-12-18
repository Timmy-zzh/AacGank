package com.timmy.aacgank;

import android.app.Application;

import com.timmy.baselib.utils.LogUtils;
import com.timmy.baselib.utils.Utils;

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
        Utils.init(this);
    }
}
