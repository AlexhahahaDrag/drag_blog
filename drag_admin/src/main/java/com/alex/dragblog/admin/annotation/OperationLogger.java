package com.alex.dragblog.admin.annotation;

import com.alex.dragblog.base.enums.PlatformEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *description:  标注该标注注解的方法需要记录操作日志
 *author:       alex
 *createDate:   2020/7/13 21:45
 *version:      1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogger {

    //业务名
    String value() default "";

    //平台
    PlatformEnum platform() default PlatformEnum.ADMIN;

    //是否保存当前日志到数据库中
    boolean save() default true;
}
