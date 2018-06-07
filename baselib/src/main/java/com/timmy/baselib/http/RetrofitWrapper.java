package com.timmy.baselib.http;


import com.timmy.baselib.BuildConfig;
import com.timmy.baselib.http.config.HeaderInterceptor;
import com.timmy.baselib.utils.LogUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
public class RetrofitWrapper {

    private static final int CONNECT_TIMEOUT = 5;
    private final Retrofit retrofit;

    public RetrofitWrapper(String baseUrl, final RetrofitFetcher retrofitFetcher) {
        //拦截器
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor() {
                    @Override
                    public void addHeaderInfo(Map<String, String> headers) {
                        if (retrofitFetcher != null) {
                            retrofitFetcher.addHeaderInfo(headers);
                        }
                    }
                })//头部请求信息拦截器
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        LogUtils.d(message);
                    }
                }).setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))//打印信息拦截器
                .retryOnConnectionFailure(true)//自动重连
//                .sslSocketFactory(SslHelper.getSslSocketFactory(Utils.getContext()))//获取SslSocketFactory
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;//自行添加判断逻辑，true->Safe，false->unsafe
//                    }
//                })
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

    public interface RetrofitFetcher {
        void addHeaderInfo(Map<String, String> headers);
    }

}
