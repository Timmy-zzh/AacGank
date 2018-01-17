package com.timmy.baselib.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.timmy.baselib.R;
import com.timmy.baselib.bean.BaseResult;
import com.timmy.baselib.databinding.ActivityBaseBinding;
import com.timmy.baselib.http.rxjava2.AConsumer;
import com.timmy.baselib.http.rxjava2.BConsumer;
import com.timmy.baselib.listener.NetResultListener;
import com.timmy.baselib.utils.NetUtils;
import java.net.UnknownHostException;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * DataBinding基类
 * 统一处理:
 * 1.界面状态:加载中->空界面->错误界面->正常数据界面
 * 2.数据请求基类封装
 * 3.提供给子类不同界面展示不一样的异常界面
 * 4.
 */
public abstract class TBaseBindingActivity<DB extends ViewDataBinding> extends TBaseActivity {

    protected DB binding;
    protected ActivityBaseBinding baseBinding;

    protected abstract void onRefresh();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 没事不要复写这个方法
     */
    protected void initBase() {
    }

    @Override
    public void setContentView(int layoutResID) {
        baseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        binding = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        //子类布局填充
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        baseBinding.rlContent.addView(binding.getRoot());
        getWindow().setContentView(baseBinding.getRoot());

        baseBinding.statusView.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重试--在空界面和错误界面时,判断当前网络状态
                if (NetUtils.isConnected(TBaseBindingActivity.this)) {
                    showLoadingLayout();
                    onRefresh();
                } else {
                    showErrorLayout(new UnknownHostException());
                    Toast.makeText(TBaseBindingActivity.this, "世界上最遥远的距离是我在这里,你却没有网!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //判断当前是否有网络--统一处理无网状态展示
        if (NetUtils.isConnected(TBaseBindingActivity.this)) {
            //默认内容:页面隐藏,展示加载页
            binding.getRoot().setVisibility(View.GONE);
        } else {
            showErrorLayout(new UnknownHostException());
        }
        initBase();
    }

    public void addTopView(View view) {
        baseBinding.toolbar.toolbarTitle.setVisibility(View.GONE);
        baseBinding.toolbar.rlToolbarContent.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        baseBinding.toolbar.rlToolbarContent.addView(view);
    }

    public void addTopView(@LayoutRes int layoutRes) {
        addTopView(getLayoutInflater().inflate(layoutRes, null));
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

    /***************************************************************************************
     * 是否展示顶部Toolbar
     *
     * @param show 默认展示
     */
    protected void showToolbar(boolean show) {
        baseBinding.toolbar.toolbar.setVisibility(show ? View.VISIBLE : View.GONE);
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
        showLoadingDialog();
        mDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new AConsumer<BaseResult<T>>() {
                            @Override
                            public void onGetResult(BaseResult<T> baseResult) {
                                hideLoadingDialog();
//                                hj.glj.util.Toast.toastErrorResult(baseResult);
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