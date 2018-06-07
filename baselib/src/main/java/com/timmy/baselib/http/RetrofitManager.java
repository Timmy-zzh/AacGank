package com.timmy.baselib.http;


import android.util.Log;

import java.util.HashMap;

/**
 * Retrofit管理器
 * 项目不同之处有
 * 1.BaseUrl
 * 2.请求头信息不同
 * 3.SSL证书设置
 * 单例模式: 根据不同的BaseUrl获取自己需要的Retrofit
 *
 */
public class RetrofitManager {

    //保存各种不同配置的RetrofitManager的容器
    private static final HashMap<String, RetrofitWrapper> retrofitHashMap = new HashMap<>();

    public static void registerRetrofit(String key,RetrofitWrapper.RetrofitFetcher retrofitFetcher){
        if (retrofitHashMap.containsKey(key)){
            Log.d("RetrofitManager","容器中已经存在该RetrofitManager实例,请直接getRetrofit(key)获取");
            return;
        }
        RetrofitWrapper retrofit = new RetrofitWrapper(key,retrofitFetcher);
        retrofitHashMap.put(key,retrofit);
    }

    public static RetrofitWrapper getRetrofit(String key){
        return retrofitHashMap.get(key);
    }
}
