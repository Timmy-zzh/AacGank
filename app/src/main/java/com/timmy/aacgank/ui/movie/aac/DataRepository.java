package com.timmy.aacgank.ui.movie.aac;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.timmy.aacgank.bean.movie.BaseDoubanResult;
import com.timmy.aacgank.bean.movie.DoubanMovie;
import com.timmy.aacgank.http.HttpHelper;
import com.timmy.aacgank.http.ServiceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Model层:
 * 所有数据的源头
 * 数据来源包括:本地数据库,和远端服务器
 */
public class DataRepository {

    private DoubanService service;
    private MutableLiveData<BaseDoubanResult<DoubanMovie>> moviesLiveData;

    public DataRepository() {
        service = HttpHelper.instance().getDoubanService();
    }

    public LiveData<BaseDoubanResult<DoubanMovie>> getDoubanMovies() {
        moviesLiveData = new MutableLiveData<>();
        //异步请求
        service.getDoubanMovies().enqueue(new Callback<BaseDoubanResult<DoubanMovie>>() {
            @Override
            public void onResponse(Call<BaseDoubanResult<DoubanMovie>> call, Response<BaseDoubanResult<DoubanMovie>> response) {
                BaseDoubanResult<DoubanMovie> body = response.body();
                moviesLiveData.setValue(body);
            }

            @Override
            public void onFailure(Call<BaseDoubanResult<DoubanMovie>> call, Throwable t) {

            }
        });
        return moviesLiveData;
    }
}
