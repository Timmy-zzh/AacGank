package com.timmy.aacgank.ui.home;

import android.content.Intent;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentTextBinding;
import com.timmy.aacgank.ui.other.HandlerActivity;
import com.timmy.baselib.base.fragment.DjBaseBindingFragment;
import com.timmy.baselib.utils.LogUtils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/11/26.
 */

public class TextFrgment extends DjBaseBindingFragment<FragmentTextBinding> {

    private String TAG = "Timmy";
    public static TextFrgment newInstance() {
        TextFrgment fragment = new TextFrgment();
        return fragment;
    }


    @Override
    protected int getLayoutRes() {
        return  R.layout.fragment_text;
    }

    @Override
    protected void onRefresh() {

    }

    /**
     * OkHttp的使用
     */
    @Override
    protected void initBase() {
        showContentLayout();
        binding.setTextFragment(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.d("222");
                    String requestResult = httpRequest();
                    LogUtils.d(requestResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LogUtils.d("444444444444");
                        String requestResult = httpRequest();
                        LogUtils.d(requestResult);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private String httpRequest() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.authenticator()
        LogUtils.d("333");
        Request request = new Request.Builder()
                .url("http://gank.io/api/day/2015/08")
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public void handlerUse(View view) {
        startActivity(new Intent(getContext(), HandlerActivity.class));
    }
}
