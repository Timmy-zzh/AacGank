package com.timmy.androidbase.behavior.ucmainpager;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.androidbase.R;
import com.timmy.androidbase.behavior.helper.HeaderScrollingViewBehavior;

import java.util.List;

/**
 * 是什么:UC主页的Content部分的使用的Behavior
 * 作用:
 * 1.监听头部变化,改变内容部分控件的位置
 */
public class UcContentBehavior extends HeaderScrollingViewBehavior {

    private Context mContext;
    private String TAG = this.getClass().getSimpleName();

    public UcContentBehavior() {
    }

    public UcContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    /**
     * 监听的Header部分的id进行判断
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    /**
     * Header控件改变时,回调的方法:
     * 主要是改变Content部分的Y方向位置
     * <p>
     * version2.0:
     * 当添加了TabLayout后,y值是不一样的计算方式
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = dependency.getTranslationY();//头部移动的距离
        int headerHeight = dependency.getHeight();
        int headerOffset = getHeaderOffset(dependency);
//        Log.d(TAG, "onDependentViewChanged--translationY:" + translationY);
        float y = headerHeight + translationY;
        if (y < headerOffset) {//头部内容全部滑出屏幕
            y = headerOffset;
        }
        child.setY(y);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    /**
     * 找到第一个被依赖对象
     */
    @Override
    protected View findFirstDependency(List<View> views) {
        for (View view : views) {
            if (isDependOn(view)) {
                return view;
            }
        }
        return null;
    }

    @Override
    protected int getScrollRange(View v) {
        return super.getScrollRange(v);
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
