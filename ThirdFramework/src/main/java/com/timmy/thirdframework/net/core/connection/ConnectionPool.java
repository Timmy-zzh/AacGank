package com.timmy.thirdframework.net.core.connection;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 连接池
 * 里面有连接集合，并开启一个线程检测失效的连接移除
 */
public class ConnectionPool {

    /**
     * 最长闲置时间,默认60s
     */
    private long keepAliveTime;
    //是否正在清理
    private boolean cleanupRuning;

    private Deque<HttpConnection> connections = new ArrayDeque<>();

    public ConnectionPool() {
        this(1, TimeUnit.MINUTES);
    }

    public ConnectionPool(long keepAlive, TimeUnit unit) {
        this.keepAliveTime = unit.toMillis(keepAlive);
    }

    private static final Executor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r, "Connection Pool");
            //设置为守护线程
            thread.setDaemon(true);
            return thread;
        }
    });

    //清理线程
    Runnable cleanupRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                long waitDuration = cleanUp(System.currentTimeMillis());
                if (waitDuration == -1) {
                    return;
                }
                if (waitDuration > 0) {
                    synchronized (ConnectionPool.this) {
                        try {
                            ConnectionPool.this.wait(waitDuration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

    /**
     * 遍历所有连接，找出连接最后使用时间与当前时间比较超过60s的删除
     *
     * @param now
     * @return
     */
    private long cleanUp(long now) {
        long longestIdDuration = -1;
        synchronized (this) {
            Log.d("Timmy", "cleanUp connections:" + connections.size());
            Iterator<HttpConnection> iterator = connections.iterator();
            while (iterator.hasNext()) {
                HttpConnection connection = iterator.next();
                //计算闲置时间
                long idleDuration = now - connection.lastUseTime;
                if (idleDuration > keepAliveTime) {
                    iterator.remove();
                    connection.close();
                    Log.d("Timmy", "移除超过60s的连接");
                    continue;
                }
                //记录最长的闲置时间
                if (longestIdDuration < idleDuration) {
                    longestIdDuration = idleDuration;
                }
            }

            if (longestIdDuration >= 0) {
                return keepAliveTime - longestIdDuration;
            }

            Log.d("Timmy", "连接池中没有连接");
            //连接池中没有连接
            cleanupRuning = false;
            return longestIdDuration;
        }
    }

    /**
     * 连接池put
     * 连接池中线程池启动线程循环遍历移除无效连接
     *
     * @param connection
     */
    public void put(HttpConnection connection) {
        Log.d("Timmy", "  connections:" + connections.size());
        Log.d("Timmy", "  cleanupRuning:" + cleanupRuning);
        if (!cleanupRuning) {
            cleanupRuning = true;
            executor.execute(cleanupRunnable);
        }
        connections.add(connection);
    }

    /**
     * 找出相同host，port的连接
     *
     * @param host
     * @param port
     * @return
     */
    public HttpConnection get(String host, int port) {
        Iterator<HttpConnection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            HttpConnection connection = iterator.next();
            if (connection.isSameAddress(host, port)) {
                iterator.remove();
                return connection;
            }
        }
        return null;
    }
}
