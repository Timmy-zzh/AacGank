package com.timmy.thirdframework.net.core;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 请求url封装
 * host 域名
 * file 请求地址
 * protocol 协议
 * port 端口
 */
public class HttpUrl {

    String host;
    String file;
    String protocol;
    int port;

    public HttpUrl(String url) throws MalformedURLException {
        URL url1 = new URL(url);
        host = url1.getHost();
        file = url1.getFile();
        file = TextUtils.isEmpty(file) ? "/" : file;
        protocol = url1.getProtocol();
        port = url1.getPort();
        port = port == -1 ? url1.getDefaultPort() : port;
    }

    public String getHost() {
        return host;
    }

    public String getFile() {
        return file;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getPort() {
        return port;
    }
}
