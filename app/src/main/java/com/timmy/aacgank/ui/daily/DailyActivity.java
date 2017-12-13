package com.timmy.aacgank.ui.daily;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.DailyData;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ActivityDailyBinding;
import com.timmy.aacgank.ui.base.BaseActivity;
import com.timmy.aacgank.ui.daily.aac.DailyViewModel;
import com.timmy.aacgank.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * DataBinding使用
 */
public class DailyActivity extends BaseActivity {

    private Gank gank;
    private ActivityDailyBinding binding;
    private DailyViewModel viewModel;
    private int num;

    public static void startAction(Context context, Gank gank) {
        Intent intent = new Intent(context, DailyActivity.class);
        intent.putExtra("data", gank);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daily);
        gank = (Gank) getIntent().getSerializableExtra("data");
        viewModel = ViewModelProviders.of(this).get(DailyViewModel.class);
        subscribeUI();
    }

    private void subscribeUI() {
        Date date = DateUtil.stringToDate(gank.getPublishedAt());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        viewModel.getDailyData(year, month, day).observe(this, new Observer<DailyData>() {
            @Override
            public void onChanged(@Nullable DailyData dailyData) {
                //获取到数据
                Logger.d(dailyData.toString());
                binding.tv.setText(dailyData.toString());
            }
        });

        viewModel.getClickMes().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.btn.setText(integer+"");
            }
        });


    }

    public void btnClick(View view) {
        num++;
        viewModel.changeNum(num);
    }

}
