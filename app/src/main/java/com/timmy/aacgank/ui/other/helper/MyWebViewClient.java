package com.timmy.aacgank.ui.other.helper;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;


public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        Logger.d("shouldOverrideUrlLoading  URL:" + url);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.loadUrl(url);
            }
        }, 100);
        return true;
    }

    //允许访问https
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
}
