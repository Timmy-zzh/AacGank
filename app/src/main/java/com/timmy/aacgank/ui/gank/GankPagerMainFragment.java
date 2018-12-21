package com.timmy.aacgank.ui.gank;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


import com.timmy.baselib.C;
import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.base.BaseGankResult;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.ui.gank.aac.GankViewModel;
import com.timmy.baselib.adapterlib.BaseDataBindingAdapter;
import com.timmy.baselib.adapterlib.BaseQuickAdapter;
import com.timmy.baselib.basemvvm.fragment.TPageLazyBaseFragment;
import com.timmy.baselib.databinding.ActivityRefreshListBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;
import com.timmy.baselib.image.ImageUtil;

import java.util.List;

import io.reactivex.functions.Function;

/**
 * Gank数据不同类型展示的界面
 * 不同之处包括
 * 1.传入的类型不同
 * 2.Adapter不同
 * 3.LayoutManager不同
 */
public abstract class GankPagerMainFragment extends TPageLazyBaseFragment<ActivityRefreshListBinding> {

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
        RecyclerView.LayoutManager layoutManager = createLayoutManager(position);
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        }
        binding.recyclerView.setLayoutManager(layoutManager);
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

    /**
     * 获取Gank数据,
     * 1.如果是福利图片数据,需要先获取到图片的真实宽高,然后进行设置
     * 2.在RxJava获取列表数据后进行二次处理
     *
     * @param refresh
     */
    private void subscribeUI(final boolean refresh) {
        bindSubscribe(
                viewModel.getData(gankType, page)
                        .map(new Function<BaseGankResult<List<Gank>>, List<Gank>>() {
                            @Override
                            public List<Gank> apply(BaseGankResult<List<Gank>> gankResult) throws Exception {
                                if (gankResult != null && gankResult.getData() != null) {
                                    if (!gankType.equals("福利")){
                                        return gankResult.getData();
                                    }
                                    List<Gank> gankList = gankResult.getData();
                                    //获取图片的真实宽高再设置回去
                                    for (Gank gank : gankList) {
                                        Bitmap bitmap = ImageUtil.loadBitmap(getContext(), gank.getUrl());
                                        if (bitmap != null) {
                                            gank.setSrcWidth(bitmap.getWidth());
                                            gank.setSrcHeight(bitmap.getHeight());
                                        }
                                    }
                                    return gankList;
                                }
                                return null;
                            }
                        })
                , new AConsumer<List<Gank>>() {
                    @Override
                    public void onGetResult(List<Gank> ganks) {
                        handleData(refresh, ganks);
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

    private void handleData(boolean refresh, List<Gank> result) {
        if (refresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
        }
        if (result != null && !result.isEmpty()) {
            showContentLayout();
            if (page <= 1) {
                adapter.setNewData(result);
            } else {
                adapter.addData(result);
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