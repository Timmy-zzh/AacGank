package com.timmy.thirdframework.net.core.chain;

import android.util.Log;

import com.timmy.thirdframework.net.core.Call;
import com.timmy.thirdframework.net.core.HttpClient;
import com.timmy.thirdframework.net.core.Response;

import java.io.IOException;

/**
 * 重试拦截器
 */
public class RetryInterceptor implements Interceptor {
    @Override
    public Response interceptor(InterceptorChain chain) throws IOException {
        IOException exception = null;
        Log.d("Timmy", "RetryInterceptor  111 ");

        Call call = chain.call;
        HttpClient client = call.client();
        for (int i = 0; i < client.retrys(); i++) {
            if (call.isCanceled()) {
                throw new IOException("请求取消");
            }
            try {
                Response response = chain.proceed();
                Log.d("Timmy", "RetryInterceptor  222 ");
                return response;
            } catch (IOException e) {
                exception = e;
            }
        }
        throw exception;
    }
}
