package com.timmy.aacgank.ui.home.adapter;

import android.content.Context;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.ui.base.BaseRecyclerAdapter;
import com.timmy.aacgank.ui.base.BaseViewHolder;
import com.timmy.aacgank.ui.daily.DailyActivity;

/**
 * Created by admin on 2017/11/26.
 */

public class HomeAdapter extends BaseRecyclerAdapter<Gank> {

    public HomeAdapter(Context context) {
        super(context);

    }

    @Override
    public int inflaterItemLayout(int viewType) {
        return R.layout.item_welfare;
    }

    @Override
    public void onItemClickListener(View itemView, int pos, Gank gank) {
        DailyActivity.startAction(context,gank);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, Gank gank) {
        holder.setImageView(R.id.iv, gank.getUrl());
        holder.setText(R.id.tv, gank.getDesc());
    }
}
