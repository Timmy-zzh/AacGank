package com.timmy.baselib.activity;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.timmy.baselib.R;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;

/**
 * 界面状态展示控件-组合控件
 * 主要包括:加载界面-空界面-错误界面
 */
public class StatusView extends FrameLayout {

    public static final int TYPE_LOADING = 1;
    public static final int TYPE_EMPTY = 2;
    public static final int TYPE_ERROR = 3;
    private int currType = TYPE_LOADING;
    private TextView tvTips;
    private ImageView ivIcon;
    private View rootView;
    private LinearLayout llTipsLayout;
    private LinearLayout llLoadingLayout;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View inflate = View.inflate(context, R.layout.view_status, this);
        rootView = inflate.findViewById(R.id.fl_root);
        llTipsLayout = inflate.findViewById(R.id.ll_tips_layout);
        ivIcon = inflate.findViewById(R.id.iv_icon);
        tvTips = inflate.findViewById(R.id.tv_tips);
        llLoadingLayout = inflate.findViewById(R.id.ll_loading_layout);
    }

    public StatusView setType(int type) {
        currType = type;
        if (type == TYPE_LOADING) {
            llTipsLayout.setVisibility(View.GONE);
            llLoadingLayout.setVisibility(View.VISIBLE);
        } else if (type == TYPE_EMPTY) {
            llTipsLayout.setVisibility(View.VISIBLE);
            llLoadingLayout.setVisibility(View.GONE);
            tvTips.setText("暂无数据");
        } else if (type == TYPE_ERROR) {
            llTipsLayout.setVisibility(View.VISIBLE);
            llLoadingLayout.setVisibility(View.GONE);
            tvTips.setText("页面出了些问题");
        }
        return this;
    }

    public StatusView setExcepotion(Throwable throwable) {
        if (currType != TYPE_ERROR) {
            setType(TYPE_ERROR);
        }
        if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
            tvTips.setText("连接超时，请稍后重试");
        } else if (throwable instanceof UnknownHostException) {
            tvTips.setText("网络异常，请确保网络正常后重试");
        } else if (throwable instanceof JsonSyntaxException) {
            tvTips.setText("数据解析异常！");
        } else if (throwable instanceof HttpException) {
            tvTips.setText("网络连接异常，请稍后再试");
//            Logger.d("HttpException code:" + ((HttpException) throwable).code());
        } else {
            tvTips.setText("网络连接异常，请稍后再试");
        }
        return this;
    }

    public StatusView setTips(String text) {
        if (tvTips != null && !TextUtils.isEmpty(text))
            tvTips.setText(text);
        return this;
    }

    public StatusView setIcon(@DrawableRes int icon) {
        if (ivIcon != null && icon > 0)
            ivIcon.setImageResource(icon);
        return this;
    }

    public StatusView setTipsAndIcon(String text, @DrawableRes int icon) {
        setTips(text);
        setIcon(icon);
        return this;
    }

    public void setOnRetryListener(OnClickListener listener) {
        llTipsLayout.setOnClickListener(listener);
    }
}
