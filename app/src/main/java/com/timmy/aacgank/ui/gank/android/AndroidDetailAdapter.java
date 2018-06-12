package com.timmy.aacgank.ui.gank.android;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.ui.other.helper.MyWebViewClient;
import com.timmy.baselib.utils.LogUtils;

import java.util.List;

public class AndroidDetailAdapter extends RecyclerView.Adapter<AndroidDetailAdapter.TViewHolder> {

    private String TAG = this.getClass().getSimpleName();
    private Gank gank;
    private static int itemCount = 5;
    public final static int TYPE_IMAGE = 0;
    public final static int TYPE_MAIN = 1;
    public final static int TYPE_REVIEW = 2;
    public final static int TYPE_WEBVIEW = 3;
    public final static int TYPE_DEAL_LIST = 4;
    private List<ItemType> typeList;

    public void setGank(Gank gank) {
        this.gank = gank;
    }

    @Override
    public TViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.d(TAG + " > onCreateViewHolder > viewType:" + viewType);
        return new TViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemView(viewType), parent, false));
    }

    private int getItemView(int viewType) {
        switch (viewType) {
            case TYPE_IMAGE:
                return R.layout.item_androiddetail_topimage;
            case TYPE_MAIN:
                return R.layout.item_androiddetail_main;
            case TYPE_REVIEW:
                return R.layout.item_androiddetail_review;
            case TYPE_WEBVIEW:
                return R.layout.item_androiddetail_webview;
            default:
                return R.layout.item_androiddetail_deallist;
        }
    }

    @Override
    public void onBindViewHolder(TViewHolder holder, int position) {
        LogUtils.d(TAG + " > onBindViewHolder > position:" + position);
        switch (position) {
            case TYPE_IMAGE:

                break;
            case TYPE_MAIN:

                break;
            case TYPE_REVIEW:

                break;
            case TYPE_WEBVIEW:
                WebView mWebView = holder.itemView.findViewById(R.id.web_view);
                mWebView.setVisibility(View.VISIBLE);
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                mWebView.setVerticalScrollBarEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
                mWebView.setWebViewClient(new MyWebViewClient());
                mWebView.setWebChromeClient(new WebChromeClient());
                mWebView.loadUrl(gank.getUrl());
                break;
            default:
                ImageView imageView = holder.itemView.findViewById(R.id.iv_deal);
                TextView dealName = holder.itemView.findViewById(R.id.tv_deal_name);
                dealName.setText("DealName :"+position);
                if (position%2 == 0){
                    imageView.setImageResource(R.mipmap.ic_002);
                }else{
                    imageView.setImageResource(R.mipmap.ic_001);
                }

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        LogUtils.d(TAG + " > getItemViewType > position:" + position);
        return typeList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        LogUtils.d(TAG + " > getItemCount >");
        return typeList.size();
    }

    public void setData(List<ItemType> typeList) {
        this.typeList = typeList;
    }

    class TViewHolder extends RecyclerView.ViewHolder {
        public TViewHolder(View itemView) {
            super(itemView);
        }
    }
}
