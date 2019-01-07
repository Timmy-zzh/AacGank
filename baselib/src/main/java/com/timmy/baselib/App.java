package com.timmy.baselib;

import android.app.Application;
import android.content.Context;

import com.timmy.baselib.utils.LogUtils;
import com.timmy.baselib.utils.Utils;

/**
 * Created by admin on 2017/11/26.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initLib();
    }

    public static Context getContext(){
        return mContext;
    }


    private void initLib() {
        Utils.init(this);
    }
}
