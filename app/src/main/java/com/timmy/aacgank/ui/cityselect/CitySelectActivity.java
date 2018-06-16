package com.timmy.aacgank.ui.cityselect;

import android.os.Bundle;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ActivityCitySelectBinding;
import com.timmy.baselib.base.activity.TBaseContentActivity;

public class CitySelectActivity extends TBaseContentActivity<ActivityCitySelectBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
    }
}
