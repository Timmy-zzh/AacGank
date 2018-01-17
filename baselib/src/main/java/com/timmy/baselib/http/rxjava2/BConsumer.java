package com.timmy.baselib.http.rxjava2;

import com.orhanobut.logger.Logger;

import io.reactivex.functions.Consumer;

/**
 * Created by admin on 2017/11/9.
 */
public class BConsumer implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Exception {
        Logger.d(throwable.getMessage());
//        if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
//            Toast.toastCenter("连接超时，请稍后重试");
//        } else if (throwable instanceof UnknownHostException) {
//            Toast.toastCenter("网络异常，请确保网络正常后重试");
//        } else if (throwable instanceof JsonSyntaxException) {
//            Toast.toastCenter("数据解析异常！");
//        } else if (throwable instanceof HttpException) {
//            Toast.toastCenter("网络连接异常，请稍后再试");
//            Logger.d("HttpException code:"+((HttpException) throwable).code());
//        } else {
//            Toast.toastCenter("网络连接异常，请稍后再试");
//        }
        onErrorResult(throwable);
    }

    public void onErrorResult(Throwable throwable) {
    }
}
