package com.timmy.aacgank.ui.gank;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;


import com.timmy.aacgank.C;
import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.base.BaseGankResult;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.ui.home.aac.GankViewModel;
import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseQuickAdapter;
import com.timmy.baselib.base.fragment.DjPageLazyBaseFragment;
import com.timmy.baselib.databinding.ActivityRefreshListBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;

import java.util.List;

/**
 * Gank数据不同类型展示的界面
 * 不同之处包括
 * 1.传入的类型不同
 * 2.Adapter不同
 * 3.LayoutManager不同
 */
public abstract class GankPagerMainFragment extends DjPageLazyBaseFragment<ActivityRefreshListBinding> {

    private GankViewModel viewModel;
    private int page = 1;
    private BaseDataBindingAdapter adapter;
    public String gankType;
    private int position;

    protected abstract BaseDataBindingAdapter createAdapter(int position);

    protected abstract RecyclerView.LayoutManager createLayoutManager(int position);

    public BaseDataBindingAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_refresh_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        gankType = arguments.getString(C.Params);
        position = arguments.getInt(C.Params1);
    }

    @Override
    protected void lazyLoadData() {
        subscribeUI(false);
    }

    @Override
    protected void initBase() {
        super.initBase();
        showLoadingLayout();
        adapter = createAdapter(position);
        viewModel = ViewModelProviders.of(this).get(GankViewModel.class);
        initView();
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
        binding.recyclerView.setLayoutManager(createLayoutManager(position));
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
        bindSubscribe(viewModel.getData(gankType, page)
                , new AConsumer<BaseGankResult<List<Gank>>>() {
                    @Override
                    public void onGetResult(BaseGankResult<List<Gank>> result) {
                        handleData(refresh, result);
                    }
                }
                , new BConsumer() {
                    @Override
                    public void onErrorResult(Throwable throwable) {
                        super.onErrorResult(throwable);
                        handleError(refresh, throwable);
                    }
                });
    }

    private void handleError(boolean refresh, Throwable throwable) {
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

    private void handleData(boolean refresh, BaseGankResult<List<Gank>> result) {
        if (refresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
        }
        if (result != null && result.getData() != null && !result.getData().isEmpty()) {
            showContentLayout();
            if (page <= 1) {
                adapter.setNewData(result.getData());
            } else {
                adapter.addData(result.getData());
                adapter.loadMoreComplete();//加载更多完成
            }
            adapter.notifyDataSetChanged();
            //还有下一页
            adapter.setEnableLoadMore(true);
        } else if (page > 1) {
            //第n页(n>1)没有数据
            adapter.loadMoreComplete();//加载更多完成
            adapter.loadMoreEnd();//没有更多数据了
        } else {
            showEmptyLayout();
        }
    }
}