package com.timmy.baselib.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.timmy.baselib.R;
import com.timmy.baselib.activity.StatusView;
import com.timmy.baselib.activity.TBaseBindingActivity;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.databinding.FragmentBaseBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;
import com.timmy.baselib.listener.NetResultListener;
import com.timmy.baselib.utils.NetUtils;

import org.reactivestreams.Subscription;

import java.net.UnknownHostException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class TBaseFragment<DB extends ViewDataBinding> extends Fragment {

    protected final CompositeDisposable mDisposable = new CompositeDisposable();
    protected DB binding;
    private FragmentBaseBinding baseBinding;
    private TBaseBindingActivity activity;

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
    protected abstract int getFragmentLayout();

    protected void initBase() {

    }

    protected void onRefresh() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false);
        binding = DataBindingUtil.inflate(inflater, getFragmentLayout(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        baseBinding.rlContent.addView(binding.getRoot());
        return baseBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //与BaseBindingActivity一样的处理方式
        baseBinding.statusView.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重试--在空界面和错误界面时,判断当前网络状态
                if (NetUtils.isConnected(getActivity())) {
                    showLoadingLayout();
                    onRefresh();
                } else {
                    showErrorLayout();
                    Toast.makeText(getActivity(), "世界上最遥远的距离是我在这里,你却没有网!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //判断当前是否有网络
        if (NetUtils.isConnected(getActivity())) {
            //默认内容页面隐藏,展示加载页
            binding.getRoot().setVisibility(View.GONE);
        } else {
            showErrorLayout(new UnknownHostException());
        }
        initBase();
    }

    /******************************************界面不同状态展示效果*************************************************/
    protected void showLoadingLayout() {
        if (binding.getRoot().getVisibility() != View.GONE) {
            binding.getRoot().setVisibility(View.GONE);
        }
        if (baseBinding.statusView.getVisibility() != View.VISIBLE) {
            baseBinding.statusView.setVisibility(View.VISIBLE);
        }
        baseBinding.statusView.setType(StatusView.TYPE_LOADING);
    }

    protected void showLoadingLayout(String msg) {
        showLoadingLayout();
        baseBinding.statusView.setTips(msg);
    }

    protected void showEmptyLayout() {
        if (binding.getRoot().getVisibility() != View.GONE) {
            binding.getRoot().setVisibility(View.GONE);
        }
        if (baseBinding.statusView.getVisibility() != View.VISIBLE) {
            baseBinding.statusView.setVisibility(View.VISIBLE);
        }
        baseBinding.statusView.setType(StatusView.TYPE_EMPTY);
    }

    protected void showEmptyLayout(String text, @DrawableRes int icon) {
        showEmptyLayout();
        baseBinding.statusView.setTipsAndIcon(text, icon);
    }

    protected void showErrorLayout() {
        if (binding.getRoot().getVisibility() != View.GONE) {
            binding.getRoot().setVisibility(View.GONE);
        }
        if (baseBinding.statusView.getVisibility() != View.VISIBLE) {
            baseBinding.statusView.setVisibility(View.VISIBLE);
        }
        baseBinding.statusView.setType(StatusView.TYPE_ERROR);
    }

    protected void showErrorLayout(String text) {
        showErrorLayout();
        baseBinding.statusView.setTips(text);
    }

    protected void showErrorLayout(Throwable throwable) {
        showErrorLayout();
        baseBinding.statusView.setExcepotion(throwable);
    }

    protected void showContentLayout() {
        if (baseBinding.statusView.getVisibility() != View.GONE) {
            baseBinding.statusView.setVisibility(View.GONE);
        }
        if (binding.getRoot().getVisibility() != View.VISIBLE) {
            binding.getRoot().setVisibility(View.VISIBLE);
        }
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
//                                hj.glj.util.Toast.toastErrorResult(baseResult);
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
                                showErrorLayout(throwable);
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
//                        ,
//                        new Action() {
//                            @Override
//                            public void run() throws Exception {
//
//                            }
//                        },
//                        new Consumer<Subscription>() {
//                            @Override
//                            public void accept(Subscription subscription) throws Exception {
//
//                            }
//                        }
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