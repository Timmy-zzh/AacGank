package com.timmy.thirdframework.net.core.chain;

import android.util.Log;


import com.timmy.thirdframework.net.core.Response;
import com.timmy.thirdframework.net.core.connection.HttpCodec;
import com.timmy.thirdframework.net.core.connection.HttpConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 使用连接池中的连接，进行最终的socket请求
 * 1.去除连接HttpConnection，使用连接中封装的Socket进行发起请求
 */
public class CallServiceInterceptor implements Interceptor {
    @Override
    public Response interceptor(InterceptorChain chain) throws IOException {
        Log.d("Timmy", "CallServiceInterceptor 111");
        HttpConnection connection = chain.connection;

        HttpCodec httpCodec = new HttpCodec();
        //发起请求,获得输入流，响应
        InputStream is = connection.call(httpCodec);

        //获取响应行
        String statusLine = httpCodec.readLine(is);
        String[] status = statusLine.split(" ");

        //响应头
        Map<String, String> headers = httpCodec.readHeaders(is);
        //根据Content-Length 解析
        int contentLength = -1;
        if (headers.containsKey("Content-Length")) {
            contentLength = Integer.valueOf(headers.get("Content-Length"));
        }
        //根据分块编码 解析
        boolean isChunked = false;
        if (headers.containsKey("Transfer-Encoding")) {
            isChunked = headers.get("Transfer-Encoding").equalsIgnoreCase("chunked");
        }

        //响应体
        String body = null;
        if (contentLength > 0) {
            byte[] bytes = httpCodec.readBytes(is, contentLength);
            body = new String(bytes);
        } else if (isChunked) {
            body = httpCodec.readChunked(is);  //按快解析获取响应体
        }

        boolean isKeepAlive = false;
        if (headers.containsKey("Connection")) {
            isKeepAlive = headers.get("Connection").equalsIgnoreCase("keep-alive");
        }
        //更新连接的请求时间
        connection.updateLastUseTime();
        Log.d("Timmy", "CallServiceInterceptor 222 +isKeepAlive:"+isKeepAlive);

        return new Response(Integer.valueOf(status[1]), contentLength, headers, body, isKeepAlive);
    }
}
