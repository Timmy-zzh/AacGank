package com.timmy.aacgank.http;

import com.timmy.baselib.http.RetrofitManager;

import java.util.HashMap;

/**
 * 所有的Service接口从这里获取
 * 不用每次读取反射获取,使用容器先进行缓存
 */
public class ServiceManager {

    private HashMap<String, Object> serviceMap;
    private static volatile ServiceManager instance;

    private ServiceManager() {
        this.serviceMap = new HashMap<>();
    }

    public static ServiceManager instance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new ServiceManager();
                }
            }
        }
        return instance;
    }

    /**
     * 返回网络请求接口实例 -->二次封装
     * 也可以直接调用create()方法,
     */
//    public static SettingsService getSettingsService() {
//        return ServiceManager.instance().create(SettingsService.class);
//    }
//
//    public static ProductService getProductService() {
//        return ServiceManager.instance().create(ProductService.class);
//    }
//
//    public static CategoryService getCategoryService() {
//        return ServiceManager.instance().create(CategoryService.class);
//    }


    public <T> T create(final Class<T> service) {
        String simpleName = service.getSimpleName();
        if (serviceMap.get(simpleName) == null) {
//            serviceMap.put(simpleName, RetrofitManager.instance().createService(service));
        }
        return (T) serviceMap.get(simpleName);
    }
}
