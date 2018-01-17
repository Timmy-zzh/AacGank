package com.timmy.aacgank.ui.home;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentMyBinding;
import com.timmy.baselib.fragment.TBaseFragment;


public class MyFragment extends TBaseFragment<FragmentMyBinding> {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my;
    }
}
