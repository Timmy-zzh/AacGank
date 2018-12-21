package com.timmy.androidbase.recyclveriew.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义LayoutManager步骤:
 * 一 实现 generateDefaultLayoutParams()
 * 二 实现 onLayoutChildren()
 * 三 竖直滚动需要 重写canScrollVertically()和scrollVerticallyBy()
 * <p>
 * 自定义LayoutManager实现流式布局：
 */
public class FlowayoutManager extends RecyclerView.LayoutManager {
    //RecyclerView在垂直方向上滑动的距离
    private int verticalOffsetY = 0;
    private SparseArray<Rect> allItemFrams = new SparseArray<>();
    private int allHeight;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 入口方法:为界面添加Item
     * 调用时机:
     * 1 在RecyclerView初始化时，会被调用两次。
     * 2 在调用adapter.notifyDataSetChanged()时，会被调用。
     * 3 在调用setAdapter替换Adapter时,会被调用。
     * 4 在RecyclerView执行动画时，它也会被调用。
     * 即RecyclerView 初始化 、 数据源改变时 都会被调用。
     * <p>
     * 实践:
     * 为每个子控件占据的位置设置Rect
     * 摆放时判断宽度
     * 滑动处理
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (getItemCount() == 0) {
            return;
        }
        //动画执行
        if (state.isPreLayout()) {
            return;
        }
        //先清空容器
        detachAndScrapAttachedViews(recycler);
//        removeAndRecycleAllViews(recycler);//区别

        int offsetX = 0;
        int offsetY = 0;
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            //获取到指定位置子控件
            View childView = recycler.getViewForPosition(i);

            addView(childView);
            //进行测量，才能拿到宽高
            measureChildWithMargins(childView, 0, 0);
            int cHeight = getDecoratedMeasuredHeight(childView);
            int cWidth = getDecoratedMeasuredWidth(childView);
            Rect rect = allItemFrams.get(i);
            if (rect == null) {
                rect = new Rect();
            }
            if (offsetX + cWidth < getWidth()) {
                rect.set(offsetX, offsetY, offsetY + cWidth, offsetY + cHeight);
                //不需要换行
                offsetX = offsetX + cWidth;
            } else {
                //换行
                offsetX = cWidth;
                offsetY = offsetY + cHeight;

                rect.set(0, offsetY, cWidth, offsetY + cHeight);
            }
            allItemFrams.put(i, rect);
        }

        allHeight = offsetY;

        //回收不可见的子控件
        recyclerOuterView(recycler, state);
    }

    private void recyclerOuterView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //现在才开始摆放
        //清空
        detachAndScrapAttachedViews(recycler);

        //根据上一个方法中获取的位置，判断子控件是否在屏幕中，不在屏幕中回收，在的话调用子控件摆放方法
        Rect windowFram = new Rect(0, verticalOffsetY, getWidth(), verticalOffsetY + getHeight());

        for (int i = 0; i < getChildCount(); i++) {
            Rect rect = allItemFrams.get(i);
            View childView = getChildAt(i);
            if (!Rect.intersects(windowFram, rect)) {
                //回收
                removeAndRecycleView(childView, recycler);
            }
        }

        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            Rect rect = allItemFrams.get(i);
            if (Rect.intersects(windowFram, rect)) {
                //摆放
                View childView = recycler.getViewForPosition(i);
                measureChildWithMargins(childView, 0, 0);
                addView(childView);
                layoutDecorated(childView, rect.left, rect.top, rect.right, rect.bottom);
            }
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        //RecyclerView可见范围的高度
        int rvVisibleHeight = getVerticalVisibleHeight();
        int moreHeight = -rvVisibleHeight;//屏幕外的高度
        Log.d("Timmy",
                ",mTotalHeight:" + allHeight +
                        ",rvVisibleHeight:" + rvVisibleHeight +
                        ",moreHeight:" + moreHeight +
                        ",dy:" + dy +
                        ",verticalOffsetY:" + verticalOffsetY);
        if (verticalOffsetY + dy < 0) {
            //抵达上边界
            dy = -verticalOffsetY;
        } else if (allHeight > rvVisibleHeight && verticalOffsetY + dy > moreHeight) {
            //下边界
            dy = moreHeight - verticalOffsetY;
        } else {

        }
        verticalOffsetY += dy;
        offsetChildrenVertical(-dy);
        Log.d("Timmy",
                "修复后:" +
                        ",dy:" + dy +
                        ",verticalOffsetY:" + verticalOffsetY);

        recyclerOuterView(recycler, state);
        return dy;
    }

    private int getVerticalVisibleHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
