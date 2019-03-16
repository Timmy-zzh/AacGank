package com.timmy.thirdframework.net.core.chain;

import android.util.Log;


import com.timmy.thirdframework.net.core.HttpClient;
import com.timmy.thirdframework.net.core.HttpUrl;
import com.timmy.thirdframework.net.core.Request;
import com.timmy.thirdframework.net.core.Response;
import com.timmy.thirdframework.net.core.connection.ConnectionPool;
import com.timmy.thirdframework.net.core.connection.HttpConnection;

import java.io.IOException;

/**
 * 连接池拦截器，因为同一个Host连接有keep-alive属性，
 * 将可以复用的Socket连接保存到本地连接池中复用
 */
public class ConnectionInterceptor implements Interceptor {
    @Override
    public Response interceptor(InterceptorChain chain) throws IOException {
        Log.d("Timmy", "ConnectionInterceptor  111 ");

        Request request = chain.call.getRequest();
        HttpClient client = chain.call.client();
        HttpUrl httpUrl = request.url();
        //从连接池中获取可用的连接
        ConnectionPool connectionPool = client.getConnectionPool();
        HttpConnection httpConnection = connectionPool.get(httpUrl.getHost(), httpUrl.getPort());

        if (null == httpConnection) {
            Log.d("Timmy", "null == httpConnection");
            httpConnection = new HttpConnection();
        } else {
            Log.d("Timmy", "从连接池中获取可用连接");
        }
        httpConnection.setRequest(request);

        //获取到可用连接，需要将该连接传输到下一个拦截器中使用
        try {
            Response response = chain.proceed(httpConnection);
            Log.d("Timmy", "ConnectionInterceptor  222 ");

            if (response.isKeepAlive()) {
                //保存该连接到连接池
                connectionPool.put(httpConnection);
            } else {
                httpConnection.close();
            }
            return response;
        } catch (IOException e) {
            httpConnection.close();
            throw e;
        }
    }
}
