package com.timmy.aacgank.ui.other;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.orhanobut.logger.Logger;
import com.timmy.aacgank.C;
import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ActivityWebViewBinding;
import com.timmy.aacgank.ui.home.MainActivity;
import com.timmy.aacgank.ui.other.helper.JsInteration;
import com.timmy.aacgank.ui.other.helper.MyWebViewClient;
import com.timmy.baselib.base.activity.TBaseContentActivity;


public class WebViewActivity extends TBaseContentActivity<ActivityWebViewBinding> {

//    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    public static void startAction(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(C.Params, url);
        context.startActivity(intent);
    }

    public static void startAction(Context context, String url,String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(C.Params, url);
        intent.putExtra(C.Params2, title);
        context.startActivity(intent);
    }

    public static void startActionData(Context context, String urlData) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(C.Params1, urlData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        showToolbar(false);
        initWeb();
        initToolbarTitle();
    }

    private void initToolbarTitle() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        binding.title.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                final TextView textView = new TextView(WebViewActivity.this);
                textView.setTextAppearance(WebViewActivity.this, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                }, 1738);

                return textView;
            }
        });
        //调用next方法显示下一个字符串
        next(getIntent().getStringExtra(C.Params2));
    }

    //事件处理函数，控制显示下一个字符串
    public void next(String title)
    {
        binding.title.setText(title);
    }

    protected void initWeb() {
        initWebView();
        Intent intent = getIntent();
        String url = intent.getStringExtra(C.Params);
        String urlData = intent.getStringExtra(C.Params1);

        binding.webView.clearHistory();
        binding.webView.clearFormData();
        binding.webView.clearCache(true);

        if (!TextUtils.isEmpty(url)) {
            Logger.d(url);
//            binding.webView.loadData("", "text/html", null);
            binding.webView.loadUrl(url);
        } else if (!TextUtils.isEmpty(urlData)) {
            binding.webView.loadData(urlData, "text/html; charset=UTF-8", null);
        }
    }

    private void initWebView() {
        binding.webView.setVerticalScrollBarEnabled(false);
        // 设置支持JavaScript脚本
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 允许webView执行JavaScript
        webSettings.setJavaScriptEnabled(true);
        binding.webView.addJavascriptInterface(new JsInteration(getBaseContext()), "androidInterface");
        // 支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        // 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBlockNetworkImage(false);
        //不使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        开启DOM storage API 功能
        webSettings.setDomStorageEnabled(true);

        //设置缓存
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//         LOAD_CACHE_ONLY:  不使用网络，只读取本地缓存数据
//        LOAD_DEFAULT:  根据cache-control决定是否从网络上取数据。
//        LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
//        LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
//                LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
//
//        开启DOM storage API 功能
//        webSettings.setDomStorageEnabled(true);
//        开启database storage API功能
//        webSettings.setDatabaseEnabled(true);
//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
//        webSettings.setAppCachePath(cacheDirPath);
//        webSettings.setAppCacheEnabled(true);

        //兼容5.0以上使用https协议访问图片
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        binding.webView.setWebViewClient(new MyWebViewClient());
//        binding.webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
//                Logger.d("shouldOverrideUrlLoading:" + url);
//                view.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        view.loadUrl(url);
//                    }
//                }, 100);
//                return true;
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                binding.webView.addJavascriptInterface(new JsInteration(getBaseContext()), "androidJS");
//            }
//        });

        binding.webView.setWebChromeClient(new WebChromeClient() {

                                               @Override
                                               public void onProgressChanged(WebView view, int newProgress) {
                                                   if (binding.progressBar.getVisibility() == View.GONE) {
                                                       binding.progressBar.setVisibility(View.VISIBLE);
                                                   }
                                                   binding.progressBar.setProgress(newProgress);
                                                   if (newProgress >= 100) {
                                                       binding.progressBar.setVisibility(View.GONE);
                                                   }
                                               }

                                               @Override
                                               public void onReceivedTitle(WebView view, String title) {
                                                   super.onReceivedTitle(view, title);
                                                   if (!TextUtils.isEmpty(title)) {
//                                                       next(title);
                                                   }
                                               }

                                               @Override
                                               public boolean onJsAlert(WebView view, String url, String message,
                                                                        final JsResult result) {
                                                   // 构建一个Builder来显示网页中的alert对话框
                                                   AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                   builder.setTitle("外部js提示");
                                                   builder.setMessage(message);
                                                   builder.setPositiveButton(android.R.string.ok,
                                                           new AlertDialog.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   result.confirm();
                                                               }
                                                           });
                                                   builder.setCancelable(false);
                                                   builder.create();
                                                   builder.show();
                                                   return true;
                                               }
                                           }
        );
    }

    @Override
    public void onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();// 返回前一个页面
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanWebViewCache();
        ((ViewGroup) binding.webView.getParent()).removeView(binding.webView);
        binding.webView.removeAllViews();
        binding.webView.destroy();
    }

    /**
     * 删除WebView的缓存数据
     * setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
     * deleteDatabase("WebView.db");和deleteDatabase("WebViewCache.db");
     * webView.clearHistory();
     * webView.clearFormData();
     * getCacheDir().delete();
     * 手动写delete方法，循环迭代删除缓存文件夹！
     */
    private void cleanWebViewCache() {
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
        deleteDatabase("WebView.db");
        deleteDatabase("WebViewCache.db");
    }
}
