package com.timmy.baselib.http.rxjava2;



import com.timmy.baselib.bean.BaseResult;

import io.reactivex.functions.Consumer;

public abstract class AConsumer<T> implements Consumer<T> {

    @Override
    public void accept(T t) throws Exception {
        if (t instanceof BaseResult) {
            BaseResult baseResult = (BaseResult) t;
            if (baseResult.getStatusCode() == 401) {
                //401 登录失效
            }
            onGetResult(t);
        } else {
            onGetResult(t);
        }
    }

    public abstract void onGetResult(T t);
}
