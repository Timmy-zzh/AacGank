package com.timmy.thirdframework.net;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.timmy.baselib.base.BaseActivity;
import com.timmy.thirdframework.R;
import com.timmy.thirdframework.net.core.Call;
import com.timmy.thirdframework.net.core.Callback;
import com.timmy.thirdframework.net.core.HttpClient;
import com.timmy.thirdframework.net.core.Request;
import com.timmy.thirdframework.net.core.RequestBody;
import com.timmy.thirdframework.net.core.Response;

/**
 * 网络请求框架
 */
public class NetHttpActivity extends BaseActivity {

    private HttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_http);
        //相当于请求框架的管家一样
        client = new HttpClient.Builder()
                .retrys(3).build();
    }

    public void getRequest(View view) {
        //一个请求，封装
        Request request = new Request.Builder()
                .url("http://www.kuaidi100.com/query?type=yuantong&postid=222222222")
                .build();
        /**
         * 每次发起一个请求，都重新创建一个Call
         */
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                Log.d("Timmy", "onResponse:" + response.getBody());
                Log.d("Timmy", "onResponse:" + response.getHeaders().toString());
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.d("Timmy", "onFailure:" + throwable.toString());
            }
        });
    }

    public void postRequest(View view) {

        RequestBody body = new RequestBody()
                .add("city", "长沙")
                .add("key", "13cb58f5884f9749287abbead9c658f2");
        Request request = new Request.Builder().url("http://restapi.amap" +
                ".com/v3/weather/weatherInfo").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.e("响应体", response.getBody());
            }
        });
    }
}
