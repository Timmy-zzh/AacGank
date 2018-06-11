package com.timmy.baselib.base.activity;

import android.databinding.ViewDataBinding;

/**
 * 有些界面不用一开始就访问后台,直接展示内容界面
 */
public class TBaseContentActivity<DB extends ViewDataBinding> extends TBaseBindingActivity<DB> {

    @Override
    protected void initBase() {
        showContentLayout();
    }

    @Override
    protected void onRefresh() {
        showContentLayout();
    }
}
