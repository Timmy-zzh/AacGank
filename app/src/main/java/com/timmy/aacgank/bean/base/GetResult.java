package com.timmy.aacgank.bean.base;

/**
 * 分类列表中获取的后台数据的bean封装
 *
 * @param <T>
 * @author Administrator
 */
public class GetResult<T>  {

    private static final long serialVersionUID = 1L;

    private int totalpage;
    private int currPage;
    private int total;
    private int count;
    private boolean hasNext;

    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

}