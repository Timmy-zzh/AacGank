package com.timmy.aacgank.ui.home;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentMyBinding;
import com.timmy.baselib.base.fragment.TBaseBindingFragment;


public class MyFragment extends TBaseBindingFragment<FragmentMyBinding> {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onRefresh() {

    }
}
