package com.timmy.baselib.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 使用PageResult
 * @param <T>
 */
@SuppressWarnings("serial")
public class PageListResult<T> extends BaseResult<PageList<T>> implements Serializable {

}