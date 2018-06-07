package com.timmy.aacgank.ui.home.adapter;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ItemWelfareBinding;
import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseViewHolder;

public class HomeAdapter extends BaseDataBindingAdapter<Gank, ItemWelfareBinding> {

    public HomeAdapter() {
        super(R.layout.item_welfare);
    }

//    @Override
//    protected void convert(ItemWelfareBinding binding, Gank item) {
//        binding.setGank(item);
//    }

    @Override
    protected void convert(BaseViewHolder helper, ItemWelfareBinding binding, Gank item) {
        binding.setGank(item);
    }
}