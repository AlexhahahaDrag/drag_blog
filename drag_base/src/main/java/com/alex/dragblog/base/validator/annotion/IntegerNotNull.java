package com.alex.dragblog.base.validator.annotion;

import com.alex.dragblog.base.validator.Messages;
import com.alex.dragblog.base.validator.Payload;
import com.alex.dragblog.base.validator.contraint.IntegerValidator;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntegerValidator.class)
@Target({TYPE, ANNOTATION_TYPE, FIELD})
public @interface IntegerNotNull {

    boolean required() default true;

    String message() default Messages.CK_NUMERIC_DEFAULT;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
