package com.timmy.thirdframework.net.core.connection;

import android.text.TextUtils;


import com.timmy.thirdframework.net.core.HttpUrl;
import com.timmy.thirdframework.net.core.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

/**
 * 连接池中的一个连接Socket
 * socket连接有一个时效性，所以在连接池中需要使用一个线程进行循环，
 * remove掉已经失效的socket连接
 */
public class HttpConnection {

    Socket socket;
    //最后使用时间
    long lastUseTime;
    private Request request;
    private InputStream is;
    private OutputStream os;

    /**
     * 判断与当前连接是否是同一域名和端口，同样的话，才可能复用该连接
     */
    public boolean isSameAddress(String host, int port) {
        if (null == socket) {
            return false;
        }
        return TextUtils.equals(socket.getInetAddress().getHostName(), host)
                && port == socket.getPort();
    }


    /**
     * 关闭该连接
     */
    public void close() {
        if (null != socket) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * HttpConnection连接封装类，内部包含的是Socket，
     * 真正发起请求的是Socket进行通信，
     */
    public InputStream call(HttpCodec httpCode) throws IOException {
        createSocket();
        //发送请求
        httpCode.writeRequest(os,request);
        //返回服务器响应，输入流（InputStream）
        return is;
    }

    private void createSocket() throws IOException {
        if (null == socket || socket.isClosed()) {
            HttpUrl httpUrl = request.url();
            //创建新的Socket实例
            if (httpUrl.getProtocol().equalsIgnoreCase("https")) {
                socket = SSLSocketFactory.getDefault().createSocket();
            } else {
                socket = new Socket();
            }
            //使用socket连接
            socket.connect(new InetSocketAddress(httpUrl.getHost(), httpUrl.getPort()));
            //连接后获取输入流，输入流
            is = socket.getInputStream();
            os = socket.getOutputStream();
        }
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    //请求结束后，更新最后请求时间
    public void updateLastUseTime() {
        this.lastUseTime = System.currentTimeMillis();
    }
}
