package com.timmy.baselib.bean;

public class BaseResult<T> {

    public int code;
    public String msg;
    public T data;

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

    public void setStatusCode(int statusCode) {
        this.code = statusCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

}
