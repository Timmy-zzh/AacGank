package com.timmy.thirdframework.database.core;

import java.util.List;

/**
 * Dao层需要实现的抽象逻辑
 */
public interface IBaseDao<T> {

    long insert(T t);

    int delete(String whereClause, String[] whereArgs);

    long update(T t,String whereClause, String[] whereArgs);

    List<T> queryAll();

    List<T> query(String whereClause, String[] whereArgs);



}
