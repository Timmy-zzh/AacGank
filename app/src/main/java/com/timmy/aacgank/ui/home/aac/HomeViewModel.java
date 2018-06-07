package com.timmy.aacgank.ui.home.aac;

import com.timmy.aacgank.bean.base.BaseGankResult;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.http.HttpHelper;
import com.timmy.baselib.base.helper.BaseListViewModel;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.bean.PageListResult;
import com.timmy.baselib.http.RetrofitManager;

import java.util.List;

import io.reactivex.Flowable;

public class HomeViewModel extends BaseListViewModel<Gank> {

    public Flowable<BaseGankResult<List<Gank>>> getData(int page){
        return HttpHelper.instance().getGankService().getWelfares("福利", page);
    }

    @Override
    public Flowable<BaseResult<PageListResult<Gank>>> getPageList(int page) {
        return null;
    }
}
