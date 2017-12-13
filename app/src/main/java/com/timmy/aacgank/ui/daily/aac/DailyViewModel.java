package com.timmy.aacgank.ui.daily.aac;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.timmy.aacgank.bean.gank.DailyData;

/**
 * Created by Timmy on 2017/12/13.
 */

public class DailyViewModel extends ViewModel {

    private LiveData<DailyData> dailyData;

    private DailyRepository repository;

    public DailyViewModel() {
        repository = new DailyRepository();
    }

    public LiveData<DailyData> getDailyData(int year, int month, int day){
        return repository.getDailyData(year,month,day);
    }

}
