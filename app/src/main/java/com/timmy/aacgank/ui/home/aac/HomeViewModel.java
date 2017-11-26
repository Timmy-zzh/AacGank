package com.timmy.aacgank.ui.home.aac;


import android.arch.lifecycle.ViewModel;

import com.timmy.aacgank.bean.base.SimpleListResult;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.http.RetrofitManager;

import io.reactivex.Flowable;

/**
 * Created by admin on 2017/11/26.
 */

public class HomeViewModel extends ViewModel {

    public Flowable<SimpleListResult<Gank>> getData(int page){
        return RetrofitManager.instance().getGankService().getWelfares("福利",page);
    }

}
