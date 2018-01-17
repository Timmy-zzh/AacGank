package com.timmy.aacgank.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.base.BaseGankResult;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.ui.home.aac.HomeViewModel;
import com.timmy.aacgank.ui.home.adapter.HomeAdapter;
import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseQuickAdapter;
import com.timmy.baselib.databinding.ActivityRefreshListBinding;
import com.timmy.baselib.fragment.TBaseFragment;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;

import java.util.List;

/**
 * 使用正常方式进行处理
 * 1.获取数据,使用retrofit
 * 2.展示recyclerview
 */
public class HomeFragment extends TBaseFragment<ActivityRefreshListBinding> {

    private HomeViewModel viewModel;
    private int page = 1;
    private BaseDataBindingAdapter adapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.activity_refresh_list;
    }

    @Override
    protected void initBase() {
        super.initBase();
        adapter = new HomeAdapter();
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
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

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
        bindSubscribe(viewModel.getData(page)
                , new AConsumer<BaseGankResult<List<Gank>>>() {
                    @Override
                    public void onGetResult(BaseGankResult<List<Gank>> result) {
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
}