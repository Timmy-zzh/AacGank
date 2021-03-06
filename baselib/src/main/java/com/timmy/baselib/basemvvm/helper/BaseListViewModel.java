package com.timmy.baselib.basemvvm.helper;


import android.arch.lifecycle.ViewModel;

import com.timmy.baselib.bean.PageListResult;

import io.reactivex.Flowable;

/**
 * 所有的列表数据获取中间层
 */
public abstract class BaseListViewModel<E> extends ViewModel {

    public abstract Flowable<PageListResult<E>> getPageList(int page);

}
