package com.timmy.baselib.activity;

import android.content.Context;

/**
 * 描述用于载入数据的View的接口
 */
public interface ILoadingView {

    Context getContext();

    String LOADING_DEFULT_TIPS = "加载中…";
    /**
     * 使用LOADING_TYPE_DEFAULT显示loading对话框
     */
    void showLoadingDialog();

    void showLoadingDialog(String loadingTitle);

    /**
     * 隐藏所有正在显示的loading对话框
     */
    void hideLoadingDialog();

    void hideLoadingDialog(String loadingTitle);
}