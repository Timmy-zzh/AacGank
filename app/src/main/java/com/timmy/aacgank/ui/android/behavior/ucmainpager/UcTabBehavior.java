package com.timmy.aacgank.ui.android.behavior.ucmainpager;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.aacgank.R;

/**
 * 随着头部偏移,TabLayout的y轴位置改变
 */
public class UcTabBehavior extends CoordinatorLayout.Behavior<View> {

    private Context mContext;

    public UcTabBehavior() {
    }

    public UcTabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //头部滑动的距离
        float headerTranslationY = dependency.getTranslationY();
        int headerOffset = getHeaderOffset(dependency);
        int headerHeight = dependency.getHeight();//头部的高度
        int titleHeight = getTitleHeight();
        int tabHeight = getTabHeight();

        /**
         *  scrollY : headerHeight ~ titleHeight
         * (-headerTranslationY / headerHeight): 0~1
         */
        float scrollY = headerHeight + headerTranslationY - (-headerTranslationY / headerOffset) * tabHeight;
        if (scrollY < titleHeight) {
            scrollY = titleHeight;
        }
        child.setY(scrollY);
        return true;
    }

    // 顶部Title高度
    private int getTitleHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.title_height);
    }

    private int getTabHeight() {
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
