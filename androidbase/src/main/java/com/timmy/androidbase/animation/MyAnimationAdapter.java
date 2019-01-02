package com.timmy.androidbase.animation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.timmy.androidbase.R;

public class MyAnimationAdapter extends RecyclerView.Adapter<MyAnimationAdapter.MyViewHolder> {

    private CartAnimatorActivity mContext;

    public MyAnimationAdapter(CartAnimatorActivity context) {
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_anim, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.iv_anim = view.findViewById(R.id.iv_anim);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.iv_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] loc = new int[2];
                view.getLocationInWindow(loc);
                mContext.playAnimation(loc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_anim;

        MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
