package com.timmy.thirdframework.net.core;

/**
 * 网路请求回调
 */
public interface Callback {

    void onResponse(Call call, Response response);

    void onFailure(Call call, Throwable throwable);
}
