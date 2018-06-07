package com.timmy.baselib.base.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.timmy.baselib.R;
import com.timmy.baselib.base.activity.DjBaseBindingActivity;
import com.timmy.baselib.base.helper.ILoadingLayout;
import com.timmy.baselib.base.statusmanager.OnStatusChildClickListener;
import com.timmy.baselib.base.statusmanager.StatusLayoutManager;
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

/**
 * 一,为什么会出现懒加载Fragment
 * 因为ViewPager的缓存机制,
 * 1.导致加载第一页Fragment时,第二页Fragment在不可见的时候其生命周期方法都调用了
 * (如果在生命周期方法中加载数据,这样就会浪费用户流量)
 *  第一页初始化: setUserVisibleHint(isVisibleToUser)方法调用,isVisibleToUser = false
 *  第一页可见: 再次调用setUserVisibleHint(isVisibleToUser)方法,isVisibleToUser = true
 *  第二页不可见:setUserVisibleHint(isVisibleToUser)方法调用,isVisibleToUser = false
 *  第二页生命周期方法执行
 *
 * 2.当滑动到第二页可见的时候:
 *  第一页不可见: setUserVisibleHint(isVisibleToUser)方法调用,isVisibleToUser = false
 *  第二页的生命周期方法不会再调用,只会调用setUserVisibleHint(isVisibleToUser)方法,isVisibleToUser = true
 *  第三页不可见: setUserVisibleHint(isVisibleToUser)方法调用,isVisibleToUser = false
 *  第三页生命周期方法调用
 *
 * 3.使用TabLayout 直接从第一页跳转到底四页
 *  第一页不可见: setUserVisibleHint(isVisibleToUser)方法调用,isVisibleToUser = false
 *  第四页初始化: setUserVisibleHint(isVisibleToUser)方法调用,isVisibleToUser = false
 *  第四页可见: 再次调用setUserVisibleHint(isVisibleToUser)方法,isVisibleToUser = true
 *  第四页生命周期方法调用
 *
 *  第三页和第五页:
 *      不可见: setUserVisibleHint(isVisibleToUser)方法调用,isVisibleToUser = false
 *      生命周期方法调用
 *
 * 懒加载Framgnt: 当界面可见时才进行数据加载
 * 当使用ViewPager + Fragment实现界面时,Fragment使用该类的集继承类
 *
 * 懒加载处理部分:
 *      1.onCreateView()方法,根据rootView是否为null,控制只调用一次 (初始化调用一次)
 *      2.onViewCreated()方法,(初始化调用一次),并且在界面可见情况才加载数据
 *      3.setUserVisibleHint()可见时,且之前没有加载过,再会加载数据
 *
 */
public abstract class DjPageLazyBaseFragment<DB extends ViewDataBinding> extends Fragment implements ILoadingLayout {

    protected final CompositeDisposable mDisposable = new CompositeDisposable();
    protected DB binding;
    private FragmentBaseBinding baseBinding;
    private DjBaseBindingActivity activity;
    private View rootView;
    private boolean isInited; // Fragment初始化完成
    private boolean isLoadedData;//是否已经加载过数据
    private StatusLayoutManager statusLayoutManager;
    /**
     * 每个Fragment自己的布局
     */
    protected abstract @LayoutRes int getLayoutRes();

    protected abstract void onRefresh();

    /**
     * 加载数据,且只能在该方法中加载数据,才能保证数据只加载一次
     */
    protected abstract void lazyLoadData();

    protected void initBase() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null){  //初始化调用
            return;
        }
        if (isVisibleToUser && !isLoadedData){//可见,布局填充完成 rootView
            //加载数据
            lazyLoadData();
            isLoadedData = true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DjBaseBindingActivity) {
            activity = (DjBaseBindingActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isInited = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {     //之前没有填充过布局
           baseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false);
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), null, false);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            binding.getRoot().setLayoutParams(params);
            baseBinding.flContent.addView(binding.getRoot());
            rootView = baseBinding.getRoot();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isInited) {     //只有初始化才调用一次
            isInited = false;
            //与BaseBindingActivity一样的处理方式
            initStatusLayout();
            //判断当前是否有网络
            if (NetUtils.isConnected(getActivity())) {
                showLoadingLayout();
            } else {
//                showErrorLayout(new UnknownHostException());
                showErrorLayout();
            }
            initBase();
            //可见时才加载数据
            if (getUserVisibleHint()) {
                lazyLoadData();
                isLoadedData = true;
            }
        }
    }

    /******************************************界面不同状态展示效果*************************************************/
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
//                                hj.glj.util.Toast.toastErrorResult(baseResult);
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