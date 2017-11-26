package com.timmy.aacgank.bean.base;

import java.util.List;

/**
 * Created by admin on 2017/3/4.
 */

public class SimpleListResult<T> extends SupperResult {

    private List<T> results;

    public List<T> getData() {
        return results;
    }

    public void setData(List<T> data) {
        this.results = data;
    }
}
