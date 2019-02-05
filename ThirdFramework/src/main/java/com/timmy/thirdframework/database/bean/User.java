package com.timmy.thirdframework.database.bean;

import com.timmy.thirdframework.database.framework.annotation.DbField;
import com.timmy.thirdframework.database.framework.annotation.DbTable;

/**
 * 注解表示：创建一个数据库表
 * 表名为tb_user
 * 字段名为： _id  Integer
 * name  TEXT
 * age Integer
 */
@DbTable("tb_user")
public class User {
    @DbField("_id")
    private Integer id;
    @DbField("name")
    private String name;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
