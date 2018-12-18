package com.timmy.aacgank.ui.gank;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.timmy.baselib.C;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.ui.gank.adapter.AndroidAdapter;
import com.timmy.aacgank.ui.gank.adapter.WelfareAdapter;
import com.timmy.aacgank.ui.gank.android.AndroidDetailActivity;
import com.timmy.baselib.adapterlib.BaseDataBindingAdapter;
import com.timmy.baselib.adapterlib.BaseQuickAdapter;

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
                switch (gankType){
                    case "福利":
                        WelfareDetailActivity.startAction(getActivity(),view,gank);
                        break;
                    case "Android":
                        AndroidDetailActivity.startAction(getActivity(),gank);
                        break;
                }
            }
        });
    }

    @Override
    protected BaseDataBindingAdapter createAdapter(int position) {
        switch (position) {
            case 0:
                return new WelfareAdapter();
            case 1:
                return new AndroidAdapter();
        }
        return new AndroidAdapter();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager(int position) {
        switch (position) {
            case 0:
                return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            case 1:
                return new LinearLayoutManager(getContext());
        }
//        return new GridLayoutManager(getContext(),2);
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }
}