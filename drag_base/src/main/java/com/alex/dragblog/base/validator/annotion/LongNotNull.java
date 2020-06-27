package com.alex.dragblog.base.validator.annotion;

import com.alex.dragblog.base.validator.Messages;
import com.alex.dragblog.base.validator.Payload;

/**
 *description:
 *author:       alex
 *createDate:   2020/6/26 23:24
 *version:      1.0.0
 */
public @interface LongNotNull {

    boolean required() default true;

    String message() default Messages.CK_NUMERIC_DEFAULT;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
