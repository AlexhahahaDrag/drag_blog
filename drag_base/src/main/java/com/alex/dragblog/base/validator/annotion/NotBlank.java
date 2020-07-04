package com.alex.dragblog.base.validator.annotion;

import com.alex.dragblog.base.validator.Messages;
import com.alex.dragblog.base.validator.Payload;
import com.alex.dragblog.base.validator.contraint.StringValidator;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 *description:  不为空字符串
 *author:       alex
 *createDate:   2020/7/2 22:50
 *version:      1.0.0
 */
@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StringValidator.class})
public @interface NotBlank {

    boolean required() default true;

    String message() default Messages.CK_NOT_BLANK_DEFAULT;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
