package com.timmy.baselib.basemvvm.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.timmy.baselib.R;
import com.timmy.baselib.basemvvm.helper.ILoadingLayout;
import com.timmy.baselib.basemvvm.statusmanager.OnStatusChildClickListener;
import com.timmy.baselib.basemvvm.statusmanager.StatusLayoutManager;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.databinding.ActivityBaseBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;
import com.timmy.baselib.listener.NetResultListener;
import com.timmy.baselib.utils.NetUtils;
import com.timmy.baselib.utils.ToastUtils;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * DataBinding界面处理基类
 * 1.顶部Toolbar处理, 可使用本类默认Toolbar展示效果
 * 暴露方法,用户自行填充Toolbar展示效果
 * 2.内容部分:分为四种状态
 * 加载状态/空界面/出错界面/内容展示
 * 暴露方法:用户可自行控制加载界面,空界面,和出错界面
 */
public abstract class TBaseBindingActivity<DB extends ViewDataBinding> extends TBaseActivity implements ILoadingLayout {

    protected DB binding;
    private ActivityBaseBinding baseBinding;
    private StatusLayoutManager statusLayoutManager;

    protected abstract void onRefresh();

    //没事不要复写这个方法
    protected void initBase() {
    }

    public ActivityBaseBinding getBaseBinding() {
        return baseBinding;
    }

    @Override
    public void setContentView(int layoutResID) {
        baseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        binding = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        //子类布局填充
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        baseBinding.flContent.addView(binding.getRoot());
        getWindow().setContentView(baseBinding.getRoot());
        //状态栏设置
        setStatusBar();
        //初始化Toolbar
        initToolbar(baseBinding.toolbar.toolbar, baseBinding.toolbar.toolbarTitle);
        initStatusLayout();
        //判断当前是否有网络--统一处理无网状态展示
        if (NetUtils.isConnected(TBaseBindingActivity.this)) {
            //默认内容:页面隐藏,展示加载页
            showLoadingLayout();
        } else {
//            showErrorLayout(new UnknownHostException());
            showErrorLayout();
        }
        initBase();
    }

    /**********************************************Toolbar*****************************************/
    /**
     * 初始化Toolbar,子类可复写该方法实现自己想要的效果
     * 暴露的方法有:
     * 1.设置顶部toolbarTitle文字属性,文字颜色和文字内容, 默认使用清单文件中设置的lable属性
     * 2.设置Toolbar背景色
     * 3.设置Toolbar返回键图片
     */
    protected void initToolbar(Toolbar toolbar, TextView toolbarTitle) {
        toolbarTitle.setText(getTitle());
//        toolbar.setBackgroundColor(getResources().getColor(R.color.white));//设置Toolbar背景色
//        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    //是否展示顶部Toolbar
    protected void showToolbar(boolean show) {
        baseBinding.toolbar.toolbar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 开启回退功能 和设置标题  引入layout文件(toolbar)就可以使用
     */
    public void setToolbarTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        if (baseBinding.toolbar.toolbarTitle != null) {
            baseBinding.toolbar.toolbarTitle.setText(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        baseBinding.toolbar.toolbar.hideOverflowMenu();
    }

    /******************************************界面不同状态展示效果*****************************************/
    //初始化状态栏
    protected void initStatusLayout() {
        statusLayoutManager = new StatusLayoutManager.Builder(binding.getRoot())
                .setLoadingLayout(getLoadingLayout())
                .setEmptyLayout(getEmptyLayout())
                .setErrorLayout(getErrorLayout())
                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onRetryClick(View view) {
                        //重试--在空界面和错误界面时,判断当前网络状态
                        if (NetUtils.isConnected(TBaseBindingActivity.this)) {
                            showLoadingLayout();
                            onRefresh();
                        } else {
//                            showErrorLayout(new UnknownHostException());
                            showErrorLayout();
                            Toast.makeText(TBaseBindingActivity.this, "世界上最遥远的距离是我在这里,你却没有网!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
    }

    //子类可复用,输入自己想展示的加载布局
    protected int getLoadingLayout() {
        return R.layout.status_loading;
    }

    protected int getEmptyLayout() {
        return R.layout.status_empty;
    }

    protected int getErrorLayout() {
        return R.layout.status_error;
    }

    @Override
    public void showLoadingLayout() {
        statusLayoutManager.showLoadingLayout();
    }

    @Override
    public void showEmptyLayout() {
        statusLayoutManager.showEmptyLayout();
    }

    @Override
    public void showErrorLayout() {
        statusLayoutManager.showErrorLayout();
    }

    @Override
    public void showContentLayout() {
        statusLayoutManager.showSuccessLayout();
    }

    /*******************************************************************************************
     * 访问网络封装--结果统一处理
     * TODO 重连与重复请求未处理
     */
    protected <T> void bindInitSubscribe(Flowable<BaseResult<T>> flowable, final NetResultListener<T> netResultListener) {
        showLoadingLayout();
        mDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new AConsumer<BaseResult<T>>() {
                            @Override
                            public void onGetResult(BaseResult<T> baseResult) {
                                ToastUtils.showErrorResult(baseResult);
                                if (baseResult != null && baseResult.getData() != null) {
                                    showContentLayout();
                                    netResultListener.getNetResult(baseResult.getData());
                                } else {
                                    showEmptyLayout();
                                }
                            }
                        }
                        , new BConsumer() {
                            @Override
                            public void onErrorResult(Throwable throwable) {
//                                showErrorLayout(throwable);
                                showErrorLayout();
                            }
                        }));
    }

    /**
     * 按钮点击等,需要在原界面上弹出LoadingDialog
     */
    protected <T> void bindDialogSubscribe(Flowable<BaseResult<T>> flowable, final NetResultListener<T> netResultListener) {
        showLoadingDialog();
        mDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new AConsumer<BaseResult<T>>() {
                            @Override
                            public void onGetResult(BaseResult<T> baseResult) {
                                hideLoadingDialog();
                                ToastUtils.showErrorResult(baseResult);
                                if (baseResult != null && baseResult.isSuccess()) {
                                    netResultListener.getNetResult(baseResult.getData());
                                }
                            }
                        }
                        , new BConsumer() {
                            @Override
                            public void onErrorResult(Throwable throwable) {
                                hideLoadingDialog();
                            }
                        }));
    }

    /**
     * 自己处理结果和异常情况
     */
    protected <T> void bindSubscribe(Flowable<T> flowable, AConsumer<T> AConsumer, BConsumer BConsumer) {
        mDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(AConsumer, BConsumer));
    }

    /**
     * 只关注结果
     */
    protected <T> void bindSubscribe(Flowable<T> flowable, AConsumer<T> AConsumer) {
        mDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(AConsumer));
    }
}