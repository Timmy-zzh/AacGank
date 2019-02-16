package com.timmy.thirdframework.database.bean;

import com.timmy.thirdframework.database.core.annotation.DbTable;

@DbTable("tb_photo")
public class Photo {

    private String path;

    private String time;

    public Photo(String path, String time) {
        this.path = path;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
