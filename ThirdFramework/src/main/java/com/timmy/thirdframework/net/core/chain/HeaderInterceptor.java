package com.timmy.thirdframework.net.core.chain;

import android.util.Log;


import com.timmy.thirdframework.net.core.Request;
import com.timmy.thirdframework.net.core.RequestBody;
import com.timmy.thirdframework.net.core.Response;

import java.io.IOException;
import java.util.Map;

/**
 * 请求头拦截器
 * 在该链中拿到当前请求Request，然后添加头部
 * 1.Connection
 * 2。Host
 * 3。Content-Length
 * 4.Content-Type
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response interceptor(InterceptorChain chain) throws IOException {

        Log.d("Timmy", "HeaderInterceptor  111 ");

        Request request = chain.call.getRequest();
        Map<String, String> headers = request.headers();
        if (!headers.containsKey("Connection")) {
            headers.put("Connection", "Keep-Alive");
        }
        //域名
        headers.put("Host", request.url().getHost());
        //
        if (null != request.body()) {
            RequestBody body = request.body();
            long contentLength = body.contentLength();
            if (contentLength != 0) {
                headers.put("Content-Length", String.valueOf(contentLength));
            }
            String contentType = body.contentType();
            if (null != contentType) {
                headers.put("Content-Type", contentType);
            }
        }
        //责任链下一个
        Response response = chain.proceed();
        Log.d("Timmy", "HeaderInterceptor  222 ");
        return response;
    }
}
