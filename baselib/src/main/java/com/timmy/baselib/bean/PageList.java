package com.timmy.baselib.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyang on 2017/11/1.
 */

public class PageList<T> implements Serializable{
    private static final long serialVersionUID = 8565086564878811733L;


    /** 总页数 */
    private int totalpage;
    /** 当前页码：从1开始计数，0页无数据 */
    private int currPage;
    /** 返回数据总个数 */
    private int total;
    /** 当前页数据个数 */
    private int count;
    /** 是否存在下一页 */
    private boolean hasNext;
    private long time;
    private List<T> data;


    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "totalpage=" + totalpage +
                ", currPage=" + currPage +
                ", total=" + total +
                ", count=" + count +
                ", hasNext=" + hasNext +
                ", time=" + time +
                ", data=" + data +
                '}';
    }

}
