package com.timmy.baselib.http.config;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 统一添加请求头信息
 */
public abstract class HeaderInterceptor implements Interceptor {

    private Map<String, String> headers;

    public HeaderInterceptor() {
        headers = new HashMap<>();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder =  chain.request().newBuilder();
        addHeaderInfo(headers);
        if (headers != null && headers.size() > 0) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                builder.addHeader(key, headers.get(key));
            }
        }
        //当前用户的手机版本信息
        builder.addHeader("User-Agent", getUserAgent());
        return chain.proceed(builder.build());
    }

    public abstract void addHeaderInfo(Map<String, String> headers);

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
}
