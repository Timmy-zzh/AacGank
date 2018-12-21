package com.timmy.baselib.basemvvm.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.timmy.baselib.R;
import com.timmy.baselib.basemvvm.activity.TBaseBindingActivity;
import com.timmy.baselib.basemvvm.helper.ILoadingLayout;
import com.timmy.baselib.basemvvm.statusmanager.OnStatusChildClickListener;
import com.timmy.baselib.basemvvm.statusmanager.StatusLayoutManager;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.databinding.FragmentBaseBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;
import com.timmy.baselib.listener.NetResultListener;
import com.timmy.baselib.utils.NetUtils;
import com.timmy.baselib.utils.ToastUtils;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public abstract class TBaseBindingFragment<DB extends ViewDataBinding> extends Fragment implements ILoadingLayout {

    protected final CompositeDisposable mDisposable = new CompositeDisposable();
    protected DB binding;
    private FragmentBaseBinding baseBinding;
    protected TBaseBindingActivity activity;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TBaseBindingActivity) {
            activity = (TBaseBindingActivity) context;
        }
    }

    /**
     * 每个Fragment自己的布局
     */
    protected abstract @LayoutRes
    int getLayoutRes();

    protected abstract void onRefresh();

    protected void initBase() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false);
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), null, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        baseBinding.flContent.addView(binding.getRoot());
        return baseBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusLayout();
        //判断当前是否有网络--统一处理无网状态展示
        if (NetUtils.isConnected(getActivity())) {
            //默认内容:页面隐藏,展示加载页
            showLoadingLayout();
        } else {
//            showErrorLayout(new UnknownHostException());
            showErrorLayout();
        }
        initBase();
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
                        if (NetUtils.isConnected(getActivity())) {
                            showLoadingLayout();
                            onRefresh();
                        } else {
//                            showErrorLayout(new UnknownHostException());
                            showErrorLayout();
                            Toast.makeText(getActivity(), "世界上最遥远的距离是我在这里,你却没有网!", Toast.LENGTH_SHORT).show();
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
        activity.showLoadingDialog();
        mDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new AConsumer<BaseResult<T>>() {
                            @Override
                            public void onGetResult(BaseResult<T> baseResult) {
                                activity.hideLoadingDialog();
                                ToastUtils.showErrorResult(baseResult);
                                if (baseResult != null && baseResult.isSuccess()) {
                                    netResultListener.getNetResult(baseResult.getData());
                                }
                            }
                        }
                        , new BConsumer() {
                            @Override
                            public void onErrorResult(Throwable throwable) {
                                activity.hideLoadingDialog();
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
                .subscribe(
                        AConsumer,
                        BConsumer
                ));
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

    @Override
    public void onStop() {
        super.onStop();
        mDisposable.clear();
    }
}