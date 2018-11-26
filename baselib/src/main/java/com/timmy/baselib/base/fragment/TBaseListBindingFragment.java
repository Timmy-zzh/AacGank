package com.timmy.baselib.base.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.timmy.baselib.R;
import com.timmy.baselib.adapterlib.BaseDataBindingAdapter;
import com.timmy.baselib.adapterlib.BaseQuickAdapter;
import com.timmy.baselib.base.helper.BaseListViewModel;
import com.timmy.baselib.bean.PageList;
import com.timmy.baselib.bean.PageListResult;
import com.timmy.baselib.databinding.ActivityRefreshListBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;

import io.reactivex.Flowable;


public abstract class TBaseListBindingFragment<E, VM extends BaseListViewModel> extends TBaseBindingFragment<ActivityRefreshListBinding> {

    private VM viewModel;
    private int page = 1;
    private BaseDataBindingAdapter mAdapter;

    protected abstract VM createViewModel();

    protected abstract BaseDataBindingAdapter createAdapter();

    protected abstract Flowable<PageListResult<E>> getPageList(int page);

    public BaseDataBindingAdapter getAdapter() {
        return mAdapter;
    }

    public VM getViewModel() {
        return viewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_refresh_list;
    }

    @Override
    protected void initBase() {
        super.initBase();
        mAdapter = createAdapter();
        viewModel = createViewModel();
        initView();
        subscribeUI(false);
    }

    private void initView() {
        binding.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(true);
                //重新获取数据
                page = 1;
                mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                subscribeUI(true);
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(mAdapter);
        //加载更多
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                subscribeUI(false);
            }
        }, binding.recyclerView);
    }

    @Override
    protected void onRefresh() {
        subscribeUI(false);
    }

    private void subscribeUI(final boolean refresh) {
        bindSubscribe(getPageList(page)
                , new AConsumer<PageListResult<E>>() {
                    @Override
                    public void onGetResult(PageListResult<E> result) {
                        if (refresh) {
                            binding.swipeRefreshLayout.setRefreshing(false);
                        }
                        if (result != null && result.getData() != null
                                && result.getData().getData() != null && !result.getData().getData().isEmpty()) {
                            showContentLayout();
                            handleData(result.getData());
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
                        if (page > 1) {
                            page--;
                            mAdapter.loadMoreFail();
                        } else {
                            showErrorLayout();
                        }
                    }
                });
    }

    private void handleData(PageList<E> pageList) {
        if (page <= 1) {
            mAdapter.setNewData(pageList.getData());
            if (pageList.isHasNext()) {
                mAdapter.setEnableLoadMore(true);
            } else {
                mAdapter.setEnableLoadMore(false);
                mAdapter.loadMoreEnd(false);
            }
        } else {
            mAdapter.addData(pageList.getData());
            mAdapter.loadMoreComplete();//加载更多完成
            if (pageList.isHasNext()) {//是否有下一页
                mAdapter.setEnableLoadMore(pageList.isHasNext());
            } else {
                mAdapter.loadMoreEnd();//没有更多数据了
            }
        }
    }
}
