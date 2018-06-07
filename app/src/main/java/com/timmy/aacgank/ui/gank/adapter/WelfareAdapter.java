package com.timmy.aacgank.ui.gank.adapter;


import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ItemWelfareBinding;
import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseViewHolder;

/**
 * {"福利", "Android", "iOS", "休息视频", "拓展资源", "前端", "all"};
 * 根据不同类型展示不同界面
 * 1.福利图片展示做成瀑布流
 * 2.主要处理图片的高度,因为图片的宽度是固定为屏幕一半,高度就根据原始图片的宽高比进行缩放.
 */
public class WelfareAdapter extends BaseDataBindingAdapter<Gank, ItemWelfareBinding> {

    public WelfareAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ItemWelfareBinding binding, Gank meizhi) {
        binding.setGank(meizhi);
    }
}