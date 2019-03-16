package com.timmy.thirdframework.net.core.chain;


import com.timmy.thirdframework.net.core.Call;
import com.timmy.thirdframework.net.core.Response;
import com.timmy.thirdframework.net.core.connection.HttpConnection;

import java.io.IOException;
import java.util.List;

/**
 * 拦截器责任链
 * 包含了所有的拦截器，从第一个拦截器开始取出来，然后执行
 * 执行完第一个，执行下一个拦截器中的拦截方法
 * Call
 * index 标记
 */
public class InterceptorChain {

    List<Interceptor> interceptors;
    Call call;
    int index;
     HttpConnection connection;

    public InterceptorChain(List<Interceptor> interceptors, Call call, int index, HttpConnection connection) {
        this.interceptors = interceptors;
        this.call = call;
        this.index = index;
        this.connection = connection;
    }

    public Response proceed(HttpConnection httpConnection) throws IOException {
        this.connection = httpConnection;
        return proceed();
    }

    //去除当前index的拦截器，执行拦截器方法，
    public Response proceed() throws IOException {
        if (index >= interceptors.size()){
            throw new IOException("InterceptorChain 长度异常");
        }
        Interceptor interceptor = interceptors.get(index);
        //新建一个新的责任揽，除了index加1，其他拦截器集合一样
        InterceptorChain chain = new InterceptorChain(interceptors, call, index + 1,connection);
        return interceptor.interceptor(chain);
    }
}
