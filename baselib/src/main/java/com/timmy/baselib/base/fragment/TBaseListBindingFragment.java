package com.timmy.baselib.base.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseQuickAdapter;
import com.timmy.baselib.R;
import com.timmy.baselib.base.helper.BaseListViewModel;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.bean.PageListResult;
import com.timmy.baselib.databinding.ActivityRefreshListBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;

import io.reactivex.Flowable;


public abstract class TBaseListBindingFragment<E, VM extends BaseListViewModel> extends TBaseBindingFragment<ActivityRefreshListBinding> {

    private VM viewModel;
    private int page = 1;
    private BaseDataBindingAdapter adapter;

    protected abstract VM createViewModel();

    protected abstract BaseDataBindingAdapter createAdapter();

    protected abstract Flowable<BaseResult<PageListResult<E>>> getPageList(int page);

    public BaseDataBindingAdapter getAdapter() {
        return adapter;
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
        adapter = createAdapter();
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
                adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                subscribeUI(true);
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        binding.recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,10,AppUtils.getColor(R.color.T_F2)));
        binding.recyclerView.setAdapter(adapter);
        //加载更多
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
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
                , new AConsumer<BaseResult<PageListResult<E>>>() {
                    @Override
                    public void onGetResult(BaseResult<PageListResult<E>> result) {
                        if (refresh) {
                            binding.swipeRefreshLayout.setRefreshing(false);
                        }
//                        Toast.toastErrorResult(result);
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
                            adapter.loadMoreFail();
                        } else {
                            showErrorLayout();
                        }
                    }
                });
    }

    private void handleData(PageListResult<E> pageListResult) {
        if (page <= 1) {
            adapter.setNewData(pageListResult.getData());
            if (pageListResult.isHasNext()) {
                adapter.setEnableLoadMore(true);
            } else {
                adapter.setEnableLoadMore(false);
                adapter.loadMoreEnd(false);
            }
        } else {
            adapter.addData(pageListResult.getData());
            adapter.loadMoreComplete();//加载更多完成
            if (pageListResult.isHasNext()) {//是否有下一页
                adapter.setEnableLoadMore(pageListResult.isHasNext());
            } else {
                adapter.loadMoreEnd();//没有更多数据了
            }
        }
    }
}
