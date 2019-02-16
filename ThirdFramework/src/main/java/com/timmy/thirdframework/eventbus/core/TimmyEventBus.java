package com.timmy.thirdframework.eventbus.core;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimmyEventBus {

    public static TimmyEventBus instance;
    private Map<Object, List<SubscribleMethod>> cacheMap;
    private final Handler handler;
    private final ExecutorService executorService;

    private TimmyEventBus() {
        cacheMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        //子线程
        executorService = Executors.newCachedThreadPool();
    }

    public static TimmyEventBus getDefault() {
        if (instance == null) {
            synchronized (TimmyEventBus.class) {
                if (instance == null) {
                    instance = new TimmyEventBus();
                }
            }
        }
        return instance;
    }

    /**
     * 注册:
     * 主要获取该Activity中及父类所有方法,判断方法上是否有Subscribe注解.
     * 有该注解,则将该方法,里面的参数和线程模式进行封装后保存
     * 最外部使用Map保存在内存中
     *
     * @param activity
     */
    public void register(Object activity) {
        List<SubscribleMethod> list = cacheMap.get(activity);
        if (list == null) {
            List<SubscribleMethod> methodList = getSubscrbleMethods(activity);
            cacheMap.put(activity, methodList);
        }
    }

    /**
     * @param activity
     * @return
     */
    private List<SubscribleMethod> getSubscrbleMethods(Object activity) {
        List<SubscribleMethod> list = new CopyOnWriteArrayList<>();
        Class<?> clazz = activity.getClass();
        while (clazz != null) {
            //去除java javax包下的父类
            String clazzName = clazz.getName();
            if (clazzName.startsWith("java.") || clazzName.startsWith("javax.") || clazzName.startsWith("android.")) {
                break;
            }

            //拿到该类的所有方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                //判断该方法上是否有SubScrible注解
                Subscribe annotation = method.getAnnotation(Subscribe.class);
                if (annotation == null) {
                    continue;
                }

                //拿到参数,且参数只能有一个
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    Class<?> parameterType = parameterTypes[0];
                    ThreadMode threadMode = annotation.threadMode();
                    SubscribleMethod subscribleMethod = new SubscribleMethod(method, parameterType, threadMode);

                    list.add(subscribleMethod);
                } else {
                    throw new RuntimeException("EventBus不能传入多个参数!");
                }
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    /**
     * 发送通知:
     * 需要遍历Map中保存的所有界面对应的注解方法list
     * 拿到封装类SubscribleMethod里面的参数是否和该post方法传入的参数是否是同一类型
     *
     * @param obj
     */
    public void post(final Object obj) {
        Set<Object> keySet = cacheMap.keySet();
        Iterator<Object> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            //遍历拿到某个Activity
            final Object activity = iterator.next();
            List<SubscribleMethod> methodList = cacheMap.get(activity);

            //拿到Activity对应注册的所有方法
            for (final SubscribleMethod subscribleMethod : methodList) {

                //判断post发送的事件类型和保存的参数类型是否一致
                if (subscribleMethod.getParmerClass().isAssignableFrom(obj.getClass())) {

                    //判断subscribleMethod中保存的线程模式
                    switch (subscribleMethod.getThreadMode()) {
                        case PostThread://处于同一线程
                            invoke(subscribleMethod, activity, obj);
                            break;
                        case MainThread://主线程调用
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                invoke(subscribleMethod, activity, obj);
                            } else {
                                //线程切换到主线程
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribleMethod, activity, obj);
                                    }
                                });
                            }
                            break;
                        case BackgroundThread://子线程执行
                            if (Looper.myLooper() != Looper.getMainLooper()) {
                                invoke(subscribleMethod, activity, obj);
                            } else {
                                //切换到子线程执行
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribleMethod, activity, obj);
                                    }
                                });
                            }
                            break;
                        case Async:

                            break;
                    }
                }
            }
        }
    }

    /**
     * 反射调用使得方法执行
     *
     * @param subscribleMethod 拿到那个方法
     * @param activity         在哪个Activityy中
     * @param obj              方法调用的参数
     */
    private void invoke(SubscribleMethod subscribleMethod, Object activity, Object obj) {
        try {
            subscribleMethod.getMethod().invoke(activity, obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unregister(Object obj) {
        cacheMap.remove(obj);
    }
}
