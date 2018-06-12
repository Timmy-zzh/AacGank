package com.timmy.baselib.bean;

import java.io.Serializable;

/**
 * Created by zhangyang on 2017/10/17.
 *
 * 分页数据基类
 */
@Deprecated()
public class PageResult<T> extends BaseResult<PageList<T>> implements Serializable{
    private static final long serialVersionUID = -8598606133978446965L;

}
