package com.timmy.aacgank.ui.gank.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ActivityAndroidDetailBinding;
import com.timmy.baselib.base.activity.TBaseBindingActivity;
import com.timmy.baselib.base.activity.TBaseContentActivity;
import com.timmy.baselib.statusbar.StatusBarUtil;
import com.timmy.baselib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 高仿淘宝商品详情页实现界面
 * 1.顶部图片轮播
 * 2.顶部状态栏和标记栏改变背景色和指示位置
 * a.轮播图
 * b.item主要信息介绍
 * c.评价
 * d.详情页
 * e.推荐商品列表
 */
public class AndroidDetailActivity extends TBaseContentActivity<ActivityAndroidDetailBinding> {

    private Gank gank;
    private int alpha = 0;
    private AndroidDetailAdapter mAdapter;

    public static void startAction(Context context, Gank gank) {
        Intent intent = new Intent(context, AndroidDetailActivity.class);
        intent.putExtra("data", gank);
        context.startActivity(intent);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, alpha, binding.llTopLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_detail);
        showToolbar(false);
        gank = (Gank) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        mAdapter = new AndroidDetailAdapter();
        mAdapter.setGank(gank);
        List<ItemType> typeList = getDealList(gank);
        mAdapter.setData(typeList);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                LogUtils.d("AndroidDetailActivity > getSpanSize > position:" + position);
                switch (mAdapter.getItemViewType(position)) {
                    case AndroidDetailAdapter.TYPE_IMAGE:
                    case AndroidDetailAdapter.TYPE_MAIN:
                    case AndroidDetailAdapter.TYPE_REVIEW:
                    case AndroidDetailAdapter.TYPE_WEBVIEW:

                        LogUtils.d("AndroidDetailActivity > getSpanSize >position:" + position +
                                " > getSpanCount:" + layoutManager.getSpanCount());
                        return layoutManager.getSpanCount();
                    default:
                        return 1;
                }
            }
        });
        binding.recyclerView.setLayoutManager(layoutManager);

        binding.recyclerView.setAdapter(mAdapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager1 = (GridLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager1.findFirstVisibleItemPosition();
//                if (binding.tvScrollIndex != null) {
//                    binding.tvScrollIndex.setText(firstVisibleItemPosition);
//                }
                LogUtils.d("firstVisibleItemPosition:"+firstVisibleItemPosition);

//                int top = layoutManager.findViewByPosition(0).getTop();
//                double gap = (double) top / 300;
//                if (gap > 1)
//                    gap = 1;
//                int transGap = (int) (gap * 0xff) << 24;
//                int textColor = transGap | 0xffffff;
//                int statusBarColor = transGap | 0x333333;
//                binding.llTopLayout.setBackgroundColor(statusBarColor);

            }
        });
    }

    private List<ItemType> getDealList(Gank gank) {
        List<ItemType> list = new ArrayList<>();
        list.add(new ItemType(AndroidDetailAdapter.TYPE_IMAGE,R.layout.item_androiddetail_topimage));
        list.add(new ItemType(AndroidDetailAdapter.TYPE_MAIN,R.layout.item_androiddetail_main));
        list.add(new ItemType(AndroidDetailAdapter.TYPE_REVIEW,R.layout.item_androiddetail_review));
        list.add(new ItemType(AndroidDetailAdapter.TYPE_WEBVIEW,R.layout.item_androiddetail_webview));

        for (int i = 0; i < 11; i++) {
            list.add(new ItemType(AndroidDetailAdapter.TYPE_DEAL_LIST,R.layout.item_androiddetail_deallist));
        }
        return list;
    }







}
