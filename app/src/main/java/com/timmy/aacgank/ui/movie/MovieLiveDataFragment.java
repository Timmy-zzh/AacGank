package com.timmy.aacgank.ui.movie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.movie.BaseDoubanResult;
import com.timmy.aacgank.bean.movie.DoubanMovie;
import com.timmy.aacgank.ui.movie.aac.DoubanViewModel;
import com.timmy.aacgank.ui.movie.adapter.MovieAdapter;
import com.timmy.baselib.basemvvm.fragment.TBaseBindingFragment;
import com.timmy.baselib.databinding.ActivityRefreshListBinding;

import java.util.List;

/**
 * 豆瓣电影
 */
public class MovieLiveDataFragment extends TBaseBindingFragment<ActivityRefreshListBinding> {

    private MovieAdapter mAdapter;
    private DoubanViewModel viewModel;

    public static MovieLiveDataFragment newInstance() {
        MovieLiveDataFragment fragment = new MovieLiveDataFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_refresh_list;
    }

    @Override
    protected void onRefresh() {
        subscribeUI(false);
    }

    @Override
    protected void initBase() {
        initView();
        viewModel = ViewModelProviders.of(this).get(DoubanViewModel.class);
        subscribeUI(false);
    }

    private void initView() {
        mAdapter = new MovieAdapter();
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(true);
                //重新获取数据
                mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                subscribeUI(true);
            }
        });
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.recyclerView.setAdapter(mAdapter);
    }

    private void subscribeUI(final boolean refresh) {
        viewModel.getDoubanMovies().observe(this, new Observer<BaseDoubanResult<DoubanMovie>>() {
            @Override
            public void onChanged(@Nullable BaseDoubanResult<DoubanMovie> result) {
                showContentLayout();
                List<DoubanMovie> subjects = result.subjects;
                mAdapter.setNewData(subjects);
            }
        });
    }
}
