package com.timmy.aacgank.bean.movie;

import java.util.List;

/**
 * Created by Timmy on 2018/1/17.
 */
public class BaseDoubanResult<T> {

    public int count;
    public int start;
    public int total;
    public String title;
    public List<T> subjects;

    public boolean isHasNext() {
        return (start + 1) * count < total;
    }

}
