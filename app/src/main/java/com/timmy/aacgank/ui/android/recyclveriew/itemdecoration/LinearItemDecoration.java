package com.timmy.aacgank.ui.android.recyclveriew.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 分割线设置的属性有
 * 1.分割线颜色
 * 2.分割线间隔大小 px
 * 3.分割线方向
 * 4.是否需要顶部和尾部
 * 5.分割线距离屏幕的间隔
 * <p>
 * 使用Builder模式进行数据设置
 * row    行
 * column 列
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int color;//颜色
    private int spaceDistance = 10;//间隔距离 px
    private boolean showHeader;//是否展示头部分割线
    private boolean showFooter;//尾部风格线
    private int mOrientation;//分割线展示方向
    private String tag = this.getClass().getSimpleName();

    public LinearItemDecoration(int color, int spaceDistance, boolean showHeader, boolean showFooter, int mOrientation) {
        this.color = color;
        this.spaceDistance = spaceDistance;
        this.showHeader = showHeader;
        this.showFooter = showFooter;
        this.mOrientation = mOrientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getAdapter().getItemCount();
        int position = parent.getChildAdapterPosition(view);
//        Log.d(tag, "position:" + position + ",childCount:" + childCount);
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        //先判断方向
        if (mOrientation == VERTICAL) {
            //垂直方向,默认左右边距为0
            left = 0;
            right = 0;
            if (isFirstColumn(childCount, position)) {  //第一行
                if (showHeader) {//展示头部
                    top = spaceDistance;
                    bottom = spaceDistance;
                } else {
                    top = 0;
                    bottom = spaceDistance;
                }
            } else if (isLastColumn(childCount, position)) { //最后一行
                if (showFooter) {
                    top = 0;
                    bottom = spaceDistance;
                } else {
                    top = 0;
                    bottom = 0;
                }
            } else {   //中间item
                top = 0;
                bottom = spaceDistance;
            }
        } else if (mOrientation == HORIZONTAL) {

        }

        outRect.set(left, top, right, bottom);
    }

    /**
     * 是否是第一行
     */
    private boolean isFirstColumn(int childCount, int position) {
        return position == 0;
    }

    /**
     * 是否是最后一行
     */
    private boolean isLastColumn(int childCount, int position) {
        return position == childCount - 1;
    }


}
