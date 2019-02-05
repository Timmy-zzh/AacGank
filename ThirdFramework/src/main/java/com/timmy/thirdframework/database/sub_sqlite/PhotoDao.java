package com.timmy.thirdframework.database.sub_sqlite;

import com.timmy.thirdframework.database.bean.Photo;
import com.timmy.thirdframework.database.framework.BaseDao;

public class PhotoDao extends BaseDao<Photo> {

    @Override
    public long insert(Photo entity) {
        return super.insert(entity);
    }
}
