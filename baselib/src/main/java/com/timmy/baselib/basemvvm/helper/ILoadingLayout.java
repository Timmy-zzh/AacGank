package com.timmy.baselib.basemvvm.helper;

/**
 * 描述用于载入数据的View的接口
 */
public interface ILoadingLayout {

    void showLoadingLayout();

    void showEmptyLayout();

    void showErrorLayout();

    void showContentLayout();
}