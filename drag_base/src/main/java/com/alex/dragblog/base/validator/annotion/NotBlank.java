package com.alex.dragblog.base.validator.annotion;

import com.alex.dragblog.base.validator.Messages;
import com.alex.dragblog.base.validator.Payload;

/**
 *description:  不为空字符串
 *author:       alex
 *createDate:   2020/7/2 22:50
 *version:      1.0.0
 */
public @interface NotBlank {

    boolean required() default true;

    String message() default Messages.CK_NOT_BLANK_DEFAULT;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
