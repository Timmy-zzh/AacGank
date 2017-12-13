package com.timmy.aacgank.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.base.SimpleListResult;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.ui.base.BaseFragment;
import com.timmy.aacgank.ui.home.aac.HomeViewModel;
import com.timmy.aacgank.ui.home.adapter.HomeAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 使用正常方式进行处理
 * 1.获取数据,使用retrofit
 * 2.展示recyclerview
 */
public class HomeFragment extends BaseFragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private int page = 1;
    private HomeAdapter homeAdapter;
    private HomeViewModel viewModel;
    protected final CompositeDisposable mDisposable = new CompositeDisposable();

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        init();
    }

    private void init() {
        initView();
        viewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        subscribeUI(true, page);
    }

    private void initView() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                //重新获取数据
                subscribeUI(false, page);
            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        homeAdapter = new HomeAdapter(getActivity());
    }

    private void subscribeUI(boolean isInit, int page) {
        mDisposable.add(viewModel.getData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SimpleListResult<Gank>>() {
                               @Override
                               public void accept(SimpleListResult<Gank> result) throws Exception {
                                   if (result != null && result.isSuccess()) {
                                       handleData(result.getData());
                                   }
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }));
    }

    private void handleData(List<Gank> gankList) {
        homeAdapter.setDatas(gankList);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
    }
}
