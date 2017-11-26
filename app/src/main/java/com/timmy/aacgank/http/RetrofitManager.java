package com.timmy.aacgank.http;

import com.timmy.aacgank.BuildConfig;
import com.timmy.aacgank.http.service.GankService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String BASE_URL = "http://gank.io/api/";
    private final Retrofit retrofit;
    private static RetrofitManager retrofitManager;
    private static final int CONNECT_TIMEOUT = 5;
    private GankService gankService;

    public static RetrofitManager instance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    /**
     * 返回正确的UserAgent * @return
     */
    private static String getUserAgent() {
        String userAgent;
        StringBuilder sb = new StringBuilder();
        userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)

        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private RetrofitManager() {
        //初始化OkHttpClient.Builder 3.0
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        //请求头设置
        Interceptor headInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                //当前用户的手机版本信息
                builder.addHeader("User-Agent", getUserAgent());
                //登录了带上Token
                return chain.proceed(builder.build());
            }
        };
        okHttpBuilder.addInterceptor(headInterceptor);

        //OkHttp3打印
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(loggingInterceptor);

        //设置缓存
//        File httpCacheDirectory = new File(App.getApplicaiton().getBaseContext().getCacheDir(), "retrofitcache");
//        Cache httpCache = new Cache(httpCacheDirectory, 30 * 1024 * 1024);

        //OkHttpClient
        OkHttpClient client = okHttpBuilder
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .cache(httpCache)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }


    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }


    public GankService getGankService() {
        if (gankService == null)
            gankService = create(GankService.class);
        return gankService;
    }
}
