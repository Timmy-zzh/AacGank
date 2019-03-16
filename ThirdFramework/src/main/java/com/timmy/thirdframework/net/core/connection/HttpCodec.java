package com.timmy.thirdframework.net.core.connection;

import android.text.TextUtils;

import com.timmy.thirdframework.net.core.Request;
import com.timmy.thirdframework.net.core.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * socket请求拼接属性处理
 */
public class HttpCodec {

    //回车和换行
    static final String CRLF = "\r\n";
    static final int CR = 13;
    static final int LF = 10;
    static final String SPACE = " ";
    static final String VERSION = "HTTP/1.1";
    static final String COLON = ":";


    public static final String HEAD_HOST = "Host";
    public static final String HEAD_CONNECTION = "Connection";
    public static final String HEAD_CONTENT_TYPE = "Content-Type";
    public static final String HEAD_CONTENT_LENGTH = "Content-Length";
    public static final String HEAD_TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String HEAD_VALUE_KEEP_ALIVE = "Keep-Alive";
    public static final String HEAD_VALUE_CHUNKED = "chunked";
    private final ByteBuffer byteBuffer;


    public HttpCodec() {
        byteBuffer = ByteBuffer.allocate(10 * 1024);
    }

    /**
     * 往输入流中写入拼接请求头
     */
    public void writeRequest(OutputStream os, Request request) throws IOException {
        StringBuffer sb = new StringBuffer();

        //拼接请求头
        //GET /v3/weather/weatherInfo?city=%E9%95%BF%E6%B2%99&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n
        sb.append(request.method());
        sb.append(SPACE);
        sb.append(request.url().getFile());
        sb.append(SPACE);
        sb.append(VERSION);
        sb.append(CRLF);

        //请求头
        Map<String, String> headers = request.headers();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey());
            sb.append(COLON);
            sb.append(SPACE);
            sb.append(entry.getValue());
            sb.append(CRLF);
        }
        sb.append(CRLF);

        //post请求，有请求体
        RequestBody body = request.body();
        if (null != body) {
            sb.append(body.body());
        }


        os.write(sb.toString().getBytes());
        os.flush();
    }

    public String readLine(InputStream is) throws IOException {
        //清理bytebuffer
        byteBuffer.clear();
        //标记
        byteBuffer.mark();
        boolean isMabeyEofLine = false;
        byte b;
        //一次读一个字节
        while ((b = (byte) is.read()) != -1) {
            byteBuffer.put(b);
            //如果当前读到一个 /r
            if (b == CR) {
                isMabeyEofLine = true;
            } else if (isMabeyEofLine) {
                //读到 /n
                if (b == LF) {
                    //一行数据
                    byte[] lineBytes = new byte[byteBuffer.position()];
                    //将
                    byteBuffer.reset();
                    //从bytebuffer获得数据
                    byteBuffer.get(lineBytes);
                    byteBuffer.clear();
                    byteBuffer.mark();
                    return new String(lineBytes);
                }
                isMabeyEofLine = false;
            }
        }
        throw new IOException("Response read Line");
    }

    public Map<String, String> readHeaders(InputStream is) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        while (true) {
            String line = readLine(is);
            //如果读到空行 \r\n 响应头读完了
            if (isEmptyLine(line)) {
                break;
            }
            int index = line.indexOf(":");
            if (index > 0) {
                String key = line.substring(0, index);
                String value = line.substring(index + 2, line.length() - 2);
                headers.put(key, value);
            }
        }
        return headers;
    }

    private boolean isEmptyLine(String line) {
        return TextUtils.equals(line, CRLF);
    }


    public byte[] readBytes(InputStream is, int len) throws IOException {
        byte[] bytes = new byte[len];
        int readNum = 0;
        while (true) {
            readNum += is.read(bytes, readNum, len - readNum);
            //读取完毕
            if (readNum == len) {
                return bytes;
            }
        }
    }

    public String readChunked(InputStream is) throws IOException {

        int len = -1;
        boolean isEmptyData = false;
        StringBuffer chunked = new StringBuffer();
        while (true) {
            if (len < 0) {
                // chunk的长度
                String line = readLine(is);
                line = line.substring(0, line.length() - 2);
                //获得长度 16进制字符串转成10进制整型
                len = Integer.valueOf(line, 16);
                //如果长度是0 再读一个/r/n 响应结束
                isEmptyData = len == 0;
            } else {
                //读内容
                byte[] bytes = readBytes(is, len + 2);
                chunked.append(new String(bytes));
                len = -1;
                if (isEmptyData) {
                    return chunked.toString();
                }
            }
        }
    }
}
