package com.timmy.aacgank.ui.movie.aac;

import android.arch.lifecycle.ViewModel;

import com.timmy.aacgank.bean.movie.BaseDoubanResult;
import com.timmy.aacgank.bean.movie.DoubanMovie;
import com.timmy.aacgank.http.HttpHelper;
import com.timmy.baselib.bean.PageListResult;

import io.reactivex.Flowable;

public class MovieViewModel extends ViewModel {

    public Flowable<BaseDoubanResult<DoubanMovie>> getHotMovies() {
        return HttpHelper.instance().getDoubanService().getHotMovies();
    }





}
