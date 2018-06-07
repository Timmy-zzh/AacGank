package com.timmy.baselib.http.rxjava2;

import com.google.gson.JsonSyntaxException;
import com.timmy.baselib.utils.LogUtils;
import com.timmy.baselib.utils.ToastUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * Created by admin on 2017/11/9.
 */
public class BConsumer implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Exception {
        LogUtils.d(throwable.getMessage());
        if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
            ToastUtils.showShort("连接超时，请稍后重试");
        } else if (throwable instanceof UnknownHostException) {
             ToastUtils.showShort("网络异常，请确保网络正常后重试");
        } else if (throwable instanceof JsonSyntaxException) {
             ToastUtils.showShort("数据解析异常！");
        } else if (throwable instanceof HttpException) {
             ToastUtils.showShort("网络连接异常，请稍后再试");
            LogUtils.d("HttpException code:"+((HttpException) throwable).code());
        } else {
             ToastUtils.showShort("网络连接异常，请稍后再试");
        }
        onErrorResult(throwable);
    }

    public void onErrorResult(Throwable throwable) {
    }
}
