package com.timmy.thirdframework.database.core;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.timmy.baselib.utils.LogUtils;
import com.timmy.thirdframework.database.core.annotation.DbField;
import com.timmy.thirdframework.database.core.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 架构：最重要的是思想
 */
public class BaseDao<T> implements IBaseDao<T> {

    private String tableName;
    private SQLiteDatabase sqLiteDatabase;
    private HashMap<String, Field> cacheMap;
    private Class<T> entityClass;
    private boolean isInit = false;

    /**
     * Dao层初始化
     * 1.因为传入了JavaBean Class
     * 根据类上的注解，拿到表明和字段名，进行数据库创建
     */
    public boolean init(SQLiteDatabase sqLiteDatabase, Class<T> tClass) {
        if (!isInit) {
            LogUtils.d(isInit);
            this.sqLiteDatabase = sqLiteDatabase;
            this.entityClass = tClass;
            if (!sqLiteDatabase.isOpen()) {
                return isInit;
            }

            //根据注解获取表名
            if (tClass.getAnnotation(DbTable.class) != null) {
                tableName = tClass.getAnnotation(DbTable.class).value();
            } else {
                tableName = tClass.getSimpleName();   //没有使用注解则直接使用类名作为表名
            }

            String sqlCreateTable = getCreateTableSql(tClass);
            sqLiteDatabase.execSQL(sqlCreateTable);
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }
        return isInit;
    }

    /**
     * 数据库表，创建完成，取出表中所有的字段和java 对象所有字段的Field缓存
     */
    private void initCacheMap() {
        Cursor cursor = null;
        try {
            String sql = "select * from " + tableName + " limit 1 offset 0";
            cursor = sqLiteDatabase.rawQuery(sql, null, null);
            String[] columnNames = cursor.getColumnNames(); //数据库表字段名
            //取出java对象所有Field
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }

            //数据库表字段与java对象比较
            for (String columnName : columnNames) {
                Field columnField = null;
                for (Field field : fields) {
                    String fieldName;
                    if (field.getAnnotation(DbField.class) != null) {
                        fieldName = field.getAnnotation(DbField.class).value();
                    } else {
                        fieldName = field.getName();
                    }
                    if (columnName.equals(fieldName)) {
                        columnField = field;
                        break;
                    }
                }
                if (columnField != null) {
                    cacheMap.put(columnName, columnField);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 获得创建数据库sql语句
     * 1。根据Class上属性的注解，
     */
    private <T> String getCreateTableSql(Class<T> tClass) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not exists ");
        stringBuffer.append(tableName + "(");
        //反射拿到所有的成员变量
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            Class fieldType = field.getType();//属性数据类型
            //字段名
            String columnName;
            if (field.getAnnotation(DbField.class) != null) {   //取注解上设置的字段名
                columnName = field.getAnnotation(DbField.class).value();
            } else {   //直接取属性名
                columnName = field.getName();
            }
            LogUtils.d(columnName);
            //属性类型
            if (fieldType == String.class) {
                stringBuffer.append(columnName + " text,");
            } else if (fieldType == Integer.class) {
                stringBuffer.append(columnName + " integer,");
            } else if (fieldType == Long.class) {
                stringBuffer.append(columnName + " bigint,");
            } else if (fieldType == Double.class) {
                stringBuffer.append(columnName + " double,");
            } else if (fieldType == byte[].class) {
                stringBuffer.append(columnName + " blob,");
            }
//            else{
//                //不支持的类型号
//                continue;
//            }
        }
        //去掉多出的一个点
        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    /**
     * 数据库插入
     */
    @Override
    public long insert(T entity) {
//        ContentValues contentValues=new ContentValues();
//        contentValues.put("_id",'1');//缓存   _id   id变量
//        contentValues.put("name","jett");
//        sqLiteDatabase.insert(tableName,null, contentValues);

        //准备好ContentValues中需要的数据
        Map<String, String> map = getValues(entity);
        //把数据转移到ContentValues中
        ContentValues values = getContentValues(map);
        long insertResult = sqLiteDatabase.insert(tableName, null, values);
        return insertResult;
    }

    @Override
    public int delete(String whereClause, String[] whereArgs) {
        int result = sqLiteDatabase.delete(tableName, whereClause, whereArgs);
        LogUtils.d(result);
        return result;
    }

    @Override
    public long update(T t, String whereClause, String[] whereArgs) {
        ContentValues values = getContentValues(getValues(t));
        int updateResult = sqLiteDatabase.update(tableName, values, whereClause, whereArgs);
        LogUtils.d(updateResult);
        return updateResult;
    }

    @Override
    public List<T> queryAll() {
        return query(null, null);
    }

    @Override
    public List<T> query(String selection, String[] selectionArgs) {
        Cursor cursor = sqLiteDatabase.query(tableName, null, selection, selectionArgs,
                null, null, null, null);
        List<T> resultList = getResult(cursor);
        return resultList;
    }

    private List<T> getResult(Cursor cursor) {
        List resultList = new ArrayList<>();
        Object item;
        try {
            while (cursor.moveToNext()) {
                item = entityClass.newInstance();
                Iterator<String> iterator = cacheMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Field field = cacheMap.get(key);
                    Class<?> type = field.getType();
                    int columnIndex = cursor.getColumnIndex(key);
                    if (columnIndex != -1) {
                        if (type == String.class) {
                            field.set(item, cursor.getString(columnIndex));
                        } else if (type == Double.class) {
                            field.set(item, cursor.getDouble(columnIndex));
                        } else if (type == Integer.class) {
                            field.set(item, cursor.getInt(columnIndex));
                        } else if (type == Long.class) {
                            field.set(item, cursor.getLong(columnIndex));
                        } else if (type == byte[].class) {
                            field.set(item, cursor.getBlob(columnIndex));
                        }
                    }
                }
                resultList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resultList;
    }

    /**
     * 拿到java对象真实的属性值存储起来，
     *
     * @param entity
     * @return 数据库表字段名与实际值的map集合                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String>  字段名-真实值
     */
    private Map<String, String> getValues(T entity) {
        Map<String, String> resultMap = new HashMap<>();
        Iterator<String> iterator = cacheMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Field field = cacheMap.get(key);
            field.setAccessible(true);
            try {
                Object object = field.get(entity);
                if (object == null) {
                    continue;
                }
                String value = object.toString();
                LogUtils.d(value);
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    resultMap.put(key, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }


}
