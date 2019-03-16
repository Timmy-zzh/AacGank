package com.timmy.thirdframework.net.core;

import com.timmy.thirdframework.net.core.chain.CallServiceInterceptor;
import com.timmy.thirdframework.net.core.chain.ConnectionInterceptor;
import com.timmy.thirdframework.net.core.chain.HeaderInterceptor;
import com.timmy.thirdframework.net.core.chain.Interceptor;
import com.timmy.thirdframework.net.core.chain.InterceptorChain;
import com.timmy.thirdframework.net.core.chain.RetryInterceptor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 请求体的二次封装
 * 包含了request，和HttpClient
 * 最后调用enqueue方法发起请求，由HttpClient中的调度器，线程池执行操作
 */
public class Call {
    //请求
    Request request;
    //
    HttpClient client;
    //是否执行过
    boolean executed;
    //是否取消
    private boolean canceled;

    public Call(Request request, HttpClient client) {
        this.request = request;
        this.client = client;
    }

    public HttpClient client() {
        return client;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Request getRequest() {
        return request;
    }

    /**
     * 取消
     */
    public void cancel() {
        canceled = true;
    }

    //真正的发起请求
    public void enqueue(Callback callback) {
        synchronized (this) {
            if (executed) {
                throw new IllegalStateException("该请求已经执行过了");
            }
            executed = true;
        }
        client.dispatcher().enqueue(new AsyncCall(callback));
    }

    /**
     * 在该线程中获取请求数据，并回调接口
     */
    class AsyncCall implements Runnable {

        private Callback callback;

        public AsyncCall(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            //信号 是否回调过--是否在获取Response时报错
            boolean signalledCallbacked = false;
            try {
                Response response = getResponse();
                if (canceled) {  //取消请求
                    signalledCallbacked = true;
                    callback.onFailure(Call.this, new IOException("Canceled"));
                } else {      //请求成功
                    signalledCallbacked = true;
                    callback.onResponse(Call.this, response);
                }
            } catch (Exception e) {
                if (!signalledCallbacked) {
                    callback.onFailure(Call.this, e);
                }
            } finally {
                //将该任务从调度器中移除
                client.dispatcher().finished(this);
            }
        }

        public String host() {
            return request.url().getHost();
        }
    }

    //通过各种拦截器--责任链模式
    private Response getResponse() throws Exception {
        //拦截器集合
        List<Interceptor> interceptors = new LinkedList<>();
        //重试拦截器
        interceptors.add(new RetryInterceptor());
        //请求头拦截器
        interceptors.add(new HeaderInterceptor());
        //连接拦截器
        interceptors.add(new ConnectionInterceptor());
        //通信拦截器
        interceptors.add(new CallServiceInterceptor());

        InterceptorChain chain = new InterceptorChain(interceptors, this, 0, null);
        return chain.proceed();
    }
}
