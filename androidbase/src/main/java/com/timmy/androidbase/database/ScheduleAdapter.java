package com.timmy.androidbase.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.androidbase.R;
import com.timmy.androidbase.calendar.Schedule;
import com.timmy.baselib.adapterlib.BaseViewHolder;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private LayoutInflater mInflater;
    private List<Schedule> mData;
    private Context mContext;

    public ScheduleAdapter(Context context, List<Schedule> schedules) {
        mInflater = LayoutInflater.from(context);
        mData = schedules;
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        final Schedule schedule = mData.get(position);
        holder.setText(R.id.tv_title, schedule.title);
        holder.setText(R.id.tv_description, schedule.description);
        holder.setText(R.id.tv_start_time, schedule.startTime + "");
        holder.setText(R.id.tv_end_time, schedule.endTime + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleAddOrEditActivity.startAction(mContext,schedule);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
