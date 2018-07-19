package com.timmy.aacgank.http;

import com.timmy.aacgank.ui.gank.aac.GankService;
import com.timmy.aacgank.ui.movie.aac.DoubanService;
import com.timmy.baselib.http.RetrofitManager;
import com.timmy.baselib.http.RetrofitWrapper;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;
import com.timmy.baselib.utils.LogUtils;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HttpHelper {

    private static final String GANK_URL = "http://gank.io/api/";
    private static final String DOUBAN_URL = "https://api.douban.com/";

    private GankService gankService;
    private DoubanService doubanService;

    static {
        RetrofitManager.registerRetrofit(GANK_URL, null);
        RetrofitManager.registerRetrofit(DOUBAN_URL, new RetrofitWrapper.RetrofitFetcher() {
            @Override
            public void addHeaderInfo(Map<String, String> headers) {
                LogUtils.d("HttpHelper  addHeaderInfo");
            }
        });
    }

    private static class HttpHolder {
        private static HttpHelper INSTENCE = new HttpHelper();
    }

    public static HttpHelper instance() {
        return HttpHelper.HttpHolder.INSTENCE;
    }

    /////////////////////////////////////////////////////////////////////////////
    public GankService getGankService() {
        if (gankService == null)
            gankService = RetrofitManager.getRetrofit(GANK_URL).create(GankService.class);
        return gankService;
    }


    public DoubanService getDoubanService() {
        if (doubanService == null)
            doubanService = RetrofitManager.getRetrofit(DOUBAN_URL).create(DoubanService.class);
        return doubanService;
    }

    /////////////////////////////////////////////////////////////////////////////
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
