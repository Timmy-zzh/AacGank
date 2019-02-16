package com.timmy.thirdframework.database.sub_sqlite;

import android.database.sqlite.SQLiteDatabase;

import com.timmy.thirdframework.database.core.BaseDao;
import com.timmy.thirdframework.database.core.BaseDaoFactory;

/**
 * 分数据库：路径和数据库名
 */
public class BaseDaoSubFactory extends BaseDaoFactory {

    private static final BaseDaoSubFactory ourInstance = new BaseDaoSubFactory();
    private SQLiteDatabase sqLiteDatabase;

    public static BaseDaoSubFactory getOurInstance() {
        return ourInstance;
    }

    protected BaseDaoSubFactory() {

    }

    public <M extends BaseDao<T>, T> M getBaseDao(Class<M> daoClass, Class<T> entityClass) {
        M baseDao = null;
        if (baseDaoMap.get(DataBasePathEnum.database.getPath()) != null) {
            return (M) baseDaoMap.get(DataBasePathEnum.database.getPath());
        }
        try {
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DataBasePathEnum.database.getPath(), null);
//            baseDao = BaseDao.class.newInstance();
            baseDao = daoClass.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
            baseDaoMap.put(DataBasePathEnum.database.getPath(), baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (M) baseDao;
    }
}
