package com.timmy.baselib.base.fragment;

import android.databinding.ViewDataBinding;

public abstract class DjBaseContentFragment<DB extends ViewDataBinding> extends DjBaseBindingFragment<DB> {

    @Override
    protected void initBase() {
        super.initBase();
        showContentLayout();
    }

    @Override
    protected void onRefresh() {

    }
}