package com.timmy.aacgank.ui.gank;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timmy.aacgank.C;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.ui.gank.adapter.AdapterFactory;
import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseQuickAdapter;

/**
 * Gank数据不同类型展示的界面
 * 不同之处包括
 * 1.传入的类型不同
 * 2.Adapter不同
 * 3.LayoutManager不同
 */
public class GankPagerFragment extends GankPagerMainFragment {

    public static GankPagerFragment newInstance(String gankType, int position) {
        GankPagerFragment fragment = new GankPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(C.Params, gankType);
        bundle.putInt(C.Params1, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBase() {
        super.initBase();
        getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Gank gank = (Gank) adapter.getData().get(position);
                if (gankType.contains("福利")) {

                } else {
//                    MeiziActivity.startAction(getContext(), gank.getUrl());
                }
            }
        });
    }

    @Override
    protected BaseDataBindingAdapter createAdapter(int position) {
        return AdapterFactory.getAdapter(position);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager(int position) {
        switch (position) {
            case 0:
                return new GridLayoutManager(getActivity(), 2);
            case 1:
                return new LinearLayoutManager(getContext());
        }
        return new GridLayoutManager(getActivity(), 2);
    }
}