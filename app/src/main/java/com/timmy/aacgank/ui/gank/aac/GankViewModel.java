package com.timmy.aacgank.ui.gank.aac;


import com.timmy.aacgank.bean.base.BaseGankResult;
import com.timmy.aacgank.bean.gank.DailyData;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.http.HttpHelper;
import com.timmy.baselib.base.helper.BaseListViewModel;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.bean.PageListResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class GankViewModel extends BaseListViewModel<Gank> {

    public Flowable<BaseGankResult<List<Gank>>> getData(String type, int page) {
        return HttpHelper.instance().getGankService().getWelfares(type, page);
    }


    public Flowable<DailyData> getDailyData(int year, int month, int day) {
        return HttpHelper.instance().getGankService().getGankDetailData(year, month, day);
    }


    @Override
    public Flowable<BaseResult<PageListResult<Gank>>> getPageList(int page) {
        return null;
    }

    /**
     * 数据解析成我们需要的
     *
     * @param dailyData
     * @return
     */
    public List<Gank> getProjectList(DailyData dailyData) {
        List<Gank> resultList = new ArrayList<>();
        DailyData.GankCategory results = dailyData.results;
        resultList.addAll(results.Android);
        resultList.addAll(results.iOS);
        resultList.addAll(results.App);
        resultList.addAll(results.休息视频);
        resultList.addAll(results.前端);
        resultList.addAll(results.瞎推荐);
        return resultList;
    }
}
