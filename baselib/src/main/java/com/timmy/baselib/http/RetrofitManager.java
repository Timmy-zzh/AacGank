package com.timmy.baselib.http;

import com.timmy.baselib.http.interceptor.HeaderInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit配置
 * 1.一个项目有多个域名请求情况
 * 2.需要使用HashMap实现单例模式--控制每个不同的Retrofit
 */
public class RetrofitManager {

    private static final String GANK_URL = "http://gank.io/api/";
    private final Retrofit retrofit;
    private static final int CONNECT_TIMEOUT = 5;

    private static class RetrofitHolder {
        private static RetrofitManager INSTENCE = new RetrofitManager(GANK_URL);
    }

    public static RetrofitManager instance() {
        return RetrofitHolder.INSTENCE;
    }

    public static RetrofitManager instance(String baseUrl) {
        RetrofitManager newInstance = new RetrofitManager(baseUrl);
        return newInstance;
    }

    private RetrofitManager(String baseUrl) {
        //拦截器
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor(null))//头部请求信息拦截器
                .addInterceptor( new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//打印信息拦截器
//                .addNetworkInterceptor()
                .retryOnConnectionFailure(true)//自动重连
//                .authenticator(new Authenticator() {
//                    @Override
//                    public Request authenticate(Route route, Response response) throws IOException {
//                        return null;
//                    }
//                })
//                .cache()//缓存
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //写操作 超时时间
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //读操作 超时时间
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
