package com.timmy.aacgank.ui.home.adapter;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ItemWelfareBinding;
import com.timmy.adapterlib.BaseDataBindingAdapter;

public class HomeAdapter extends BaseDataBindingAdapter<Gank, ItemWelfareBinding> {

    public HomeAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void convert(ItemWelfareBinding binding, Gank item) {
        binding.setGank(item);
    }
}