package com.timmy.thirdframework.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 运行时注解，主要设置数据库表字段名称和对应的数据类型
 */
@Target(ElementType.FIELD)  //属性上使用
@Retention(RetentionPolicy.RUNTIME)  //运行时注解
public @interface DbField {
    String value();
}
