package com.timmy.thirdframework.net.core;

import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 请求分发者
 * 正在执行的请求队列
 * 等待请求队列
 * 线程池
 */
public class Dispatcher {

    //同时进行的最大请求数
    private int maxRequests = 64;
    //同时请求的相同host的最大数
    private int maxRequestsPreHost = 5;

    //等待执行队列
    private Deque<Call.AsyncCall> readyAsyncCalls = new ArrayDeque<>();
    //正在执行队列
    private Deque<Call.AsyncCall> runningAsyncCalls = new ArrayDeque<>();
    //线程池
    private ExecutorService executorService;

    //创建线程池
    private synchronized ExecutorService executorService() {
        if (null == executorService) {
            //线程工厂 -- 创建线程
            ThreadFactory threadFactory = new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    return new Thread(r, "TOkHttp");
                }
            };
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60,
                    TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
        }
        return executorService;
    }

    /**
     * 线程池执行
     *
     * @param asyncCall
     */
    public void enqueue(Call.AsyncCall asyncCall) {
        //不能超过最大请求数与相同host的请求数
        //满足条件意味着可以马上开始任务
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(asyncCall) < maxRequestsPreHost) {
            runningAsyncCalls.add(asyncCall);
            executorService().execute(asyncCall);
        } else {
            readyAsyncCalls.add(asyncCall);
        }
    }

    private int runningCallsForHost(Call.AsyncCall call) {
        int result = 0;
        for (Call.AsyncCall c : runningAsyncCalls) {
            if (c.host().equals(call.host())) {
                result++;
            }
        }
        return result;
    }

    public void finished(Call.AsyncCall asyncCall) {
        runningAsyncCalls.remove(asyncCall);
        //检测是否可以运行ready
        checkReady();
    }

    /**
     * 执行完一个请求，查看请求队列中是否还有可以执行的请求，有的话还需要调用线程池去执行请求
     * 1。判断正在执行请求队列的个数
     * 2。是否有等待的请求
     * 3。循环，拿到第一个请求请求，交给线程池执行
     */
    private void checkReady() {
        if (runningAsyncCalls.size() >= maxRequests) {
            return;
        }
        if (readyAsyncCalls.isEmpty()) {
            return;
        }
        Iterator<Call.AsyncCall> iterator = readyAsyncCalls.iterator();
        while (iterator.hasNext()) {
            Call.AsyncCall asyncCall = iterator.next();
            if (runningCallsForHost(asyncCall) < maxRequestsPreHost) {
                iterator.remove();
                runningAsyncCalls.add(asyncCall);
                executorService.execute(asyncCall);
            }
            if (runningAsyncCalls.size() >= maxRequests) {
                return;
            }
        }
    }
}
