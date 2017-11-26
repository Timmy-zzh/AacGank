package com.timmy.aacgank.ui.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public String TAG = this.getClass().getSimpleName();
    private List<T> realDatas;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    protected Context context;
    public int TYPE_FOOTER = 2;//底部--往往是loading_more
    public int TYPE_LIST = 3;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        realDatas = new ArrayList<>();
    }

    public BaseRecyclerAdapter setDatas(List<T> realDatas) {
        this.realDatas = realDatas;
        return this;
    }

    public BaseRecyclerAdapter addMoreDatas(List<T> realDatas) {
        this.realDatas.addAll(realDatas);
        notifyDataSetChanged();
        return this;
    }

    /***
     * 填充布局，返回fragment布局id
     *
     * @return
     */
    public abstract int inflaterItemLayout(int viewType);

    public abstract void onItemClickListener(View itemView, int pos, T t);

    public void onItemLongClickListener(View itemView, int pos, T t) {

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final BaseViewHolder holder = new BaseViewHolder(context,inflater.inflate(inflaterItemLayout(viewType), parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, position, realDatas.get(position));
        holder.itemView.setOnClickListener(getOnClickListener(position));
        if (mClickListener == null) {
            mClickListener = new OnItemClickListener<T>() {

                @Override
                public void onItemClick(View itemView, int pos, T t) {
                    onItemClickListener(itemView, pos, t);
                }
            };
        }
        holder.itemView.setOnClickListener(getOnClickListener(position));
        if (mLongClickListener == null) {
            mLongClickListener = new OnItemLongClickListener<T>() {

                @Override
                public void onItemLongClick(View itemView, int pos, T t) {
                    onItemLongClickListener(itemView, pos, t);
                }
            };
        }
        holder.itemView.setOnLongClickListener(getOnLongClickListener(position));
    }

    protected abstract void bindData(BaseViewHolder holder, int position, T t);

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (mClickListener != null && v != null) {
                    mClickListener.onItemClick(v, position, realDatas.get(position));
                }
            }
        };
    }

    public View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongClickListener != null && v != null) {
                    mLongClickListener.onItemLongClick(v, position, realDatas.get(position));
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public int getItemCount() {
        if (realDatas != null) {
            return realDatas.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return realDatas.get(position) != null ? TYPE_LIST : TYPE_FOOTER;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, int pos, T t);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View itemView, int pos, T t);
    }


    public List<T> getRealDatas() {
        return realDatas;
    }

    public void setRealDatas(List<T> realDatas) {
        this.realDatas = realDatas;
    }
}
