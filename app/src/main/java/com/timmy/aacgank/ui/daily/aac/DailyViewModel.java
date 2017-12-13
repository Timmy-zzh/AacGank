package com.timmy.aacgank.ui.daily.aac;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.timmy.aacgank.bean.gank.DailyData;

/**
 * Created by Timmy on 2017/12/13.
 */

public class DailyViewModel extends ViewModel {

    private MutableLiveData<Integer> clickMes = new MutableLiveData<>();

    public MutableLiveData<Integer> getClickMes() {
        return clickMes;
    }

    public void changeNum(int num){
        clickMes.setValue(num);
    }

    private DailyRepository repository;

    public DailyViewModel() {
        repository = new DailyRepository();
    }

    public LiveData<DailyData> getDailyData(int year, int month, int day){
        return repository.getDailyData(year,month,day);
    }

}
