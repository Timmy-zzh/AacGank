package com.timmy.aacgank.ui.daily;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.DailyData;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ActivityDailyBinding;

/**
 * DataBinding使用
 * 1.创建需要使用到的bean类,在xml布局中使用
 * 2.使用DataBinding取代setContentView方法
 * 3.binding设置值
 */
public class DailyActivity extends AppCompatActivity {

    public static void startAction(Context context, Gank gank) {
        Intent intent = new Intent(context, DailyActivity.class);
        intent.putExtra("data", gank);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_daily);
        ActivityDailyBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_daily);

        DailyData dailyData = new DailyData();
        dailyData._id = "11027";
        dailyData.title="Timmy";
        dailyData.publishedAt = "2017-11-26";
        dailyData.content = "adfja垃圾地方垃圾的啦发卡记得了";

        binding.setDaily(dailyData);

    }

}
