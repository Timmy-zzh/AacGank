package com.timmy.baselib.listener;

/**
 * item点击回调
 * @param <T>
 */
public interface OnItemClickListener<T> {
    void onClick(T t, int position);
}
