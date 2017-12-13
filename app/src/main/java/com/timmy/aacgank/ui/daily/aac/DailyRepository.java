package com.timmy.aacgank.ui.daily.aac;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.timmy.aacgank.bean.base.BaseResult;
import com.timmy.aacgank.bean.gank.DailyData;
import com.timmy.aacgank.bean.gank.GankResult;
import com.timmy.aacgank.http.RetrofitManager;
import com.timmy.aacgank.http.service.GankService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Timmy on 2017/12/13.
 */

public class DailyRepository {

    private GankService gankService;

    public DailyRepository() {
        gankService = RetrofitManager.instance().getGankService();
    }

    public LiveData<DailyData> getDailyData(int year, int month, int day) {
        final MutableLiveData<DailyData> data = new MutableLiveData<>();

        gankService.getGankDetailData(year, month, day).enqueue(new Callback<DailyData>() {
            @Override
            public void onResponse(Call<DailyData> call, Response<DailyData> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DailyData> call, Throwable t) {

            }
        });
        return data;
    }

}
