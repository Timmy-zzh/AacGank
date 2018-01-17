package com.timmy.adapterlib;

import android.databinding.ViewDataBinding;
import android.view.View;

class BaseBindingViewHolder<Binding extends ViewDataBinding> extends BaseViewHolder {
    private Binding mBinding;

    BaseBindingViewHolder(View view) {
        super(view);
    }

    Binding getBinding() {
        return mBinding;
    }

    void setBinding(Binding binding) {
        mBinding = binding;
    }
}
