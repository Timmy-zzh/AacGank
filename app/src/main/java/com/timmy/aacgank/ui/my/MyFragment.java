package com.timmy.aacgank.ui.my;

import android.content.Intent;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentMyBinding;
import com.timmy.aacgank.ui.cityselect.CitySelectActivity;
import com.timmy.baselib.base.fragment.TBaseBindingFragment;


public class MyFragment extends TBaseBindingFragment<FragmentMyBinding> {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onRefresh() {
    }

    @Override
    protected void initBase() {
        showContentLayout();

        binding.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CitySelectActivity.class));
            }
        });


    }
}
