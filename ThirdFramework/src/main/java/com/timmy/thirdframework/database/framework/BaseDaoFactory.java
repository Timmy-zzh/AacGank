package com.timmy.thirdframework.database.framework;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Dao层工厂类
 * 1.创建数据库: 确定数据库存放路径
 * 2.初始化BaseDao层，通过反射创建BaseDao的实例，完成Dao层对应的bean对象数据库表创建
 * 20190126:修改
 * 3.BaseDao层对象使用map缓存，不用每次都进行反射创建BaseDao实例
 * 20190126
 * 4.数据库分库：路径和数据库名  BaseDaoSubFactory
 * 5。数据库升级
 *
 */
public class BaseDaoFactory {

    private final SQLiteDatabase sqLiteDatabase;
    private static BaseDaoFactory instance;
    //线程安全的Map集合
    protected Map<String, BaseDao> baseDaoMap = Collections.synchronizedMap(new HashMap<String, BaseDao>());

    public static BaseDaoFactory getInstance() {
        if (instance == null) {
            synchronized (BaseDaoFactory.class) {
                if (instance == null) {
                    instance = new BaseDaoFactory();
                }
            }
        }
        return instance;
    }

    protected BaseDaoFactory() {
//        String dbPath = "data/data/com.timmy.aacgank/timmy_db0115.db";
//        String dbPath = Environment.getExternalStorageDirectory() + "/AacGank/timmy_db0115.db";
        String dirPath = Environment.getExternalStorageDirectory() + "/AacGank/";
        File dirFile  = new File(dirPath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dirPath+"timmy_db0126.db", null);
    }

    /**
     * @param daoClass
     * @param entityClass
     * @param <T>
     * @return
     */
    public <M extends BaseDao<T>, T> M getBaseDao(Class<M> daoClass, Class<T> entityClass) {
        BaseDao baseDao = null;
        if (baseDaoMap.get(daoClass.getSimpleName()) != null) {
            return (M) baseDaoMap.get(daoClass.getSimpleName());
        }
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
            baseDaoMap.put(daoClass.getSimpleName(), baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (M) baseDao;
    }

//    public <T> BaseDao<T> getBaseDao(Class<T> tClass) {
//        BaseDao baseDao = null;
//        try {
//            baseDao = BaseDao.class.newInstance();
//            baseDao.init(sqLiteDatabase, tClass);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        return baseDao;
//    }

}
