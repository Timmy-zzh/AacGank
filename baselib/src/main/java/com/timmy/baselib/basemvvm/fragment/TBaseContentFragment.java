package com.timmy.baselib.basemvvm.fragment;

import android.databinding.ViewDataBinding;

public abstract class TBaseContentFragment<DB extends ViewDataBinding> extends TBaseBindingFragment<DB> {

    @Override
    protected void initBase() {
        super.initBase();
        showContentLayout();
    }

    @Override
    protected void onRefresh() {

    }
}