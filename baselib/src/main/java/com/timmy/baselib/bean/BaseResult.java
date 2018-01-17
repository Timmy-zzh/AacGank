package com.timmy.baselib.bean;

public class BaseResult<T> {

    private int code;
    private String msg;
    private T data;

    public T getData() {
        return data;
    }

    public int getStatusCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return code == 0;
    }

}
