package com.timmy.aacgank.ui.gank.adapter;


import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ItemWelfareBinding;
import com.timmy.adapterlib.BaseDataBindingAdapter;

/**
 * {"福利", "Android", "iOS", "休息视频", "拓展资源", "前端", "all"};
 * 根据不同类型展示不同界面
 */
public class WelfareAdapter extends BaseDataBindingAdapter<Gank, ItemWelfareBinding> {

    public WelfareAdapter() {
        super(R.layout.item_welfare);
    }

//    @Override
//    protected void convert(BaseViewHolder helper, ItemWelfareBinding binding, Gank item) {
//        binding.setGank(item);
//    }

    @Override
    protected void convert(ItemWelfareBinding binding, Gank item) {
        binding.setGank(item);
    }
}