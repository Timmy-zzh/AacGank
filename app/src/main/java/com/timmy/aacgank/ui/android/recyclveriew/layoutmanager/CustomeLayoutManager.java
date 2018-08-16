package com.timmy.aacgank.ui.android.recyclveriew.layoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 自定义LayoutManager步骤:
 * 一 实现 generateDefaultLayoutParams()
 * 二 实现 onLayoutChildren()
 * 三 竖直滚动需要 重写canScrollVertically()和scrollVerticallyBy()
 */
public class CustomeLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 入口方法:为界面添加Item
     * 调用时机:
     * 1 在RecyclerView初始化时，会被调用两次。
     * 2 在调用adapter.notifyDataSetChanged()时，会被调用。
     * 3 在调用setAdapter替换Adapter时,会被调用。
     * 4 在RecyclerView执行动画时，它也会被调用。
     * 即RecyclerView 初始化 、 数据源改变时 都会被调用。
     *
     * 实践:
     * 一:在界面可见范围内添加子View
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);




    }
}
