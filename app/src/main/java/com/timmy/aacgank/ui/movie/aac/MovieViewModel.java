package com.timmy.aacgank.ui.movie.aac;

import android.arch.lifecycle.ViewModel;

import com.timmy.aacgank.bean.movie.BaseDoubanResult;
import com.timmy.aacgank.bean.movie.DoubanMovie;
import com.timmy.aacgank.http.HttpHelper;
import com.timmy.baselib.bean.PageListResult;

import io.reactivex.Flowable;

public class MovieViewModel extends ViewModel {

    /**
     * 第一页从0开始  :0~19
     * 第二页从20开始: 20~39
     *   2    40      40~60
     * @param page
     * @return
     */
    public Flowable<BaseDoubanResult<DoubanMovie>> getHotMovies(int page) {
        return HttpHelper.instance().getDoubanService().getHotMovies((page-1)*20);
    }

}
