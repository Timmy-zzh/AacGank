package com.timmy.aacgank.ui.movie;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.movie.BaseDoubanResult;
import com.timmy.aacgank.bean.movie.DoubanMovie;
import com.timmy.aacgank.ui.movie.aac.MovieViewModel;
import com.timmy.aacgank.ui.movie.adapter.MovieAdapter;
import com.timmy.adapterlib.BaseQuickAdapter;
import com.timmy.baselib.base.fragment.TBaseBindingFragment;
import com.timmy.baselib.bean.PageList;
import com.timmy.baselib.bean.PageListResult;
import com.timmy.baselib.databinding.ActivityRefreshListBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;

/**
 * 豆瓣电影
 */
public class MovieFragment extends TBaseBindingFragment<ActivityRefreshListBinding> {

    private MovieAdapter mAdapter;
    private MovieViewModel viewModel;

//    public static MovieFragment newInstance() {
//        MovieFragment fragment = new MovieFragment();
//        return fragment;
//    }

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
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
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
        bindSubscribe(viewModel.getHotMovies()
                , new AConsumer<BaseDoubanResult<DoubanMovie>>() {
                    @Override
                    public void onGetResult(BaseDoubanResult<DoubanMovie> result) {
                        if (refresh) {
                            binding.swipeRefreshLayout.setRefreshing(false);
                        }
                        if (result != null && result.subjects != null && !result.subjects.isEmpty()) {
                            showContentLayout();
                            mAdapter.setNewData(result.subjects);
                        } else {
                            showEmptyLayout();
                        }
                    }
                }
                , new BConsumer() {
                    @Override
                    public void onErrorResult(Throwable throwable) {
                        super.onErrorResult(throwable);
                        if (refresh) {
                            binding.swipeRefreshLayout.setRefreshing(false);
                        }
                        showErrorLayout();
                    }
                });
    }
}
