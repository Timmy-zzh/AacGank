package com.timmy.thirdframework.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Dao层工厂类
 * 1.创建数据库   确定数据库存放路径
 * 2.初始化BaseDao层，通过反射创建BaseDao的实例，完成Dao层对应的bean对象数据库表创建
 * <p>
 * 问题： 怎么创建不同的数据库
 */
public class BaseDaoFactory {

    private final SQLiteDatabase sqLiteDatabase;
    private static BaseDaoFactory instance;

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

    private BaseDaoFactory() {
        String dbPath = "data/data/com.timmy.aacgank/timmy_db0115.db";
//        String dbPath = Environment.getExternalStorageDirectory() + "/AacGank/timmy_db0115.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

    public <T> BaseDao<T> getBaseDao(Class<T> tClass) {
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase, tClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return baseDao;
    }

}
