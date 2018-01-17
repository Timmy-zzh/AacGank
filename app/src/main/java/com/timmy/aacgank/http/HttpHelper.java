package com.timmy.aacgank.http;

import com.timmy.aacgank.http.service.GankService;
import com.timmy.baselib.http.RetrofitManager;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HttpHelper {

    private GankService gankService;

    public static HttpHelper instance() {
        return HttpHelper.HttpHolder.INSTENCE;
    }

    private static class HttpHolder {
        private static HttpHelper INSTENCE = new HttpHelper();
    }

    /////////////////////////////////////////////////////////////////////////////
    public GankService getGankService() {
        if (gankService == null)
            gankService = RetrofitManager.instance().create(GankService.class);
        return gankService;
    }

    public <T> Disposable request(Flowable<T> flowable, AConsumer<T> aConsumer, BConsumer bConsumer) {
        return flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aConsumer, bConsumer);
    }

    //转换器
    private FlowableTransformer flowableToMain() {
        return new FlowableTransformer() {
            @Override
            public Publisher apply(Flowable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
