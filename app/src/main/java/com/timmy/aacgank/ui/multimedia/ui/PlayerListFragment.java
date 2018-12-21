package com.timmy.aacgank.ui.multimedia.ui;

import android.support.v7.widget.LinearLayoutManager;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ViewPaddingRecyclerBinding;
import com.timmy.baselib.simple.SimpleAdapter;
import com.timmy.baselib.basemvvm.fragment.TPageLazyBaseFragment;
import com.timmy.baselib.utils.LogUtils;

import java.util.Arrays;

/**
 * 播单
 */
public class PlayerListFragment extends TPageLazyBaseFragment<ViewPaddingRecyclerBinding> {

    public static PlayerListFragment newInstance() {
        PlayerListFragment fragment = new PlayerListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_padding_recycler;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void lazyLoadData() {
        LogUtils.d("PlayerListFragment  lazyLoadData  ");
        String[] stringArray = getResources().getStringArray(R.array.date);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerView.setAdapter(new SimpleAdapter(this.getContext(), Arrays.asList(stringArray)));
    }

    @Override
    protected void initBase() {
        super.initBase();
        showContentLayout();
    }
}
