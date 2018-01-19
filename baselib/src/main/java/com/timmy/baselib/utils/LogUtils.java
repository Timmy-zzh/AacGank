package com.timmy.baselib.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author: lex
 * @Email: ldlywt@gmail.com
 * @Time: 2018/1/11 10:56
 * @Description: 对logger的库进行一层包装，方便以后更换其他库
 */
public class LogUtils {

    private LogUtils(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(final boolean isDebug){
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });
    }

    public static void json(String json){
        Logger.json(json);
    }

    public static void xml(String xml){
        Logger.xml(xml);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void e(String message, Object... args) {
        Logger.e(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

}
