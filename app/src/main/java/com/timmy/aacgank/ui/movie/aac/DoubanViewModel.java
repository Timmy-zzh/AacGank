package com.timmy.aacgank.ui.movie.aac;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.timmy.aacgank.bean.movie.BaseDoubanResult;
import com.timmy.aacgank.bean.movie.DoubanMovie;

/**
 * ViewModel的生命周期可能比Activity/Fragment的生命周期都长,所以不能持有View,Activity的引用
 * 当ViewModel需要调用系统服务时,可以传入Applicaiton
 */
public class DoubanViewModel extends AndroidViewModel {

    private final DataRepository repository;

    public DoubanViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository();
    }

    public LiveData<BaseDoubanResult<DoubanMovie>> getDoubanMovies(){
        return repository.getDoubanMovies();
    }
}
