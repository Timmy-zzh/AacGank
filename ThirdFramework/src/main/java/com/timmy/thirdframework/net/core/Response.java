package com.timmy.thirdframework.net.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应体封装
 * code 响应吗
 * contentLength 响应体长度
 * headers 响应头
 * body 响应内容
 */
public class Response {

    private int code;
    private int contentLength = -1;
    private Map<String, String> headers = new HashMap<>();
    private String body;
    //保持连接
    boolean isKeepAlive;

    public Response() {
    }

    public Response(int code, int contentLength, Map<String, String> headers, String body,
                    boolean isKeepAlive) {
        this.code = code;
        this.contentLength = contentLength;
        this.headers = headers;
        this.body = body;
        this.isKeepAlive = isKeepAlive;
    }

    public int getCode() {
        return code;
    }

    public int getContentLength() {
        return contentLength;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public boolean isKeepAlive() {
        return isKeepAlive;
    }
}
