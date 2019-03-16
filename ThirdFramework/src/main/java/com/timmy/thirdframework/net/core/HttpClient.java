package com.timmy.thirdframework.net.core;

import com.timmy.thirdframework.net.core.connection.ConnectionPool;

/**
 * 请求框架管家
 * 请求重试次数
 * 请求分发者-调度器
 */
public class HttpClient {

    private int retrys;
    private Dispatcher dispatcher;
    private ConnectionPool connectionPool;

    public Call newCall(Request request) {
        return new Call(request, this);
    }

    public HttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.connectionPool = builder.connectionPool;
        this.retrys = builder.retrys;
    }

    public Dispatcher dispatcher() {
        return dispatcher;
    }

    public int retrys() {
        return retrys;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public static final class Builder {
        Dispatcher dispatcher;
        ConnectionPool connectionPool;
        int retrys = 1;

        /**
         * 自定义调度器
         */
        public Builder dispatcher(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder retrys(int retrys) {
            this.retrys = retrys;
            return this;
        }

        public Builder connectionPool(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
            return this;
        }

        public HttpClient build() {
            if (null == dispatcher) {
                this.dispatcher = new Dispatcher();
            }
            if (null == connectionPool) {
                connectionPool = new ConnectionPool();
            }
            return new HttpClient(this);
        }
    }
}
