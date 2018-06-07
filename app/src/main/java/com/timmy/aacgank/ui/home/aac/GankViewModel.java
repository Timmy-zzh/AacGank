package com.timmy.aacgank.ui.home.aac;



import com.timmy.aacgank.bean.base.BaseGankResult;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.http.HttpHelper;
import com.timmy.baselib.base.helper.BaseListViewModel;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.bean.PageListResult;

import java.util.List;

import io.reactivex.Flowable;

public class GankViewModel extends BaseListViewModel<Gank> {

    public Flowable<BaseGankResult<List<Gank>>> getData(String type , int page){
        return HttpHelper.instance().getGankService().getWelfares(type, page);
    }

    @Override
    public Flowable<BaseResult<PageListResult<Gank>>> getPageList(int page) {
        return null;
    }
}
