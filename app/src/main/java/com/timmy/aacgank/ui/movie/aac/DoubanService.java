package com.timmy.aacgank.ui.movie.aac;

import com.timmy.aacgank.bean.movie.BaseDoubanResult;
import com.timmy.aacgank.bean.movie.DoubanMovie;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DoubanService {

    /**
     * 电影 正在上映
     * /v2/movie/in_theaters
     */
    @GET("v2/movie/in_theaters")
    Flowable<BaseDoubanResult<DoubanMovie>> getHotMovies();


    @GET("v2/movie/in_theaters")
    Call<BaseDoubanResult<DoubanMovie>> getDoubanMovies();

}
