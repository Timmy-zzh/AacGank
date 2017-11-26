package com.timmy.aacgank.ui.home;

import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.base.BaseFragment;

/**
 * Created by admin on 2017/11/26.
 */

public class MyFragment extends BaseFragment {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my;
    }
}
