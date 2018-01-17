package com.timmy.aacgank.ui.home.aac;

import com.timmy.aacgank.bean.base.BaseGankResult;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.http.HttpHelper;
import com.timmy.aacgank.http.service.GankService;
import com.timmy.baselib.activity.BaseListViewModel;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.bean.PageListResult;
import com.timmy.baselib.http.RetrofitManager;

import java.util.List;

import io.reactivex.Flowable;

public class HomeViewModel extends BaseListViewModel<Gank> {

    public Flowable<BaseGankResult<List<Gank>>> getData(int page){
        Flowable<BaseGankResult<List<Gank>>> resultFlowable = RetrofitManager.instance().create(GankService.class).getWelfares("福利", page);
        return resultFlowable;
    }

    @Override
    public Flowable<BaseResult<PageListResult<Gank>>> getPageList(int page) {
        return null;
    }
}
