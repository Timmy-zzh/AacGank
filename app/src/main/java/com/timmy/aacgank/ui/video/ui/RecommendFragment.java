package com.timmy.aacgank.ui.video.ui;

import android.support.v7.widget.LinearLayoutManager;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ViewPaddingRecyclerBinding;
import com.timmy.aacgank.ui.simple.SimpleAdapter;
import com.timmy.baselib.base.fragment.TPageLazyBaseFragment;
import com.timmy.baselib.utils.LogUtils;

import java.util.Arrays;

public class RecommendFragment extends TPageLazyBaseFragment<ViewPaddingRecyclerBinding> {


    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        LogUtils.d("RecommendFragment  getLayoutRes  ");
        return R.layout.view_padding_recycler;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void lazyLoadData() {
        LogUtils.d("RecommendFragment  lazyLoadData  ");
        String[] stringArray = getResources().getStringArray(R.array.date);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerView.setAdapter(new SimpleAdapter(this.getContext(), Arrays.asList(stringArray)));
    }

    @Override
    protected void initBase() {
        super.initBase();
        showContentLayout();
        LogUtils.d("RecommendFragment  initBase  ");
    }
}
