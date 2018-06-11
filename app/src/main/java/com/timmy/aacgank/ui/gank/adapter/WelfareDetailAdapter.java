package com.timmy.aacgank.ui.gank.adapter;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ItemAndroidBinding;
import com.timmy.aacgank.databinding.ItemWelfareDetailBinding;
import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseViewHolder;

import java.util.List;

/**
 * {"福利", "Android", "iOS", "休息视频", "拓展资源", "前端", "all"};
 * 根据不同类型展示不同界面
 */
public class WelfareDetailAdapter extends BaseDataBindingAdapter<Gank, ItemWelfareDetailBinding> {

    public WelfareDetailAdapter() {
        super(R.layout.item_welfare_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemWelfareDetailBinding binding, Gank gank) {
        helper.addOnClickListener(R.id.tv_desc);
        binding.setGank(gank);

        String author = "(who:" + gank.getWho() + ")";
        SpannableString spannableString = new SpannableString(author);
        spannableString.setSpan(new TextAppearanceSpan(helper.getContext(), R.style.ViaTextAppearance), 0, author.length(), 0);
        SpannableStringBuilder builder = new SpannableStringBuilder(" * " + gank.getDesc()).append(spannableString);
        CharSequence gankText = builder.subSequence(0, builder.length());
        helper.setText(R.id.tv_desc, gankText);

        int position = getData().indexOf(gank);
        if (position == 0) {
            binding.setShow(true);
        } else if (position > 1) {
            Gank prePageItem = getData().get(position - 1);
            if (gank.getType().equals(prePageItem.getType())) {
                binding.setShow(false);
            } else {
                binding.setShow(true);
            }
        }
    }
}