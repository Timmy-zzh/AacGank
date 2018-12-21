package com.timmy.aacgank.ui.multimedia.ui;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentVideoHotBinding;
import com.timmy.baselib.basemvvm.fragment.TPageLazyBaseFragment;
import com.timmy.baselib.utils.LogUtils;

public class HotVideoFragment extends TPageLazyBaseFragment<FragmentVideoHotBinding> {

    public static HotVideoFragment newInstance() {
        HotVideoFragment fragment = new HotVideoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video_hot;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void lazyLoadData() {
        LogUtils.d("HotVideoFragment  lazyLoadData  ");
    }

    @Override
    protected void initBase() {
        super.initBase();
        showContentLayout();
        LogUtils.d("HotVideoFragment  initBase  ");
    }
}
