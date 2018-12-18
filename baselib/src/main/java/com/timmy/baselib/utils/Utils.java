
package com.timmy.baselib.utils;

import android.content.Context;

/**
 * @author: lex
 * @Email: ldlywt@gmail.com
 * @Time: 2018/1/11 11:32
 * @Description: 从主项目中传入一个全局的Application Context进来
 */
public final class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
        SpHelper.init(context);
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

}