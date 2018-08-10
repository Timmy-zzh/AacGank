package com.timmy.aacgank.ui.android.behavior.ucmainpager;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.aacgank.R;

/**
 * 作用在顶部Title控件上:
 * 随着头部控件的滑动而位置变化
 */
public class UcTitleBehavior extends CoordinatorLayout.Behavior<View> {

    private Context mContext;

    public UcTitleBehavior() {
    }

    public UcTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    /**
     *
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //头部滑动的距离
        float headerTranslationY = dependency.getTranslationY();
        int headerOffset = getHeaderOffset(dependency);//头部的高度
        int titleHeight = getTitleHeight();

        /**
         * 默认刚开始 scrollY : -titleHeight ~ 0
         * (-headerTranslationY / headerHeight): 0~1
         */
        float scrollY = -(1 - (-headerTranslationY / headerOffset)) * titleHeight;
        child.setY(scrollY);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    // 顶部Title高度
    private int getTitleHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.tab_height);
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.header;
    }

    /**
     * 头部滑动的距离
     */
    private int getHeaderOffset(View headerView) {
        return headerView.getHeight()
                - mContext.getResources().getDimensionPixelOffset(R.dimen.tab_height)
                - mContext.getResources().getDimensionPixelOffset(R.dimen.title_height);
    }
}
