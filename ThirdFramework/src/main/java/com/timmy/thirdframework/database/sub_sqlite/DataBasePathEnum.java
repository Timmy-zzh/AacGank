package com.timmy.thirdframework.database.sub_sqlite;

import android.os.Environment;

import java.io.File;

/**
 * 创建数据库需要的路径获取
 * 每个数据库对应着一个路径和数据库名
 */
public enum DataBasePathEnum {

    database("");

    DataBasePathEnum(String path) {

    }

    public String getPath() {
        String dirPath = Environment.getExternalStorageDirectory() + "/AacGank/";
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile + "t_login.db";

    }
}
