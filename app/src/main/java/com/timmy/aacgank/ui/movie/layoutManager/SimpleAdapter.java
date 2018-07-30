package com.timmy.aacgank.ui.movie.layoutManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.cityselect.SortAdapter;
import com.timmy.aacgank.ui.cityselect.helper.SortModel;

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
        View view = mInflater.inflate(R.layout.item_name, parent,false);
        return new SimpleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleAdapter.ViewHolder holder, final int position) {
        holder.tvName.setText(this.mData.get(position));
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
