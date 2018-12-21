package com.timmy.baselib.simple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.timmy.baselib.R;

import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mData;
    private Context mContext;

    public SimpleAdapter(Context context, List<String> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.mContext = context;
    }

    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_name, parent, false);
        return new SimpleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleAdapter.ViewHolder holder, final int position) {
        holder.tvName.setText("item:" + position + ":  " + this.mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
