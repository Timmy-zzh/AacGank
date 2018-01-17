package com.timmy.aacgank.bean.base;

/**
 * Created by Timmy on 2018/1/17.
 */

public class BaseGankResult<T> {
    public boolean error;
    public T results;

    public T getData() {
        return results;
    }
}
