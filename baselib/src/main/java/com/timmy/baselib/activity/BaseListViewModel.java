package com.timmy.baselib.activity;


import android.arch.lifecycle.ViewModel;

import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.bean.PageListResult;

import io.reactivex.Flowable;

/**
 * 所有的列表数据获取中间层
 */
public abstract class BaseListViewModel<E> extends ViewModel {

    public abstract Flowable<BaseResult<PageListResult<E>>> getPageList(int page);

}
