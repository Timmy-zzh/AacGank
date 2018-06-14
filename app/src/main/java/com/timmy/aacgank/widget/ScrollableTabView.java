package com.timmy.aacgank.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timmy.aacgank.R;
import com.timmy.baselib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 水平方向可以滑动的tab
 * 布局结构为: HorizontalScrollView -> LinearLayout
 */
public class ScrollableTabView extends HorizontalScrollView {

    private LinearLayout llContainer;
    private IAdapter mAdapter;
    private OnTabItemClickListener mTabItemListener;
    private List<View> cacheViews;//视图缓存类,当某个item点击时,需要修改item的点击效果
    //    private List<Integer> selectedIndex;//视图缓存类,当某个item点击时,需要修改item的点击效果
    private int mCount;
    private int mCurrSelected = 0;
    private boolean isSelected = false;

    public ScrollableTabView(Context context) {
        this(context, null);
    }

    public ScrollableTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        llContainer = new LinearLayout(context);
        HorizontalScrollView.LayoutParams layoutParams = new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llContainer.setVerticalGravity(LinearLayout.HORIZONTAL);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        addView(llContainer, layoutParams);
    }

    public void setAdapter(IAdapter adapter) {
        if (adapter == null || this.mAdapter == adapter) {
            return;
        }
        this.mAdapter = adapter;
        mCount = mAdapter.getCount();
        cacheViews = new ArrayList<>(mCount);
        buildMenuItems();
    }

    @Override
    protected void onAttachedToWindow() {
        if (mAdapter != null) {
            buildMenuItems();
        }
        super.onAttachedToWindow();
    }

    private void buildMenuItems() {
        llContainer.removeAllViews();
        cacheViews.clear();
        TagView tagViewContainer;
        for (int i = 0; i < mCount; i++) {

            View tagView = mAdapter.getView(i, llContainer);

            tagViewContainer = new TagView(getContext());
            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tagView.getLayoutParams();
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                tagViewContainer.setLayoutParams(layoutParams);
            } else {
                MarginLayoutParams lp = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(dip2px(getContext(), 5),
                        dip2px(getContext(), 5),
                        dip2px(getContext(), 5),
                        dip2px(getContext(), 5));
                tagViewContainer.setLayoutParams(lp);
            }

            final int position = i;
            tagViewContainer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTabItemListener != null) {
                        mTabItemListener.onTabItemClick(v, position);
                    }
                    isSelected = true;
                    resetItemSelectState(position);
                }
            });

            tagViewContainer.addView(tagView);
            llContainer.addView(tagViewContainer);

//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tagView.getLayoutParams();
//            layoutParams.gravity = Gravity.CENTER_VERTICAL;
//            mAdapter.setItemView(tagView, i);
//            final int position = i;
//            tagView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mTabItemListener != null) {
//                        mTabItemListener.onTabItemClick(v, position);
//                    }
//                    resetItemSelectState(position);
//                }
//            });
//            llContainer.addView(tagView, layoutParams);
//            cacheViews.add(tagView);
        }
    }

    private void resetItemSelectState(int position) {
        LogUtils.d("mCurrSelected:" + mCurrSelected + " ,position:" + position);
        //遍历所有itemView,并设置当前点击的itemView效果
//        for (int i = 0; i < mCount; i++) {
//            View itemView = cacheViews.get(position);
//            TextView textView = itemView.findViewById(R.id.tv_tab);
//            llContainer.getChildAt(position).setSelected(i == position);

        TagView pre = (TagView) llContainer.getChildAt(mCurrSelected);
        TagView child = (TagView) llContainer.getChildAt(position);

        pre.setChecked(false);
        child.setChecked(true);

        mCurrSelected = position;
//        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnTabItemClickListener {
        void onTabItemClick(View v, int position);
    }

    public void setOnTabItemClickListener(OnTabItemClickListener listener) {
        this.mTabItemListener = listener;
    }
}
