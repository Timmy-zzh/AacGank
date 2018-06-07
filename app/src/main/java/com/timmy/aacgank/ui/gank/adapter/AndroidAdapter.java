package com.timmy.aacgank.ui.gank.adapter;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ItemAndroidBinding;
import com.timmy.adapterlib.BaseDataBindingAdapter;

/**
 * {"福利", "Android", "iOS", "休息视频", "拓展资源", "前端", "all"};
 * 根据不同类型展示不同界面
 */
public class AndroidAdapter extends BaseDataBindingAdapter<Gank, ItemAndroidBinding> {

    public AndroidAdapter() {
        super(R.layout.item_android);
    }

//    @Override
//    protected void convert(BaseViewHolder helper, ItemAndroidBinding binding, Gank item) {
//        binding.setGank(item);
//    }

    @Override
    protected void convert(ItemAndroidBinding binding, Gank item) {
        binding.setGank(item);
    }
}