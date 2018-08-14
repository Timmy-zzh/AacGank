package com.timmy.aacgank.ui.my;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.timmy.aacgank.C;
import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.my.MainModel;
import com.timmy.aacgank.bean.my.MainTag;
import com.timmy.aacgank.databinding.ViewRecyclerViewBinding;
import com.timmy.baselib.base.fragment.TPageLazyBaseFragment;

import java.util.ArrayList;
import java.util.List;

public class TechnologyPageFragment extends TPageLazyBaseFragment<ViewRecyclerViewBinding> {

    private String mPage;
    private RecyclerView.LayoutManager layoutManager;
    private MainContentAdapter adapter;
    private List<MainModel> pageListOne = new ArrayList<>();
    private List<MainModel> pageListTwo = new ArrayList<>();
    private List<MainModel> pageListThree = new ArrayList<>();
    private List<MainModel> pageListFour = new ArrayList<>();
    private List<MainModel> pageListFive = new ArrayList<>();
    private List<MainModel> pageListSix = new ArrayList<>();
    private RecyclerView.ItemDecoration mDivider;
    private String tabTitles[] = new String[]{"Android基础", "音视频开发", "Java基础", "自定义控件", "性能优化", "框架源码"};

    public static TechnologyPageFragment newInstance(String page) {
        Bundle bundle = new Bundle();
        bundle.putString(C.Params, page);
        TechnologyPageFragment fragment = new TechnologyPageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_recycler_view;
    }

    @Override
    protected void onRefresh() {
        subscribeUI();
    }

    @Override
    protected void lazyLoadData() {
        //加载数据
        subscribeUI();
    }

    @Override
    protected void initBase() {
        mPage = getArguments().getString(C.Params);
        switch (mPage) {
            case "Android基础":
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mDivider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//                layoutManager = new GridLayoutManager(getActivity(), 2);
//                mDivider = new DividerGridItemDecoration(getContext());
                break;
            case "Java基础":
                layoutManager = new GridLayoutManager(getActivity(), 2);
//                mDivider = new DividerGridItemDecoration(getContext());
                break;
            case "自定义控件":
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                mDivider = new DividerGridItemDecoration(getContext());
                break;
            default:
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mDivider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                break;
        }
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.removeItemDecoration(mDivider);
//        binding.recyclerView.addItemDecoration(mDivider);
        adapter = new MainContentAdapter(getActivity());
        binding.recyclerView.setAdapter(adapter);
    }

    private void subscribeUI() {
        switch (mPage) {
            case "Android基础"://高级ui
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_FOUR_COMPONENT, "四大组件"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_BEHAVIOR, "Behavior"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_DATA_BASE, "数据存储"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_MOTION_EVENT, "事件分发"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_UI_DRAW, "UI绘制流程"));


                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_COLLAPSING_TOOLBAR_LAYOUT, "CollapsingToolbarLayout使用"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_SLIDESLIP, "MD侧滑"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_SNACKBAR, "SnackBar解析"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_TOAST, "Toast源码解析"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_DIALOG, "Dialog源码解析"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_TEXT_INPUT_LAYOUT, "TextInputLayout使用"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_TOOLBAR, "Toolbar源码解析"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_SEARCH_VIEW, "SearchView源码解析"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_LINEAR_LAYOUT_COMPAT, "LinearLayoutCompat源码解析"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_TAB_LAYOUT, "TabLayout源码解析"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_PALETTE, "Palette调色板的使用"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_CARD_VIEW, "CardView的使用"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_COORDINATOR_LAYOUT, "CoordinatorLayout的使用"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_ANIMATION_VIEW, "动画的使用"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_PATH, "Path高级使用"));
                pageListOne.add(new MainModel(MainTag.ANDROID.TAG_SHADER, "高级渲染"));

                adapter.setData(pageListOne);
                break;
            case "Java基础":
                pageListFive.add(new MainModel(MainTag.JAVA.TAG_THREAD, "线程Thread"));

                adapter.setData(pageListFive);
                break;
            case "音视频开发":
                pageListSix.add(new MainModel(MainTag.VIDEO.TAG_AUDIO, "音频数据采集"));

                adapter.setData(pageListSix);
                break;

            case "自定义控件":
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_CLOCK_VIEW, "自定义钟表"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_HOT_TAG, "热门标签"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_LETTER_NAVIGATION, "右侧A-Z字母导航栏"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_NOTE_PAD, "自定义NotePad"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_MY_VIEWPAGER, "自定义ViewPager"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_MY_INDICATOR, "自定义ViewPager指示器"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_GUAGUA_WINNING, "刮刮乐"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_PHOTO_VIEW, "高仿今日头条图片功能"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_LOADING_LAYOUT, "加载控件"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_RADAR_VIEW, "雷达展示控件"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_IMOOC_RIPPLE, "仿慕课网下拉刷新水波纹"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_COUNT_DOWN_TIME, "广告页倒计时控件"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_CIRCLE_MENU, "圆形菜单"));

                adapter.setData(pageListFour);
                break;
            case "性能优化":
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_ACTIVITY_LAUNCH, "App广告页3秒倒计时处理"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_ACTIVITY_SPLASH, "App闪屏页动画效果"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_SVG, "SVG图片效果"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_INFLATE, "inflate()方法的使用和详解"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_SERVICE, "Service组件详解"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_AIDL_BINDER, "AIDL(Binder机制)"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_TWOCODE_DOWNLOAD, "第二行代码实例-通过服务进行下载"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_SOFT_KEYBOARD, "虚拟键盘使用"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_DATA_PERSIST, "数据持久化"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_COUNT_DOWN, "倒计时实现方案"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_SCREEN_ADAPTER, "屏幕适配方案"));


                adapter.setData(pageListTwo);
                break;
            case "框架源码":
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_ANNOTATIONS, "运行时注解框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_COMPILE_ANNOTATIONS, "编译时时注解框架(注解处理器使用)"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_NETWORK_REQUEST, "网络请求框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_CUSTOME_RETROFIT2, "自定义网络请求Retrofit2.0"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_DATABASE, "数据库框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_IMAGE_LOADER, "图片加载框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_EVENT_BUS, "EventBus"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_TENCENT_TINKER, "腾讯Tinker框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_RAJAVA, "RxJava响应式编程"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_HOT_FIX, "热更新-热修复框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_MVP, "设计模式MVP"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_ANIMATOR, "动画框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_AUTO_RECYCLER, "无限轮播"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_PULL_DOWN_REFRESH, "下拉刷新，上拉加载更多框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_VIEWPAGER_SCROLL, "各种左右滑动页面ViewPager效果"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_ALIBABA_V_LAYOUT, "Alibaba V-Layout框架使用"));

                adapter.setData(pageListThree);
                break;
        }
        showContentLayout();
    }
}
