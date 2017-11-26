package com.timmy.aacgank.bean.base;

/**
 * Created by admin on 2017/5/8.
 */

public class BaseResult<T> extends SupperResult {

    private T data;

    public T getData() {
        return data;
    }

}
