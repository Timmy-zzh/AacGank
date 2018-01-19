package com.timmy.baselib.http.interceptor;

import com.timmy.baselib.utils.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 统一添加请求头信息
 */
public class HeaderInterceptor implements Interceptor {

    private Map<String, String> headers;

    public HeaderInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
        Request.Builder builder =  chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                builder.addHeader(key, headers.get(key));
            }
        }
        //当前用户的手机版本信息
        builder.addHeader("User-Agent", Utils.getUserAgent());
        builder.header("name","Timmy");
//        builder.addHeader("ear","中国");//报错,头信息使用ASCII编码
        //登录了带上Token

//        Logger.d("url:" + request.url());
//        Logger.d("connection:" + chain.connection().toString());
//        Headers headers = request.headers();
//        Logger.d("Headers: " + headers.get("User-Agent"));
//        Logger.d("method:" + request.method());
//        Logger.d("body:" + request.body().toString());


//        Response response =  chain.proceed(builder.build());
//
////        Response response = chain.proceed(request);//收到的响应
//        Logger.d("message:" + response.message());
//        Logger.d("body:" + response.body().toString());
//        Logger.d("code:" + response.code());

        return chain.proceed(builder.build());
//        return response;
    }
}
