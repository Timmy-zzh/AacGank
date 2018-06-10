package com.timmy.aacgank.ui.gank;

import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.DailyData;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ActivityWelfareDetailBinding;
import com.timmy.aacgank.ui.gank.aac.GankViewModel;
import com.timmy.aacgank.ui.gank.adapter.WelfareDetailAdapter;
import com.timmy.aacgank.ui.other.WebViewActivity;
import com.timmy.aacgank.util.DateUtil;
import com.timmy.adapterlib.BaseQuickAdapter;
import com.timmy.baselib.base.activity.DjBaseContentActivity;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.image.ImageUtil;
import com.timmy.baselib.utils.LogUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 1.共享元素开启界面
 * 2.
 */
public class WelfareDetailActivity extends DjBaseContentActivity<ActivityWelfareDetailBinding> {

    private Gank gank;
    private GankViewModel viewModel;
    private WelfareDetailAdapter mAdapter;

    public static void startAction(Activity activity, View shareView, Gank gank) {
        Intent intent = new Intent(activity, WelfareDetailActivity.class);
        intent.putExtra("data", gank);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //添加切换动画，MD风格的
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    shareView,
                    activity.getResources().getString(R.string.image_transition));
            ActivityCompat.startActivity(activity, intent, compat.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_detail);
        setStatusBarConfig();
        showToolbar(false);
        initAppBar();
        initView();
        subscribeUI();
    }

    private void setStatusBarConfig() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0+
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //设置系统UI元素可见性
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4-5.0
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View view = this.getWindow().getDecorView();
        Rect frame = new Rect();
        view.getWindowVisibleDisplayFrame(frame);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) binding.toolbar.getLayoutParams();
        layoutParams.setMargins(0, frame.top, 0, 0);
        binding.toolbar.setLayoutParams(layoutParams);
    }


    private void initAppBar() {
        binding.toolbar.setNavigationIcon(R.mipmap.ic_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        WelfareDetailActivity.this.finishAfterTransition();
                    } else {
                        WelfareDetailActivity.this.finish();
                    }
                } catch (Exception e) {
                    WelfareDetailActivity.this.finish();
                }
            }
        });

    }

    private void initView() {
        gank = (Gank) getIntent().getSerializableExtra("data");
        viewModel = ViewModelProviders.of(this).get(GankViewModel.class);
        gank = (Gank) getIntent().getSerializableExtra("data");
        ImageUtil.loadImage(this, gank.getUrl(), binding.ivImage);

        binding.toolbar.setTitle(DateUtil.getStringDate(gank.getPublishedAt()));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WelfareDetailAdapter();
        binding.recyclerView.setAdapter(mAdapter);

//         mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//             @Override
//             public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//             }
//         });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Gank gank = mAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_desc:
                        WebViewActivity.startAction(getContext(), gank.getUrl());
                        break;
                }
            }
        });
    }

    private void subscribeUI() {
        Date date = DateUtil.stringToDate(gank.getPublishedAt());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        bindSubscribe(viewModel.getDailyData(year, month, day)
                , new AConsumer<DailyData>() {
                    @Override
                    public void onGetResult(DailyData dailyData) {
                        LogUtils.d(dailyData.toString());
                        if (dailyData != null) {
                            List<Gank> resultList = viewModel.getProjectList(dailyData);
                            mAdapter.setNewData(resultList);
                        } else {
                            showEmptyLayout();
                        }
                    }
                });
    }
}
