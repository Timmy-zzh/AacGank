package com.timmy.thirdframework.eventbus.fw;

import java.lang.reflect.Method;

/**
 * 封装类
 */
public class SubscribleMethod {

    //Activity中的方法->post方法中需要反射调用
    private Method method;

    //方法中的参数,参数决定只能接收该类型的事件
    private Class<?> parmerClass;

    //注解上的线程模式
    private ThreadMode threadMode;

    public SubscribleMethod(Method method, Class<?> parmerClass, ThreadMode threadMode) {
        this.method = method;
        this.parmerClass = parmerClass;
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getParmerClass() {
        return parmerClass;
    }

    public void setParmerClass(Class<?> parmerClass) {
        this.parmerClass = parmerClass;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }
}
