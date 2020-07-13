package com.alex.dragblog.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *description:  自定义权限校验接口
 *author:       alex
 *createDate:   2020/7/13 21:10
 *version:      1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityVerify {
    String value() default "";
}
