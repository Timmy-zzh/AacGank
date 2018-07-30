package com.timmy.aacgank.ui.movie.layoutManager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义LayoutManager,主要实现
 * 1.对RecyclerView子控件的排放
 * 2.滑动
 * 3.复用回收功能  缓存
 * <p>
 * 属性控制:
 * 1.方向
 */
public class MyLayoutManager extends RecyclerView.LayoutManager {

    private int mTotalHeight; //整个RecyclerView高度
    private int mMoveDistance;// 手指整体滑动的距离
    private SparseArray<Rect> itemRects = null;

    /**
     * 1.设置RecyclerView的子item控件的LayoutParams
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 2.核心:相当于自定义ViewGroup的onLayotu()方法
     * 操作步骤包括:
     * 1.添加子View -- addView(scrap);
     * 2.测量子View --  measureChildWithMargins(scrap,0,0);
     * <p>
     * 4.复用回收功能
     * 子View添加只处理可见范围条目个数
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //每次Item摆放时,都需要确定位置
        int offsetY = 0;

        for (int i = 0; i < getItemCount(); i++) {
            View scrap = recycler.getViewForPosition(i);//从缓存中获取
            addView(scrap);
            //子控件自己测量
            measureChildWithMargins(scrap, 0, 0);

            //获取到单个子View自己的宽+分割线宽之和
            int perItemWidth = getDecoratedMeasuredWidth(scrap);
            int perItemHeight = getDecoratedMeasuredHeight(scrap);

            layoutDecorated(scrap, 0, offsetY, perItemWidth, offsetY + perItemHeight);
            offsetY += perItemHeight;

        }
        mTotalHeight = offsetY;
    }

    /**
     * 3.1 滑动能力
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * 3.2 滑动距离
     * 滑动到上下边界的处理
     *
     * @param dy       代表手指在屏幕上每次滑动的位移,canScrollVertically()方法返回true时才有意义
     *                 手指由下往上滑时，dy值为 >0 的。
     *                 手指由上往下滑时，dy值为 <0 的。
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //RecyclerView可见范围的高度
        int rvVisibleHeight = getVerticalVisibleHeight();
        int moreHeight = mTotalHeight - rvVisibleHeight;//屏幕外的高度
        Log.d("Timmy",
                ",mTotalHeight:" + mTotalHeight +
                        ",rvVisibleHeight:" + rvVisibleHeight +
                        ",moreHeight:" + moreHeight +
                        ",dy:" + dy +
                        ",mMoveDistance:" + mMoveDistance);
        if (mMoveDistance + dy < 0) {
            //抵达上边界
            dy = -mMoveDistance;
        } else if (mTotalHeight > rvVisibleHeight && mMoveDistance + dy > moreHeight) {
            //下边界
            dy = moreHeight - mMoveDistance;
        } else {

        }
        mMoveDistance += dy;
        offsetChildrenVertical(-dy);
        Log.d("Timmy",
                "修复后:" +
                        ",dy:" + dy +
                        ",mMoveDistance:" + mMoveDistance);

//        handleRecycle(recycler,state);
        return dy;

        //RecyclerView的方法
//        offsetChildrenVertical(-1 * dy);
        //最后调用scrollBy()方法
//        return super.scrollVerticallyBy(dy, recycler, state);
    }

    /**
     * 处理item回收
     *
     * @param recycler
     * @param state
     */
    private void handleRecycle(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //状态判断

        //
        detachAndScrapAttachedViews(recycler);

        int childCount = getChildCount();
        Rect visibleRect = getVisibleArea();
        for (int i = 0; i < getItemCount(); i++) {
            View itemView = recycler.getViewForPosition(i);
            Rect rect = itemRects.get(i);
            if (Rect.intersects(visibleRect, rect)) {
                addView(itemView);
                measureChildWithMargins(itemView, 0, 0);
                layoutDecorated(itemView, rect.left, rect.top - mMoveDistance,
                        rect.right, rect.bottom - mMoveDistance);
            } else {
                removeAndRecycleView(itemView, recycler);
                Log.e("handleRecycle", "回收喽");
            }
        }

    }

    /**
     * 获取RecyclerView可见范围区域
     *
     * @return
     */
    private Rect getVisibleArea() {
        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mMoveDistance, getWidth() + getPaddingRight(), getVerticalVisibleHeight() + mMoveDistance);
        return result;
    }

    private int getVerticalVisibleHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
