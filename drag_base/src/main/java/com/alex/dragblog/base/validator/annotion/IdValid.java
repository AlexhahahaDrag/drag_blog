package com.alex.dragblog.base.validator.annotion;

import com.alex.dragblog.base.validator.contraint.IdValidator;
import com.alex.dragblog.base.validator.Messages;
import com.alex.dragblog.base.validator.Payload;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 *description:  校验id
 *author:       alex
 *createDate:   2020/7/4 15:58
 *version:      1.0.0
 */
@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdValidator.class})
public @interface IdValid {

    boolean required() default true;

    String message() default Messages.ID_LENGTH_THIRTY_TWO;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
